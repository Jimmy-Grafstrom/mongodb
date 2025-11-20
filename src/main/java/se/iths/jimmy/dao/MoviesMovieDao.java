package se.iths.jimmy.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import se.iths.jimmy.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesMovieDao implements MovieDao<Movie> {
    private final MongoCollection<Document> collection;

    public MoviesMovieDao(MongoDatabase database) {
        this.collection = database.getCollection("movies");
    }

    @Override
    public void insert(Movie movie) {
        Bson filter = Filters.eq("title", movie.getTitle());
        if (collection.find(filter).first() != null) {
            collection.findOneAndReplace(filter, movie.toDocument());
        } else {
            collection.insertOne(movie.toDocument());
        }
    }

    @Override
    public List<Movie> findAll() {
        List<Document> moviesDocs = collection.find().into(new ArrayList<>());
        return moviesDocs.stream()
                .map(Movie::fromDocument)
                .toList();
    }

    @Override
    public Movie findByTitle(String title) {
        Bson filter = Filters.eq("title", title);
        Document movieDoc = collection.find(filter).first();
        if (movieDoc != null) {
            return Movie.fromDocument(movieDoc);
        } else {
            return null;
        }
    }

    @Override
    public Movie findAndUpdateYear(String title, int newYear) {
        Bson filter = Filters.eq("title", title);
        Bson update = Updates.set("year", newYear);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions()
                .returnDocument(ReturnDocument.AFTER);

        Document updatedMovie = collection.findOneAndUpdate(filter, update, options);

        if (updatedMovie != null) {
            return Movie.fromDocument(updatedMovie);
        } else {
            return null;
        }
    }

    @Override
    public void findAndDeleteMovie(String title) {
        Bson filter = Filters.eq("title", title);
        collection.deleteOne(filter);
    }

}
