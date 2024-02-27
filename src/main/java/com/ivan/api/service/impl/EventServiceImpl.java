package com.ivan.api.service.impl;

import com.ivan.api.dto.event.EventDto;
import com.ivan.api.dto.file.FileDto;
import com.ivan.api.dto.user.UserDto;
import com.ivan.api.dto.user.UserStatus;
import com.ivan.api.model.Event;
import com.ivan.api.repository.EventRepository;
import com.ivan.api.repository.impl.EventRepositoryImpl;
import com.ivan.api.service.EventService;
import com.ivan.api.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Transaction;

public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository = new EventRepositoryImpl();

  @Override
  public EventDto getById(Long id) {
    Transaction transaction = null;
    try {
      var session = HibernateUtil.getCurrenntSession();
      transaction = session.beginTransaction();
      var eventDto = eventToEventDto(eventRepository.getById(id));
      transaction.commit();
      return eventDto;
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<EventDto> getAll() {
    Transaction transaction = null;
    try {
      var session = HibernateUtil.getCurrenntSession();
      transaction = session.beginTransaction();
      var eventDtoList = eventRepository.getAll().stream().map(this::eventToEventDto).toList();
      transaction.commit();
      return eventDtoList;
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  @Override
  public EventDto create(EventDto eventDto) {
    throw new UnsupportedOperationException("operation create unsupported fo Event");

  }

  @Override
  public EventDto update(EventDto eventDto, Long id) {
    throw new UnsupportedOperationException("operation update unsupported fo Event");

  }

  @Override
  public void deleteById(Long id) {
    throw new UnsupportedOperationException("operation deleteById unsupported fo Event");
  }

  private EventDto eventToEventDto(Event event) {
    return EventDto.builder()
        .id(event.getId())
        .type(event.getType())
        .userDto(getUserDtoFromEvent(event))
        .fileDto(getFileDtoFromEvent(event))
        .build();
  }

  private UserDto getUserDtoFromEvent(Event event) {
    var user = event.getUser();
    return UserDto.builder()
        .id(user.getId())
        .name(user.getName())
        .status(UserStatus.valueOf(user.getStatus()))
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
  }

  private FileDto getFileDtoFromEvent(Event event) {
    var file = event.getFile();
    return FileDto.builder()
        .id(file.getId())
        .fileName(file.getFileName())
        .filePath(file.getFilePath())
        .build();
  }
}
