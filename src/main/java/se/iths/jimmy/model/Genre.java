package se.iths.jimmy.model;
import org.bson.Document;

public class Genre {
    private String name;
    private String description;

    public Genre() {
    }

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("name", this.name);
        document.append("description", this.description);
        return document;
    }

    public static Genre fromDocument(Document document) {
        Genre genre = new Genre();
        genre.setDescription(document.getString("description"));
        genre.setName(document.getString("name"));
        return genre;
    }
}
