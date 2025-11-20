package se.iths.jimmy;

import org.bson.Document;

import java.util.List;

public interface MovieDao {
    void insert(String title, int year);

    List<Document> findAll();

    Document findByTitle(String title);

    Document findAndUpdateYear(String title, int year);

    void findAndDeleteMovie(String title);
}
