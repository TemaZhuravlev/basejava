package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements SerializeStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeCollection(dos, r.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeCollection(dos, r.getSections().entrySet(), section -> {
                AbstractSection sectionValue = section.getValue();
                dos.writeUTF(section.getKey().name());
                switch (section.getKey()) {
                    case OBJECTIVE, PERSONAL -> {
                        dos.writeUTF(((TextSection) sectionValue).getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        writeCollection(dos, ((ListSection) sectionValue).getElements(), dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        writeCollection(dos, ((OrganizationSection) sectionValue).getOrganizations(), organization -> {
                            dos.writeUTF(organization.getName());
                            Link homePage = organization.getHomePage();
                            dos.writeUTF(homePage.getTitle());
                            dos.writeUTF(homePage.getUrl());
                            writeCollection(dos, organization.getPeriods(), period -> {
                                writeLocalDate(dos, period.getDateFrom());
                                writeLocalDate(dos, period.getDateTo());
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
                            });
                        });
                    }
                }
            });
        }
    }

    private interface RuleWrite<T> {
        void accept(T t) throws IOException;
    }

    private interface RuleReadElement {
        void apply() throws IOException;
    }

    private interface RuleReadList<T> {
        T get() throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, RuleWrite<T> ruleWrite) throws IOException {
        dos.writeInt(collection.size());
        for (T entry : collection) {
            ruleWrite.accept(entry);
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonthValue());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private void readElement(DataInputStream dis, RuleReadElement ruleReadElement) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            ruleReadElement.apply();
        }
    }

    private <T> List<T> readList(DataInputStream dis, RuleReadList<T> ruleReadList) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(ruleReadList.get());
        }
        return list;
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readElement(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readElement(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> {
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        resume.addSection(sectionType, new ListSection(readList(dis, dis::readUTF)));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        resume.addSection(sectionType, new OrganizationSection(readList(dis, () ->
                                new Organization(dis.readUTF(),
                                        new Link(dis.readUTF(), dis.readUTF()), readList(dis, () ->
                                        new Period(readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF())
                                ))
                        )));
                    }
                }
            });
            return resume;
        }
    }
}
