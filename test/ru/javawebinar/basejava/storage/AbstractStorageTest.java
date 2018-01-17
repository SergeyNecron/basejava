package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.Month.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    private static List ACHIEVEMENT = new ArrayList<String>();
    private static List QUALIFICATIONS = new ArrayList<String>();
    private static List EXPERIENCE = new ArrayList<Organization>();
    private static List EDUCATION = new ArrayList<Organization>();

    //********************dateOrganizations***************
    private static List dateJavaOnlineProjects = new ArrayList<Organization>();
    private static List dateAlcatelJob = new ArrayList<Organization>();
    private static List dateAlcatelScience = new ArrayList<Organization>();
    //  private static List dateJavaOnlineProjects = new ArrayList<Organization>();

    static {
        RESUME_1 = new Resume(UUID_1, "Name1");
        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = new Resume(UUID_3, "Name3");
        RESUME_4 = new Resume(UUID_4, "Name4");

        ACHIEVEMENT.add("С 2013 года: разработка проектов \"Практика Java, разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven." +
                " Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\" и проведение по ним стажировок" +
                " и корпоративных обучений. Более 1000 выпускников.");
        ACHIEVEMENT.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        ACHIEVEMENT.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");

        QUALIFICATIONS.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        QUALIFICATIONS.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        QUALIFICATIONS.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");

        dateJavaOnlineProjects.add(new DateOrganization(DateUtil.of(13, OCTOBER), LocalDate.now(), "Практика:" +
                " Разработка Web приложения \"База данных резюме\"", "Объектная модель, коллекции, система ввода-вывода, работа с файлами," +
                " сериализация, работа с XML, JSON, SQL, персистентность в базу данных (PostgreSQL), сервлеты, JSP/JSTL, веб-контейнер Tomcat, HTML, " +
                "модульные тесты JUnit, java.util.Logging, система контроля версий Git."));
        dateJavaOnlineProjects.add(new DateOrganization(DateUtil.of(13, OCTOBER), LocalDate.now(), "Стажировка Java Enterprise",
                "Разработка полнофункционального Spring/JPA Enterprise приложения c авторизацией и правами доступа на основе ролей с " +
                        "использованием наиболее популярных инструментов и технологий Java: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson)," +
                        " Bootstrap (css,js), datatables, jQuery + plugins, Java 8 Stream and Time API."));

        dateAlcatelJob.add(new DateOrganization(DateUtil.of(1997, SEPTEMBER), DateUtil.of(2005, JANUARY),
                "\tИнженер по аппаратному и программному тестированию", "" +
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));

        dateAlcatelScience.add(new DateOrganization(DateUtil.of(1997), DateUtil.of(1998),
                "6 месяцев обучения цифровым телефонным сетям (Москва)", ""));

        RESUME_1.addContact(ContactType.PHONE, "88005353535");
        RESUME_2.addContact(ContactType.MOBILE, "84285653535");
        RESUME_2.addContact(ContactType.HOME_PHONE, "891735532428");
        RESUME_4.addContact(ContactType.SKYPE, "vasa.ivanov");
        RESUME_3.addContact(ContactType.EMAIL, "vasa@mail.ru");
        RESUME_2.addContact(ContactType.LINKEDIN, "профиль LINKEDIN");
        RESUME_1.addContact(ContactType.GITHUB, "профиль GITHUB");
        RESUME_3.addContact(ContactType.STATCKOVERFLOW, "профиль StackOverflow");
        RESUME_4.addContact(ContactType.HABRAHABR, "профиль Habrahabr");
        RESUME_1.addContact(ContactType.HOME_PHONE, "vasa.ivanov.ru");

        RESUME_2.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        RESUME_3.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));

        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection(ACHIEVEMENT));
        RESUME_4.addSection(SectionType.QUALIFICATIONS, new ListSection(QUALIFICATIONS));
        RESUME_2.addSection(SectionType.EXPERIENCE, new OrganizationSection(EXPERIENCE));
        RESUME_3.addSection(SectionType.EDUCATION, new OrganizationSection(EDUCATION));

        EXPERIENCE.add(new Organization("Java Online Projects", "http://javaops.ru/", dateJavaOnlineProjects));
        EXPERIENCE.add(new Organization("Alcatel", "http://www.alcatel.ru/", dateAlcatelJob));

        EDUCATION.add(new Organization("Alcatel", "http://www.alcatel.ru/", dateAlcatelScience));
    }

    Storage storage;

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
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
        assertTrue(newResume == storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
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
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
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
