package com.ivan.api.repository.impl;

import com.ivan.api.model.File;
import com.ivan.api.repository.FileRepository;
import com.ivan.api.util.HibernateUtil;
import java.util.List;

public class FileRepositoryImpl implements FileRepository {

  @Override
  public File getById(Long id) {
    return HibernateUtil.getCurrenntSession().get(File.class, id);
  }

  @Override
  public List<File> getAll() {
    return HibernateUtil.getCurrenntSession()
        .createQuery("select f from File f", File.class).list();
  }

  @Override
  public File create(File file) {
    throw new UnsupportedOperationException("operation update unsupported fo File");
  }

  @Override
  public File update(File file) {
    throw new UnsupportedOperationException("operation update unsupported fo File");
  }

  @Override
  public void deleteById(Long id) {
    var file = HibernateUtil.getCurrenntSession().get(File.class, id);
    HibernateUtil.getCurrenntSession().remove(file);
  }
}
