package se.iths.jimmy.dao;

import com.mongodb.client.MongoCollection;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
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
    public void insert(String title, int year) {
        System.out.println("Lägger till: '" + title + "' var god vänta...");
        collection.deleteOne(Filters.eq("title", title));

        Movie movie = new Movie(title, year, new ArrayList<>());
        Document doc = movie.toDocument();
        collection.insertOne(doc);

        System.out.println("Film: '" + title + "' tillagd i movies-collection.");
    }

    @Override
    public List<Movie> findAll() {
        System.out.println("Söker och listar befintliga filmer...");
        List<Document> moviesDocs = collection.find().into(new ArrayList<>());
        if (moviesDocs.isEmpty()) {
            System.out.println("Inga filmer hittades");
            return new ArrayList<>();
        }
        List<Movie> movies = moviesDocs.stream()
                .map(Movie::fromDocument)
                .toList();
        movies.forEach(movie -> System.out.println(movie.toDocument().toJson()));
        return movies;
    }

    @Override
    public Movie findByTitle(String title) {
        System.out.println("Söker efter: '" + title + "'...");
        Bson filter = Filters.eq("title", title);
        Document movieDoc = collection.find(filter).first();
        if (movieDoc != null) {
            System.out.println("Film hittad: '" + movieDoc.toJson() + "'");
            return Movie.fromDocument(movieDoc);
        } else {
            System.out.println("Filmen: " + title + " hittades inte...");
            return null;
        }
    }

    @Override
    public Movie findAndUpdateYear(String title, int year) {
        System.out.println("Uppdaterar år för: '" + title + "'");

        Bson filter = Filters.eq("title", title);
        Bson update = Updates.set("year", year);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions()
                .returnDocument(ReturnDocument.AFTER);

        Document updatedMovie = collection.findOneAndUpdate(filter, update, options);

        if (updatedMovie != null) {
            System.out.println("Film uppdaterades: '" + updatedMovie.toJson() + "'");
            return Movie.fromDocument(updatedMovie);
        } else {
            System.out.println("Hittar inte filmen");
            return null;
        }
    }

    @Override
    public void findAndDeleteMovie(String title) {
        System.out.println("Tar bort: '" + title + "'");
        Bson filter = Filters.eq("title", title);
        DeleteResult deletedMovie = collection.deleteOne(filter);

        if (deletedMovie.getDeletedCount() > 0) {
            System.out.println("Tog bort filmen: '" + title + "'");
        } else {
            System.out.println("Ingen film hittad");
        }
    }
}
