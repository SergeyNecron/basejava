package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    protected Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        List<Resume> listResume = storage.getAllSorted();

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        //  String name = request.getParameter("name");
        //  writer.write(name == null ? "Hello Resumes!" : "Hello " + name + '!');
        PrintWriter writer = response.getWriter();
        writer.println("<h3>Список резюме:</h3>");
        writer.println("<table border='1' cellpadding='8' cellspacing='0'>");
        writer.println("<tr><th>UUID</th><th>Full Name</th></tr>");
        for (Resume resume : listResume) {
            writer.println("<tr><td>" + resume.getUuid() + "</td><td>" + resume.getFullName() + "</td><</tr>");
        }
        writer.println("</table>");
    }
}
