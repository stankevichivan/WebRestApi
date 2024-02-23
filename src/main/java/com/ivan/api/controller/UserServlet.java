package com.ivan.api.controller;

import com.google.gson.Gson;
import com.ivan.api.dto.user.UserDto;
import com.ivan.api.service.UserService;
import com.ivan.api.service.impl.UserServiceImpl;
import com.ivan.api.util.ServletUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/users")
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();
    private final Gson gson = new Gson();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var id = ServletUtil.getIdParam(request);
        if (id == 0) {
            List<UserDto> users = userService.getAll();
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(users);
            out.flush();
        } else {
            var user = userService.getById(id);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(user);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto user = gson.fromJson(request.getReader(), UserDto.class);
        userService.create(user);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("Save user ...");
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto user = gson.fromJson(request.getReader(), UserDto.class);
        var id = ServletUtil.getIdParam(request);
        userService.update(user, id);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("Update user ...");
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var id = ServletUtil.getIdParam(request);
        userService.deleteById(id);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("Delete user ...");
        out.flush();
    }
}
