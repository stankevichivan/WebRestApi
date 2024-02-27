package com.ivan.api.controller;

import com.ivan.api.dto.file.FileDto;
import com.ivan.api.service.FileService;
import com.ivan.api.service.impl.FileServiceImpl;
import com.ivan.api.util.HibernateUtil;
import com.ivan.api.util.ServletUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;

@WebServlet("/api/v1/files")
public class FileRestControllerV1 extends HttpServlet {

  private final FileService fileService = new FileServiceImpl();

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try (Session session = HibernateUtil.openSession()) {
      var id = ServletUtil.getIdParam(request);
      if (id == 0) {
        List<FileDto> files = fileService.getAll();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(files);
        out.flush();
      } else {
        var file = fileService.getById(id);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(file);
        out.flush();
      }
    } finally {
      HibernateUtil.shutDown();
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    try (Session session = HibernateUtil.openSession()) {
      var userId = Integer.parseInt(request.getHeader("user_id"));
      var file = fileService.uploadFile(request, 3L);
      response.setContentType("application/json");
      PrintWriter out = response.getWriter();
      out.print(file);
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
      fileService.deleteById(id);
      response.setContentType("application/json");
      PrintWriter out = response.getWriter();
      out.print("Delete file ...");
      out.flush();
    } finally {
      HibernateUtil.shutDown();
    }
  }
}
