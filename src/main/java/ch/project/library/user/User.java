package ch.project.library.user;

import java.util.List;
import java.util.Scanner;

import ch.project.library.Book;
import ch.project.library.db.DBCommands;
import ch.project.library.db.DBConnector;

public class User {
    protected DBCommands db;
    protected int id;

    public void setupDB(DBCommands database){
        db = database;
    }

    public List<Book> searchBook(String name){
        return db.searchBook(name);
    }

    public List<Book> searchBookByGenre(String genre){
        return db.searchBookByGenre(genre);
    }

    public List<Book> searchBookByAuthor(String name, String sureName){
        return db.searchBookByAuthor(name, sureName);
    }

    public Book searchBookByID(int id){
        return db.searchBookByID(id);
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
