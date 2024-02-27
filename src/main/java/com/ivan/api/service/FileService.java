package com.ivan.api.service;

import com.ivan.api.dto.file.FileDto;
import com.ivan.api.model.File;

import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;

public interface FileService extends GenericService<FileDto, Long> {
  FileDto uploadFile(HttpServletRequest inputStream, long userId);
}
