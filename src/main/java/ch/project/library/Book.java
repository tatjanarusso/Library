package ch.project.library;

public class Book {
    private int bookID;
    private String bookName;
    private String author;
    private String genre;
    private String language;
    private int rating;
    private int id;

    public Book(String bookName, String author, String genre, String language, String description, int rating, int id) {
        setBookName(bookName);
        setAuthor(author);
        setGenre(genre);
        setRating(rating);
        bookID = id;
    }

    public Book(){

    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
