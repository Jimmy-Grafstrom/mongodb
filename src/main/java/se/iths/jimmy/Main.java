package se.iths.jimmy;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Main {
    public static void main(String[] args) {
        String uri = "mongodb+srv://jimmygrafstrom_db_user:SfxtvbgipWb16MpO@clusterjimmy.5bingbq.mongodb.net/?appName=ClusterJimmy";

        try (MongoClient client = MongoClients.create(uri)) {
            MongoDatabase database = client.getDatabase("Student");
            MongoCollection<Document> collection = database.getCollection("Movies");
            System.out.println("Ansluten till databasen!");
            MovieDao movieDao = new MovieDAOMongo(collection);


            movieDao.insert("Sagan om de tre tornen", 1938);
            movieDao.findAll();
            movieDao.findAndUpdateYear("Sagan om de tre tornen", 1955);
            movieDao.findByTitle("Sagan om de tre tornen");

            movieDao.findAndDeleteMovie("Sagan om de tre tornen");

        } catch (Exception e) {
            System.err.println("Ett fel inträffade: " + e.getMessage());
            e.printStackTrace();
        }
    }
}