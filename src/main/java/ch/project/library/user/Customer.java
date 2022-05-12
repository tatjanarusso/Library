package ch.project.library.user;

import java.util.List;

import ch.project.library.Book;
import ch.project.library.db.DBCommands;

public class Customer extends User{
    private String foreName;
    private String sureName;
    private String email;
    private String username;
    
    public Customer(String name, String lastName, String mail, String userName, int id, DBCommands database){
        foreName = name;
        sureName = lastName;
        mail = email;
        userName = username;
        db = database;
        setId(id);
    }

    public Customer(String name, String lastName, String mail, String userName){
        foreName = name;
        sureName = lastName;
        mail = email;
        userName = username;
    }

    public void rentBook(int bookID, int userID, int dayCount) {
        db.rentBook(bookID, userID, dayCount);
    }

    public void buyBook(int bookID, int userID) {
        db.buyBook(bookID, userID);
    }

    public String getForeName() {
        return foreName;
    }

    public String getSureName() {
        return sureName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsermame() {
        return username;
    }

    public void setDatabase(DBCommands database){
        db = database;
    }
}
