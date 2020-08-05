package de.dragoncoder.dragonbot.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface DAO<T> {

    Logger logger = LoggerFactory.getLogger(DAO.class);

    void save(T t);
    void update(T t);
    T getByID(long ID);
    List<T> getAll();
    void delete(T t);
    void deleteList(List<T> t);
}
