package com.empresa.dao;

import java.util.List;

public interface DAO<T> {
    void insert(T obj);
    void update(T obj);
    void delete(long id);
    T getById(long id);
    List<T> getAll();
}
