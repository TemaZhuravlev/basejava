package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write("<link rel=\"stylesheet\" href=\"css/style.css\">");

        String uuid = request.getParameter("uuid");
        response.getWriter().write("<body><table><tr><th>uuid</th><th>full name</th></tr>");
        if (uuid != null) {
            Resume resume = sqlStorage.get(uuid);
            response.getWriter().write("<tr><td>" + resume.getUuid() + "</td><td>" + resume.getFullName() + "</td></tr>");
        } else {
            List<Resume> resumes = sqlStorage.getAllSorted();
            for (Resume resume : resumes) {
                response.getWriter().write("<tr><td>" + resume.getUuid() + "</td><td>" + resume.getFullName() + "</td></tr>");
            }
        }
        response.getWriter().write("</table></body>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
