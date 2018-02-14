package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static final Resume R1;
    private static final Resume R2;
    private static final Resume R3;
    private static final Resume R4;

    static {
        R1 = new Resume(UUID_1, "Name1");
        R2 = new Resume(UUID_2, "Name2");
        R3 = new Resume(UUID_3, "Name3");
        R4 = new Resume(UUID_4, "Name4");

//        R1.addContact(ContactType.PHONE, "88005353535");
//        R2.addContact(ContactType.MOBILE, "84285653535");
//        R2.addContact(ContactType.HOME_PHONE, "891735532428");
//        R4.addContact(ContactType.SKYPE, "vasa.ivanov");
//        R3.addContact(ContactType.EMAIL, "vasa@mail.ru");
//        R2.addContact(ContactType.LINKEDIN, "профиль LINKEDIN");
//        R1.addContact(ContactType.GITHUB, "профиль GITHUB");
//        R3.addContact(ContactType.STATCKOVERFLOW, "профиль StackOverflow");
//        R4.addContact(ContactType.HABRAHABR, "профиль Habrahabr");
//        R1.addContact(ContactType.HOME_PHONE, "vasa.ivanov.ru");

//        R2.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
//        R3.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
//
//        R1.addSection(SectionType.ACHIEVEMENT, new ListSection(
//                "С 2013 года: разработка проектов \"Практика Java, разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven." +
//                        " Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\" и проведение по ним стажировок" +
//                        " и корпоративных обучений. Более 1000 выпускников.",
//                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
//                        "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
//                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
//                        "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
//                        "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."));
//
//        R4.addSection(SectionType.QUALIFICATIONS, new ListSection(
//                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
//                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
//                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB"
//        ));
//
//        R2.addSection(SectionType.EXPERIENCE, new OrganizationSection(
//                new Organization("Java Online Projects", null,
//                        new Organization.Position(13, Month.OCTOBER, "Практика:" +
//                                " Разработка Web приложения \"База данных резюме\"", "Объектная модель, коллекции, система ввода-вывода, работа с файлами," +
//                                " сериализация, работа с XML, JSON, SQL, персистентность в базу данных (PostgreSQL), сервлеты, JSP/JSTL, веб-контейнер Tomcat, HTML, " +
//                                "модульные тесты JUnit, java.util.Logging, система контроля версий Git."),
//                        new Organization.Position(13, Month.OCTOBER, "Стажировка Java Enterprise",
//                                "Разработка полнофункционального Spring/JPA Enterprise приложения c авторизацией и правами доступа на основе ролей с " +
//                                        "использованием наиболее популярных инструментов и технологий Java: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson)," +
//                                        " Bootstrap (css,js), datatables, jQuery + plugins, Java 8 Stream and Time API."))));
//
//
//        R3.addSection(SectionType.EDUCATION, new OrganizationSection(
//                new Organization("Alcatel", "http://www.alcatel.ru/",
//                        new Organization.Position(1997, Month.SEPTEMBER, 2005, Month.JANUARY,
//                                "\tИнженер по аппаратному и программному тестированию", "" +
//                                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."),
//                        new Organization.Position(1997, Month.JANUARY, 1998, Month.JANUARY,
//                                "6 месяцев обучения цифровым телефонным сетям (Москва)", ""
//                        ))));
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_1, "New Name");
        storage.update(newResume);
        assertTrue(newResume.equals(storage.get(UUID_1)));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        List<Resume> sortedResumes = Arrays.asList(R1, R2, R3);
        Collections.sort(sortedResumes);
        assertEquals(list, sortedResumes);
    }

    @Test
    public void save() throws Exception {
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(R1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}