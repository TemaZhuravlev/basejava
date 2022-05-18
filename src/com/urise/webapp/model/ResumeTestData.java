package com.urise.webapp.model;

import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume1 = new Resume("uuid1", "Григорий Кислин");
        /**
         * Block contacts
         */
        resume1.getContacts().put(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume1.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        resume1.getContacts().put(ContactType.E_MAIL, "gkislin@yandex.ru");
        resume1.getContacts().put(ContactType.LINKEDIN, "Профиль LinkedIn");
        resume1.getContacts().put(ContactType.GITHUB, "Профиль GitHub");
        resume1.getContacts().put(ContactType.STACKOVERFLOW, "Профиль Stackoverflow");
        resume1.getContacts().put(ContactType.HOME_PAGE, "Домашняя страница");

        /**
         * Block sections
         */
        resume1.getSections().put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям."));
        resume1.getSections().put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume1.getSections().put(SectionType.ACHIEVEMENT, new ListSection("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, " +
                "многомодульный Spring Boot + Vaadin проект для комплексных DIY смет",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
                        "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников. ",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."
        ));
        resume1.getSections().put(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce ",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB ",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy ",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts ",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), " +
                        "JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements). "
        ));
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
            section.getValue().showInfo();
            System.out.println();
        }
    }
}
