package com.ivan.api.repository.impl;

import com.ivan.api.model.Event;
import com.ivan.api.repository.EventRepository;
import com.ivan.api.util.HibernateUtil;
import java.util.List;

public class EventRepositoryImpl implements EventRepository {

  @Override
  public Event getById(Long id) {
    String hql = """
        select e from Event e
        join fetch e.file f
        join fetch e.user u
        where e.id = :eventId
        """;
    return HibernateUtil.getCurrenntSession().createQuery(hql, Event.class)
        .setParameter("eventId", id)
        .getSingleResult();
  }

  @Override
  public List<Event> getAll() {
    String hql = """
        select distinct e from Event e
        join fetch e.file f
        join fetch e.user u
        order by u.name
        """;
    return HibernateUtil.getCurrenntSession().createQuery(hql, Event.class).list();
  }

  @Override
  public Event create(Event event) {
    HibernateUtil.getCurrenntSession().persist(event);
    return event;
  }

  @Override
  public Event update(Event event) {
    throw new UnsupportedOperationException("operation update unsupported fo Event");
  }

  @Override
  public void deleteById(Long id) {
    throw new UnsupportedOperationException("operation deleteById unsupported fo Event");
  }
}
