package se.iths.jimmy.service;

import se.iths.jimmy.dao.MovieDao;
import se.iths.jimmy.model.Movie;

import java.util.List;

public class MovieService {

    private final MovieDao<Movie> movieDao;

    public MovieService(MovieDao<Movie> moviesDao) {
        this.movieDao = moviesDao;
    }

    public void addMovie(Movie movie) {
        movieDao.insert(movie);
    }


    public List<Movie> getAllMovies() {
        return movieDao.findAll();
    }

    public Movie getMovieByTitle(String title) {
        return movieDao.findByTitle(title);
    }

    public Movie updateMovieYear(String title, int newYear) {
        return movieDao.findAndUpdateYear(title, newYear);
    }

    public void deleteMovie(String title) {
        movieDao.findAndDeleteMovie(title);
    }

}
