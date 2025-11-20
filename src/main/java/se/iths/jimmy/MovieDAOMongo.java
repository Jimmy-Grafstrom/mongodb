package se.iths.jimmy;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MovieDAOMongo implements MovieDao {

    private final MongoCollection<Document> collection;

    public MovieDAOMongo(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
    public void insert(String title, int year) {
        collection.deleteOne(Filters.eq("title", title));

        Document doc = new Document("title", title)
                .append("year", year);
        InsertOneResult result = collection.insertOne(doc);

        if (result.wasAcknowledged()) {
            BsonValue insertedId = result.getInsertedId();
            System.out.println("Film tillagd med ID: " + insertedId);
        } else {
            System.out.println("Insättning misslyckades.");
        }
    }

    @Override
    public List<Document> findAll() {
        System.out.println("Hämtar alla filmer:");
        List<Document> allMovies = collection.find().into(new ArrayList<>());
        if (allMovies.isEmpty()) {
            System.out.println("Inga filmer hittades i collection.");
        } else {
            for (Document movie : allMovies) {
                System.out.println(movie.toJson());
            }
        }
        return allMovies;
    }

    @Override
    public Document findByTitle(String title) {
        System.out.println("Söker efter film...");
        Document foundMovie = collection.find(Filters.eq("title", title)).first();
        if (foundMovie != null) {
            System.out.println("Hittade filmen: " + foundMovie.toJson());
        } else {
            System.out.println("Filmen hittades inte.");
        }
        return foundMovie;
    }

    @Override
    public Document findAndUpdateYear(String title, int year) {
        Bson filter = Filters.eq("title", title);
        Bson update = Updates.set("year", year);

        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions()
                .returnDocument(ReturnDocument.AFTER);

        System.out.println("Uppdaterar år för: " + title);
        Document updatedMovie = collection.findOneAndUpdate(filter, update, options);

        if (updatedMovie != null) {
            System.out.println("uppdaterade: " + updatedMovie.toJson());
        } else {
            System.out.println("Hittar inte filmen");
        }
        return updatedMovie;
    }

    @Override
    public void findAndDeleteMovie(String title) {
        Bson filter = Filters.eq("title", title);
        DeleteResult deletedMovie = collection.deleteOne(filter);

        if (deletedMovie.getDeletedCount() > 0) {
            System.out.println("Tog bort filmen: " + title);
        } else {
            System.out.println("Ingen film hittad");
        }
    }
}
