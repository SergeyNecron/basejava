package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    // protected Storage storage = Config.get().getStorage();
    protected Storage storage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        List<Resume> listResume = storage.getAllSorted();

        List<String> listFullName = new ArrayList<>();
        for (Resume resume : listResume) {
            listFullName.add(resume.getFullName());
        }
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');
        for (String fullName : listFullName) {
            response.getWriter().println(fullName + "\n");
        }
    }
}
