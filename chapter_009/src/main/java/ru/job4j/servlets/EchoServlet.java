package ru.job4j.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

public class EchoServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding(UTF_8.name());
        PrintWriter writer = response.getWriter();
        writer.append("hello world");
        writer.flush();
    }
}
