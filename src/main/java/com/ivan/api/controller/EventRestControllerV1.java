package com.ivan.api.controller;

import com.ivan.api.dto.event.EventDto;
import com.ivan.api.service.EventService;
import com.ivan.api.service.impl.EventServiceImpl;
import com.ivan.api.util.HibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;

@WebServlet("/api/v1/events")
public class EventRestControllerV1 extends HttpServlet {

  private final EventService eventService = new EventServiceImpl();

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try (Session session = HibernateUtil.openSession()) {
      var paramId = request.getParameter("id");
      long id = paramId == null ? 0 : Long.parseLong(paramId);
      if (id == 0) {
        List<EventDto> events = eventService.getAll();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(events);
        out.flush();
      } else {
        var u = eventService.getById(id);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(u);
        out.flush();
      }
    } finally {
      HibernateUtil.shutDown();
    }

  }
}
