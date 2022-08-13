package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.Map;

public class ResumeTestData {
    public static Resume createResume(String uuid, String fullName) {

        Resume resume = new Resume(uuid, fullName);
        /**
         * Block contacts
         */
        resume.getContacts().put(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContacts().put(ContactType.E_MAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.LINKEDIN, "Профиль LinkedIn");
        resume.getContacts().put(ContactType.GITHUB, "Профиль GitHub");
        resume.getContacts().put(ContactType.STACKOVERFLOW, "Профиль Stackoverflow");
        resume.getContacts().put(ContactType.HOME_PAGE, "Домашняя страница");
        /**
         * Block sections
         */
        resume.getSections().put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного " +
                "обучения по Java Web и Enterprise технологиям."));
        resume.getSections().put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика," +
                " креативность, инициативность. Пурист кода и архитектуры."));
        resume.getSections().put(SectionType.ACHIEVEMENT, new ListSection("Организация команды и успешная " +
                "реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов " +
                "на Spring Boot, участие в проекте МЭШ на Play-2, " +
                "многомодульный Spring Boot + Vaadin проект для комплексных DIY смет",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный " +
                        "maven. Многопоточность. XML (JAXB/StAX). " +
                        "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн " +
                        "стажировок и ведение проектов. Более 3500 выпускников. ",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с " +
                        "Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."
        ));
        resume.getSections().put(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), " +
                "OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce ",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB ",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy ",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts ",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, " +
                        "Data, Clouds, Boot), " +
                        "JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, " +
                        "Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements). "
        ));
        resume.getSections().put(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("Java Online Projects", "http://javaops.ru/",
                        new Period(2013, Month.OCTOBER, "Автор проекта", "Создание, " +
                                "организация и проведение Java онлайн проектов и стажировок.")),
                new Organization("Wrike", "https://www.wrike.com/",
                        new Period(2014, Month.OCTOBER, 2016, Month.JANUARY, "Старший разработчик (backend)",
                                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."))
        ));
        resume.getSections().put(SectionType.EDUCATION, new OrganizationSection(
                new Organization("Coursera", "https://www.coursera.org/course/progfun",
                        new Period(2013, Month.MARCH, 2013, Month.MAY, "'Functional Programming Principles " +
                                "in Scala' by Martin Odersky", "")),
                new Organization("Санкт-Петербургский национальный исследовательский университет информационных " +
                        "технологий, механики и оптики", "http://www.ifmo.ru/",
                        new Period(1993, Month.SEPTEMBER, 1996, Month.JULY, "Аспирантура (программист С, С++)", ""),
                        new Period(1987, Month.SEPTEMBER, 1993, Month.JULY, "Инженер (программист Fortran, C)", ""))
        ));
        return resume;
    }

    public static void main(String[] args) {
        Resume resume1 = createResume("uuid1", "Григорий Кислин");
        /**
         * Print
         */
        System.out.println(resume1.getFullName());
        System.out.println();
        for (Map.Entry<ContactType, String> contact : resume1.getContacts().entrySet()) {
            System.out.println(contact.getKey().getTitle() + ": " + contact.getValue());
        }
        System.out.println("----------------------------------------------------");
        for (Map.Entry<SectionType, AbstractSection> section : resume1.getSections().entrySet()) {
            System.out.println(section.getKey().getTitle());
            System.out.println(section.getValue());
            System.out.println();
        }
    }
}
