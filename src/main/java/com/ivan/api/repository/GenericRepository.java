package com.ivan.api.repository;

import java.util.List;

public interface GenericRepository<T, I> {
    T getById(I id);

    List<T> getAll();

    T create(T t);

    T update(T t);

    void deleteById(I id);
}
