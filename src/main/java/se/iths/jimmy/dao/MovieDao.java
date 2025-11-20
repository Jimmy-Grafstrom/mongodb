package se.iths.jimmy.dao;

import org.bson.Document;

import java.util.List;

public interface MovieDao<T> {
    void insert(T movie);

    List<T> findAll();

    T findByTitle(String title);

    T findAndUpdateYear(String title, int year);

    void findAndDeleteMovie(String title);
}
