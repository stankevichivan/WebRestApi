package com.ivan.api.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ivan.api.dto.user.UserDto;
import com.ivan.api.service.UserService;
import com.ivan.api.service.impl.UserServiceImpl;
import com.ivan.api.util.HibernateUtil;
import com.ivan.api.util.LocalDateTypeAdapter;
import com.ivan.api.util.ServletUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;

@WebServlet("/api/v1/users")
public class UserRestControllerV1 extends HttpServlet {

  private final UserService userService = new UserServiceImpl();
  private final Gson gson = new GsonBuilder()
      .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
      .create();

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try (Session session = HibernateUtil.openSession()) {
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
    } finally {
      HibernateUtil.shutDown();
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    try (Session session = HibernateUtil.openSession()) {
      UserDto user = gson.fromJson(request.getReader(), UserDto.class);
      var userDto = userService.create(user);
      response.setContentType("application/json");
      PrintWriter out = response.getWriter();
      out.print(userDto);
      out.flush();
    } finally {
      HibernateUtil.shutDown();
    }
  }

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    try (Session session = HibernateUtil.openSession()) {
      UserDto user = gson.fromJson(request.getReader(), UserDto.class);
      var id = ServletUtil.getIdParam(request);
      var userDto = userService.update(user, id);
      response.setContentType("application/json");
      PrintWriter out = response.getWriter();
      out.print(userDto);
      out.flush();
    } finally {
      HibernateUtil.shutDown();
    }
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    try (Session session = HibernateUtil.openSession()) {
      var id = ServletUtil.getIdParam(request);
      userService.deleteById(id);
      response.setContentType("application/json");
      PrintWriter out = response.getWriter();
      out.print("Delete user ...");
      out.flush();
    } finally {
      HibernateUtil.shutDown();
    }
  }
}
