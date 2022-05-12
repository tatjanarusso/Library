package ch.project.library.user;

import java.util.Scanner;

public class Librarian extends User {

    String bookTitle;
    String author;
    String category;
    String language;

    public void addBook(String title, int author, int language, int genre, String description){
        db.addBook(title, author, language, genre, description);
    }

    public void addAuthor(String surename, String forename){
        db.addAuthor(surename, forename);
    }

    public void bookIsReturned(int returnedBook, int userID){
        db.bookIsReturned(returnedBook, userID);
    }

    public void removeBook(int bookToRemove){
        db.removeBook(bookToRemove);
    }

    public void seeStatus(int bookStatus){
        
    }
}
