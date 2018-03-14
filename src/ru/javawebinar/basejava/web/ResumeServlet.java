package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        if (uuid.equals("")) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (!(values == null))
                if (value.length() == 0 && values.length < 2) {
                    r.getSections().remove(type);
                } else {
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            r.addSection(type, new TextSection(value));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            r.addSection(type, new ListSection(value.split("\\n")));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            List<Organization> organizations = new ArrayList<>();
                            String[] urls = request.getParameterValues(type.name() + "url");
                            for (int i = 0; i < values.length; i++) {
                                String name = values[i];
                                if (!(name.length() == 0)) {
                                    List<Organization.Position> positions = new ArrayList<>();
                                    String nameParametr = type.name() + i;
                                    String[] startDates = request.getParameterValues(nameParametr + "startDate");
                                    String[] endDates = request.getParameterValues(nameParametr + "endDate");
                                    String[] titles = request.getParameterValues(nameParametr + "title");
                                    String[] descriptions = request.getParameterValues(nameParametr + "description");
                                    if (!(titles == null))
                                        for (int j = 0; j < titles.length; j++) {
                                            if (!(titles[j].length() == 0)) {
                                                positions.add(new Organization.Position(DateUtil.parser(startDates[j]), DateUtil.parser(endDates[j]), titles[j], descriptions[j]));
                                            }
                                        }
                                    organizations.add(new Organization(new Link(name, urls[i]), positions));
                                }
                            }
                            r.addSection(type, new OrganizationSection(organizations));
                            break;
                    }
                }
        }
        if (uuid.equals(""))
            storage.save(r);
        else
            storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "add":
                r = new Resume();
                r.addSection(SectionType.OBJECTIVE, new TextSection(""));
                r.addSection(SectionType.PERSONAL, new TextSection(""));
                r.addSection(SectionType.ACHIEVEMENT, new ListSection(""));
                r.addSection(SectionType.QUALIFICATIONS, new ListSection(""));
                r.addSection(SectionType.EXPERIENCE, new OrganizationSection(new Organization("", "", new Organization.Position())));
                r.addSection(SectionType.EDUCATION, new OrganizationSection(new Organization("", "", new Organization.Position())));
                break;
            case "edit":
            case "addEXPERIENCE":
            case "addEDUCATION":
            case "addPosition":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = new TextSection("");
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = new ListSection("");
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection organizationSection = (OrganizationSection) section;
                            List<Organization> organizations = new ArrayList<>();
                            if (action.equals("addEXPERIENCE") && type.equals(SectionType.EXPERIENCE) || action.equals("addEDUCATION") && type.equals(SectionType.EDUCATION))
                                organizations.add(new Organization("", "", new Organization.Position()));
                            if (organizationSection != null) {
                                for (Organization organization : organizationSection.getOrganizations()) {
                                    List<Organization.Position> positions = new ArrayList<>();
                                    if (action.equals("addPosition"))
                                        positions.add(new Organization.Position());
                                    positions.addAll(organization.getPositions());
                                    organizations.add(new Organization(organization.getHomePage(), positions));
                                }
                            }
                            section = new OrganizationSection(organizations);
                            break;
                    }
                    r.addSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
