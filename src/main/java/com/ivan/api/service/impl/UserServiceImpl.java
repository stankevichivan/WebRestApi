package com.ivan.api.service.impl;

import com.ivan.api.dto.user.UserDto;
import com.ivan.api.dto.user.UserStatus;
import com.ivan.api.model.User;
import com.ivan.api.repository.UserRepository;
import com.ivan.api.repository.impl.UserRepositoryImpl;
import com.ivan.api.service.UserService;
import com.ivan.api.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Transaction;

public class UserServiceImpl implements UserService {

  private final UserRepository userRepository = new UserRepositoryImpl();

  @Override
  public UserDto getById(Long id) {
    Transaction transaction = null;
    try {
      var session = HibernateUtil.getCurrenntSession();
      transaction = session.beginTransaction();
      var user = userRepository.getById(id);
      var userDtoFromUser = createUserDtoFromUser(user);
      transaction.commit();
      return userDtoFromUser;
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<UserDto> getAll() {
    Transaction transaction = null;
    try {
      var session = HibernateUtil.getCurrenntSession();
      transaction = session.beginTransaction();
      var userList = userRepository.getAll();
      var userDtoList = userList.stream()
          .map(UserServiceImpl::createUserDtoFromUser)
          .toList();
      transaction.commit();
      return userDtoList;
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  @Override
  public UserDto create(UserDto userDto) {
    Transaction transaction = null;
    try {
      var session = HibernateUtil.getCurrenntSession();
      transaction = session.beginTransaction();
      User user = new User();
      user.setStatus(UserStatus.NEW.name());
      user.setName(userDto.name());
      var userDtoFromUser = createUserDtoFromUser(userRepository.create(user));
      transaction.commit();
      return userDtoFromUser;
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public UserDto update(UserDto userDto, Long id) {
    Transaction transaction = null;
    try {
      var session = HibernateUtil.getCurrenntSession();
      transaction = session.beginTransaction();
      var user = new User();
      user.setId(id);
      user.setName(userDto.name());
      user.setStatus(UserStatus.UPDATED.name());
      var userDtoFromUser = createUserDtoFromUser(userRepository.update(user));
      transaction.commit();
      return userDtoFromUser;
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void deleteById(Long id) {
    Transaction transaction = null;
    try {
      var session = HibernateUtil.getCurrenntSession();
      transaction = session.beginTransaction();
      userRepository.deleteById(id);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  private static UserDto createUserDtoFromUser(User user) {
    return UserDto.builder()
        .id(user.getId())
        .name(user.getName())
        .status(UserStatus.valueOf(user.getStatus()))
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
  }
}
