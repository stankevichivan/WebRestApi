package com.ivan.api.service;

import java.util.List;

public interface GenericService<T, I> {
    T getById(I id);

    List<T> getAll();

    T create(T t);

    T update(T t, I id);

    void deleteById(I id);
}
