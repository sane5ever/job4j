package ru.job4j.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.job4j.servlets.Gender.FEMALE;
import static ru.job4j.servlets.Gender.MALE;

public class EchoServlet extends HttpServlet {
    private static final ObjectMapper JSON = new ObjectMapper();
    private final AtomicInteger counter = new AtomicInteger();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        User first = new User(counter.incrementAndGet(), "john@example.com", "password", MALE, "Example");
        User second = new User(counter.incrementAndGet(), "mary@example.com", "password", FEMALE, "Description");
        users.put(first.getId(), first);
        users.put(second.getId(), second);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSON.writeValue(response.getWriter(), users.values());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        User user = JSON.readValue(reader, User.class);
        user.setId(counter.incrementAndGet());
        users.put(user.getId(), user);
        JSON.writeValue(response.getWriter(), user);
    }
}
