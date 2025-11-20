package se.iths.jimmy.model;

import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class Movie {

    private ObjectId id;
    private String title;
    private int year;
    private List<Genre> genres;

    public Movie() {
    }

    public Movie(ObjectId id, String title, int year, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genres = genres;
    }

    public Movie(String title, int year, List<Genre> genres) {
        this.title = title;
        this.year = year;
        this.genres = genres;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Document toDocument() {
        List<Document> genreDocs = new ArrayList<>();
        if(this.genres != null) {
            genreDocs = this.genres.stream()
                    .map(Genre::toDocument)
                    .toList();
        }
        Document doc = new Document()
                .append("title", this.title)
                .append("genres", genreDocs)
                .append("year", this.year);
        if (this.id != null) {
            doc.append("_id", this.id);
        }
        return doc;
    }
public static Movie fromDocument(Document document) {
        Movie movie = new Movie();
        movie.setId(document.getObjectId("_id"));
        movie.setTitle(document.getString("title"));
        movie.setYear(document.getInteger("year"));
        List<Document> genres = document.getList("genres", Document.class);
        List<Genre> genresJava = new ArrayList<>();
        if(genres != null) {
            genresJava = genres.stream()
                    .map(Genre::fromDocument)
                    .toList();
        }
        movie.setGenres(genresJava);
        return movie;
    }

}