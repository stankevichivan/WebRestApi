package com.ivan.api.service.impl;

import com.ivan.api.dto.file.FileDto;
import com.ivan.api.model.Event;
import com.ivan.api.model.File;
import com.ivan.api.repository.EventRepository;
import com.ivan.api.repository.FileRepository;
import com.ivan.api.repository.UserRepository;
import com.ivan.api.repository.impl.EventRepositoryImpl;
import com.ivan.api.repository.impl.FileRepositoryImpl;
import com.ivan.api.repository.impl.UserRepositoryImpl;
import com.ivan.api.service.FileService;
import com.ivan.api.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Transaction;

public class FileServiceImpl implements FileService {

  private static final String filePath = "C:/Users/ivans/IdeaProjects/WebRestApi/src/main/resources/files/";

  private final FileRepository fileRepository = new FileRepositoryImpl();
  private final UserRepository userRepository = new UserRepositoryImpl();
  private final EventRepository eventRepository = new EventRepositoryImpl();
  private java.io.File fileIo;

  @Override
  public FileDto getById(Long id) {
    Transaction transaction = null;
    try {
      var session = HibernateUtil.getCurrenntSession();
      transaction = session.beginTransaction();
      var fileDto = getFileDtoFromFile(fileRepository.getById(id));
      transaction.commit();
      return fileDto;
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<FileDto> getAll() {
    Transaction transaction = null;
    try {
      var session = HibernateUtil.getCurrenntSession();
      transaction = session.beginTransaction();
      var files = fileRepository.getAll();
      var fileDtoList = files.stream().map(FileServiceImpl::getFileDtoFromFile).toList();
      transaction.commit();
      return fileDtoList;
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  @Override
  public FileDto create(FileDto fileDto) {
    throw new UnsupportedOperationException("operation create unsupported fo File");
  }

  @Override
  public FileDto update(FileDto fileDto, Long id) {
    throw new UnsupportedOperationException("operation update unsupported fo File");

  }

  @Override
  public void deleteById(Long id) {
    Transaction transaction = null;
    try {
      var session = HibernateUtil.getCurrenntSession();
      transaction = session.beginTransaction();
      fileRepository.deleteById(id);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public FileDto uploadFile(HttpServletRequest request, long userId) {
    Transaction transaction = null;
    try {
      var session = HibernateUtil.getCurrenntSession();
      transaction = session.beginTransaction();
      saveFileOnDisk(request);
      var event = eventRepository.create(createEvent(userId));
      var fileDto = FileDto.builder()
          .filePath(event.getFile().getFilePath())
          .fileName(event.getFile().getFileName())
          .id(event.getFile().getId())
          .build();
      transaction.commit();
      return fileDto;
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
      return null;
    }
  }

  private Event createEvent(long userId) {
    var filePath = fileIo.getAbsolutePath();
    var fileName = FilenameUtils.getBaseName(fileIo.getName());
    var type = FilenameUtils.getExtension(fileIo.getName());
    var user = userRepository.getById(userId);
    var file = new File();
    file.setFileName(fileName);
    file.setFilePath(filePath);
    var event = new Event();
    event.setFile(file);
    event.setUser(user);
    event.setType(type);
    return event;
  }

  private void saveFileOnDisk(HttpServletRequest request) throws Exception {
    var diskFileItemFactory = new DiskFileItemFactory();
    diskFileItemFactory.setRepository(new java.io.File(filePath));
    var servletFileUpload = new ServletFileUpload(diskFileItemFactory);
    var fileItems = servletFileUpload.parseRequest(request);
    for (FileItem fileItem : fileItems) {
      if (!fileItem.isFormField()) {
        var fileName = fileItem.getName();
        if (fileName.lastIndexOf("\\") >= 0) {
          fileIo = new java.io.File(filePath +
              fileName.substring(fileName.lastIndexOf("\\")));
        } else {
          fileIo = new java.io.File(filePath +
              fileName.substring(fileName.lastIndexOf("\\") + 1));
        }
      }
      fileItem.write(fileIo);
    }
  }

  private static FileDto getFileDtoFromFile(File file) {
    return FileDto.builder()
        .id(file.getId())
        .fileName(file.getFileName())
        .filePath(file.getFilePath())
        .build();
  }
}
