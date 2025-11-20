package se.iths.jimmy;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import se.iths.jimmy.dao.MoviesMovieDao;
import se.iths.jimmy.util.MongoDbConfig; // Import the new config class

public class Main {
    public static void main(String[] args) {
        try (MongoClient client = MongoClients.create(MongoDbConfig.CONNECTION_URI)) {
            MongoDatabase database = client.getDatabase(MongoDbConfig.DATABASE_NAME);
            System.out.println("Ansluten till databasen!");

            MoviesMovieDao movieDao = new MoviesMovieDao(database);

            movieDao.insert("Filmen om Lars", 1688);
            //movieDao.findAll();
            //movieDao.findAndUpdateYear("Sagan om de tre tornen", 1955);
            //movieDao.findByTitle("Sagan om de tre tornen");
           // movieDao.findAndDeleteMovie("Sagan om de tre tornen");

        } catch (Exception e) {
            System.err.println("Ett fel inträffade: " + e.getMessage());
            e.printStackTrace();
        }
    }
}