package com.ivan.api.repository.impl;

import com.ivan.api.model.Event;
import com.ivan.api.repository.EventRepository;
import com.ivan.api.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    @Override
    public Event getById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();
            String hql = """
                    select e from Event e
                    join fetch e.file f
                    join fetch e.user u
                    where e.id = :eventId
                    """;

            var event = session.createQuery(hql, Event.class)
                    .setParameter("eventId", id)
                    .getSingleResult();

            transaction.commit();
            return event;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Event> getAll() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();
            String hql = """
                    select distinct e from Event e
                    join fetch e.file f
                    join fetch e.user u
                    order by u.name
                    """;
            var eventList = session.createQuery(hql, Event.class).list();
            transaction.commit();
            return eventList;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Event create(Event event) {
        throw new UnsupportedOperationException("operation create unsupported fo Event");
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
