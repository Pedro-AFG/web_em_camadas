package com.empresa.dao;

import java.util.List;

public interface DAO<T> {
    T insert(T obj);
    T update(T obj);
    void delete(long id);
    T getById(long id);
    List<T> getAll();
}
