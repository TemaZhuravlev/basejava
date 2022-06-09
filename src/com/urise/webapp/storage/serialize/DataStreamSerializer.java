package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializeStrategy {

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
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL -> {
                        TextSection textSection = (TextSection) entry.getValue();
                        dos.writeUTF(textSection.getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = (ListSection) entry.getValue();
                        List<String> list = listSection.getElements();
                        dos.writeInt(list.size());
                        for (String str : list) {
                            dos.writeUTF(str);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
                        List<Organization> organizationList = organizationSection.getOrganizations();
                        dos.writeInt(organizationList.size());
                        for (Organization organization : organizationList) {
                            List<Period> organizationPeriods = organization.getPeriods();
                            dos.writeInt(organizationPeriods.size());
                            for (Period period : organizationPeriods) {
                                writeLocalDate(dos, period.getDateFrom());
                                writeLocalDate(dos, period.getDateTo());
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
                            }
                            dos.writeUTF(organization.getName());
                            Link homePage = organization.getHomePage();
                            dos.writeUTF(homePage.getTitle());
                            dos.writeUTF(homePage.getUrl());

                        }
                    }
                }
            }
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonthValue());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSection = dis.readInt();
            for (int i = 0; i < sizeSection; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> {
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> elements = new ArrayList<>();
                        int sizeElements = dis.readInt();
                        for (int j = 0; j < sizeElements; j++) {
                            elements.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(elements));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizationList = new ArrayList<>();
                        int sizeOrganizations = dis.readInt();
                        for (int k = 0; k < sizeOrganizations; k++) {
                            List<Period> periodsList = new ArrayList<>();
                            int sizePeriod = dis.readInt();
                            for (int j = 0; j < sizePeriod; j++) {
                                periodsList.add(new Period(readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()));
                            }
                            organizationList.add(new Organization(dis.readUTF(), new Link(dis.readUTF(), dis.readUTF()), periodsList));
                        }
                        resume.addSection(sectionType, new OrganizationSection(organizationList));
                    }
                }
            }
            return resume;
        }
    }
}
