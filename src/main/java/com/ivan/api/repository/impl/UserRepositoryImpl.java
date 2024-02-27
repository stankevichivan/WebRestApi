package com.ivan.api.repository.impl;

import com.ivan.api.model.User;
import com.ivan.api.repository.UserRepository;
import com.ivan.api.util.HibernateUtil;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

  @Override
  public User getById(Long id) {
    return HibernateUtil.getCurrenntSession().get(User.class, id);
  }

  @Override
  public List<User> getAll() {
    return HibernateUtil.getCurrenntSession()
        .createQuery("select a from User a", User.class).list();
  }

  @Override
  public User create(User user) {
    HibernateUtil.getCurrenntSession().persist(user);
    return user;
  }

  @Override
  public User update(User user) {
    return HibernateUtil.getCurrenntSession().merge(user);
  }

  @Override
  public void deleteById(Long id) {
    User user = HibernateUtil.getCurrenntSession().get(User.class, id);
    HibernateUtil.getCurrenntSession().remove(user);
  }
}
