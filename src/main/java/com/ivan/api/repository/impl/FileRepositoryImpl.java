package com.ivan.api.repository.impl;

import com.ivan.api.model.File;
import com.ivan.api.repository.FileRepository;
import com.ivan.api.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class FileRepositoryImpl implements FileRepository {

    @Override
    public File getById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();
            var file = session.get(File.class, id);
            transaction.commit();
            return file;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<File> getAll() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();
            var fileList = session.createQuery("from File", File.class).list();
            transaction.commit();
            return fileList;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public File create(File file) {
        throw new UnsupportedOperationException("operation create unsupported fo File");
    }

    @Override
    public File update(File file) {
        throw new UnsupportedOperationException("operation update unsupported fo File");
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            var file = getById(id);
            if (file != null) {
                transaction = session.beginTransaction();
                session.remove(file);
                transaction.commit();
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
