package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        List list = ((ListSection) section).getItems();
                        dos.writeInt(list.size());
                        list.forEach(str -> {
                            try {
                                dos.writeUTF(String.valueOf(str));
                            } catch (IOException e) {
                                throw new StorageException("Error write QUALIFICATIONS ", e);
                            }
                        });
                    }
                    break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> sectionOrganization = ((OrganizationSection) section).getOrganizations();
                        dos.writeInt(sectionOrganization.size());
                        for (Organization organization : sectionOrganization) {
                            try {
                                dos.writeUTF(organization.getHomePage().getName());
                                dos.writeUTF(organization.getHomePage().getUrl());
                            } catch (IOException e) {
                                throw new StorageException("Error write EDUCATION ", e);
                            }

                            List<Organization.Position> positions = organization.getPositions();
                            dos.writeInt(positions.size());
                            positions.forEach(position -> {
                                try {
                                    dos.writeInt(position.getStartDate().getYear());
                                    dos.writeInt(position.getStartDate().getMonth().getValue());
                                    dos.writeInt(position.getEndDate().getYear());
                                    dos.writeInt(position.getEndDate().getMonth().getValue());
                                    dos.writeUTF(position.getTitle());
                                    dos.writeUTF(position.getDescription());
                                } catch (IOException e) {
                                    throw new StorageException("Error write EDUCATION ", e);
                                }
                            });
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int sizeContacts = dis.readInt();
            for (int i = 0; i < sizeContacts; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSections = dis.readInt();
            for (int i = 0; i < sizeSections; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(type, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        List<String> list = new ArrayList<>();
                        int sizeList = dis.readInt();
                        for (int j = 0; j < sizeList; j++) list.add(dis.readUTF());
                        resume.addSection(type, new ListSection(list));
                    }
                    break;
                    case EXPERIENCE:
                    case EDUCATION: {
                        List<Organization> listOrganization = new ArrayList<>();
                        int sizeOrganization = dis.readInt();
                        for (int j = 0; j < sizeOrganization; j++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            List<Organization.Position> positions = new ArrayList<>();
                            int sizePositions = dis.readInt();
                            for (int k = 0; k < sizePositions; k++) {
                                int StartDateYear = dis.readInt();
                                Month StartDateMonth = Month.of(dis.readInt());
                                int EndDateYear = dis.readInt();
                                Month EndDateMonth = Month.of(dis.readInt());
                                String Title = dis.readUTF();
                                String Description = dis.readUTF();
                                positions.add(new Organization.Position(StartDateYear, StartDateMonth, EndDateYear, EndDateMonth, Title, Description));
                            }

                            listOrganization.add(new Organization(new Link(name, url), positions));
                        }

                        resume.addSection(type, new OrganizationSection(listOrganization));
                    }
                }
            }
            return resume;
        }
    }
}