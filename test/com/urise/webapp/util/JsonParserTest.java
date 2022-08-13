package com.urise.webapp.util;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class JsonParserTest {
    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();

    private static final String FULL_NAME_1 = "Ivanov Ivan";
    private static final String FULL_NAME_2 = "Petrov Petr";
    private static final String FULL_NAME_3 = "Petrov Petr";

    protected static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, FULL_NAME_1);
    protected static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, FULL_NAME_2);
    protected static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, FULL_NAME_3);

    @Test
    public void testReadWrite() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void writeSection() {
        AbstractSection section1 =new TextSection("Objective1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1,section2);
    }
}