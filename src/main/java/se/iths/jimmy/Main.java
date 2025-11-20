package se.iths.jimmy;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import se.iths.jimmy.dao.MoviesMovieDao;
import se.iths.jimmy.model.Genre;
import se.iths.jimmy.model.Movie;
import se.iths.jimmy.service.MovieService;
import se.iths.jimmy.util.MongoDbConfig;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Movie kingKong = new Movie("King Kong", 2005, List.of(Genre.ACTION, Genre.SCIFI));

        Movie elf = new Movie("Elf", 2003, List.of(Genre.COMEDY));

        Movie it = new Movie("It", 2017, List.of(Genre.HORROR));


        try (MongoClient client = MongoClients.create(MongoDbConfig.CONNECTION_URI)) {
            MongoDatabase database = client.getDatabase(MongoDbConfig.DATABASE_NAME);
            MoviesMovieDao movieDao = new MoviesMovieDao(database);
            MovieService service = new MovieService(movieDao);

            System.out.println("Ansluten till databasen!");

//          Lägger till king kong
            System.out.println("Lägger till: '" + kingKong.getTitle() + "' var god vänta...");
            movieDao.insert(kingKong);
            System.out.println("Film: '" + kingKong.getTitle() + ", " + kingKong.getYear() + "' tillagd i movies-collection.");

//          Ger Elf fel årtal
            elf.setYear(2000);
            System.out.println("Lägger till: '" + elf.getTitle() + "' var god vänta...");
            movieDao.insert(elf);
            System.out.println("Film: '" + elf.getTitle() + ", " + elf.getYear() + "' tillagd i movies-collection.");

            System.out.println("Uppdaterar årtal på '" + elf.getTitle() + "'");
            elf = movieDao.findAndUpdateYear(elf.getTitle(), 2003);
            System.out.println("Nytt år för '" + elf.getTitle() + "' till -> " + elf.getYear());

            System.out.println("Söker efter titel: '" + elf.getTitle() + "'...");
            movieDao.findByTitle(elf.getTitle());
            System.out.println("Hittade: " + elf.getTitle());

            System.out.println("Alla filmer:");
            movieDao.findAll().forEach(System.out::println);

            System.out.println("Tar bort Elf");
            movieDao.findAndDeleteMovie(elf.getTitle());

            System.out.println("Alla filmer:");
            movieDao.findAll().forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Ett fel inträffade: " + e.getMessage());
            e.printStackTrace();
        }

    }
}