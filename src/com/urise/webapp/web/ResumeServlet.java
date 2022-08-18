package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage sqlStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = sqlStorage.get(uuid);
                break;
            case "edit":
                r = sqlStorage.get(uuid);
                if(r.getSection(SectionType.EXPERIENCE) == null){
                    r.addSection(SectionType.EXPERIENCE, OrganizationSection.EMPTY);
                }
                if(r.getSection(SectionType.EDUCATION) == null){
                    r.addSection(SectionType.EDUCATION, OrganizationSection.EMPTY);
                }
                break;
            case "add":
                r = new Resume();
                r.addSection(SectionType.EXPERIENCE, OrganizationSection.EMPTY);
                r.addSection(SectionType.EDUCATION, OrganizationSection.EMPTY);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        boolean isEmptyUuid = uuid == null || uuid.trim().length() == 0;
        if (isEmptyUuid) {
            r = new Resume(fullName);
        } else {
            r = sqlStorage.get(uuid);
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
            if (value != null && value.trim().length() != 0) {
                switch (type.name()) {
                    case "OBJECTIVE", "PERSONAL" -> {
                        r.addSection(type, new TextSection(value));
                    }
                    case "ACHIEVEMENT", "QUALIFICATIONS" -> {
                        List<String> list = new ArrayList<>(Arrays.asList(value.split("\r\n")));
                        list.removeIf(String::isEmpty);
                        r.addSection(type, new ListSection(list));
                    }
                    case "EXPERIENCE", "EDUCATION" -> {
                        String[] names = request.getParameterValues(type.name());
                        String[] urls = request.getParameterValues(type.name() + "url");
                        List<Organization> organizationList = new ArrayList<>();
                        for (int i = 0; i < names.length; i++) {
                            Link link = new Link(names[i], urls[i]);
                            String[] datesFrom = request.getParameterValues(type.name() + "dateFrom"+ i);
                            String[] datesTo = request.getParameterValues(type.name() + "dateTo"+ i);
                            String[] titles = request.getParameterValues(type.name() + "title"+ i);
                            String[] descriptions = request.getParameterValues(type.name() + "description"+ i);
                            List<Period> periods = new ArrayList<>();
                            for (int j = 0; j < datesFrom.length; j++) {
                                periods.add(new Period(parseDate(datesFrom[j]), parseDate(datesTo[j]), titles[j], descriptions[j]));
                            }
                            organizationList.add(new Organization(names[i], link, periods));
                        }
                        r.addSection(type, new OrganizationSection(organizationList));
                    }
                }
            } else {
                r.getSections().remove(type);
            }
        }
        if (isEmptyUuid) {
            sqlStorage.save(r);
        } else {
            sqlStorage.update(r);
        }
        response.sendRedirect("resume");
    }

    private LocalDate parseDate(String date) {
        if(date == null || date.trim().length() == 0) return DateUtil.EMPTY;
        int[] ints = Arrays.stream((date.split("-")))
                .mapToInt(a -> Integer.parseInt(a))
                .toArray();
        return LocalDate.of(ints[0], ints[1], ints[2]);
    }
}
