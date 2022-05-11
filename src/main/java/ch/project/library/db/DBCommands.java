package ch.project.library.db;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ch.project.library.Book;
import ch.project.library.user.Customer;

public class DBCommands {
    private Connection db;

    public DBCommands(){
        DBConnector connector = new DBConnector();
        db = connector.getConnection();
    }

    public Customer logIn(String username, String pwd){
        try{
            Statement statement = db.createStatement();
            ResultSet userData = statement.executeQuery("select forename, surname, email, username, isLibrarian from user_library where username = '" + username + "' and password = '" + pwd + "'");
            if(userData.next()){
                return new Customer(userData.getString("forename"), userData.getString("surname"), userData.getString("email"), userData.getString("username"));
            } else {
                return null;
            }
            
        } catch(Exception e){
        }
        return null;
    }

    public List<Book> searchBook(String searchedName){
        try{
            Statement statement = db.createStatement();
            ResultSet bookResult = statement.executeQuery("select * from book where title like '%" + searchedName + "'%");
            List<Book> bookList = new ArrayList<Book>();
            while(bookResult.next()){
                String author = getAuthorName(bookResult.getInt("id_author"));
                String language = getLanguage(bookResult.getInt("id_language"));
                String genre = getGenre(bookResult.getInt("id_genre"));
                Book book = new Book(bookResult.getString("title"), author, genre, language, bookResult.getString("description"), bookResult.getInt("rating"), bookResult.getInt("id_book"));
                bookList.add(book);
            }
            return bookList;
            
        } catch(Exception e){
        }
        return null;
    }

    public List<Book> searchBookByGenre(String searchedGenre){
        try{
            Statement statement = db.createStatement();
            ResultSet categorieResult = statement.executeQuery("select name from genre where genre like %" + searchedGenre + "%");
            List<Book> bookList = new ArrayList<Book>();
            while(categorieResult.next()){
                Statement bookStatement = db.createStatement();
                ResultSet bookResult = bookStatement.executeQuery("select * from book where id_genre = " + categorieResult.getInt("id_genre"));    
                while(bookResult.next()){
                    String author = getAuthorName(bookResult.getInt("id_author"));
                    String language = getLanguage(bookResult.getInt("id_language"));
                    String genre = getGenre(bookResult.getInt("id_genre"));
                    Book book = new Book(bookResult.getString("title"), author, genre, language, bookResult.getString("description"), bookResult.getInt("rating"), bookResult.getInt("id_book"));
                    bookList.add(book);                    
                }
            }
            return bookList;
        } catch(Exception e){
        }
        return null;
    }

    public List<Book> searchBookByAuthor(String name, String sureName){
        try{
            Statement statement = db.createStatement();
            ResultSet authorResult = statement.executeQuery("select forename, surename from author where forename like %" + name + "% and surename like %" + sureName +"%");
            List<Book> bookList = new ArrayList<Book>();
            while(authorResult.next()){
                Statement bookStatement = db.createStatement();
                ResultSet bookResult = bookStatement.executeQuery("select * from book where id_author = " + authorResult.getInt("id_author"));    
                while(bookResult.next()){
                    String author = getAuthorName(bookResult.getInt("id_author"));
                    String language = getLanguage(bookResult.getInt("id_author"));
                    String genre = getGenre(bookResult.getInt("id_genre"));
                    Book book = new Book(bookResult.getString("title"), author, genre, language, bookResult.getString("description"), bookResult.getInt("rating"), bookResult.getInt("id_book"));
                    bookList.add(book);                    
                }
            }
            return bookList;
        } catch(Exception e){
        }
        return null;
    }

    public Book searchBookByID(int id){
        try{
            Statement statement = db.createStatement();
            ResultSet bookResult = statement.executeQuery("select * from book where id_book = " + id);
            String author = getAuthorName(bookResult.getInt("id_author"));
            String language = getLanguage(bookResult.getInt("id_language"));
            String genre = getGenre(bookResult.getInt("id_genre"));
            return new Book(bookResult.getString("title"), author, genre, language, bookResult.getString("description"), bookResult.getInt("rating"), bookResult.getInt("id_book"));            
        } catch(Exception e){
        }
        return null;
    }


    public boolean rentBook(int bookID, int userID, int days){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expectedReturnDate = LocalDateTime.now().plusDays(days);
            Statement statement = db.createStatement();
            statement.executeQuery("INSERT INTO `borrowed_book`(`id_user_library`, `id_book`, `borrowed_date`, `return_date`) VALUES (" + userID + ", " + bookID + ", " + now.format(formatter) + ", " + expectedReturnDate.format(formatter) + ")");

        } catch(Exception e){
        }
        return false;
    }

    public boolean buyBook(int bookID, int userID){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
            LocalDateTime now = LocalDateTime.now();
            Statement statement = db.createStatement();
            statement.executeQuery("INSERT INTO `borrowed_book`(`id_user_library`, `id_book`, `borrowed_date`) VALUES (" + userID + ", " + bookID + ", " + now.format(formatter) + ")");
        } catch(Exception e){
        }
        return false;
    }
    
    private String getGenre(int id){
        try{
            Statement statement = db.createStatement();
            ResultSet nameResult = statement.executeQuery("select name from genre where id_genre = " + id);
            nameResult.next();
            return nameResult.getString("name");   
        } catch(Exception e){
        }
        return "";
    }

    private String getAuthorName(int id){
        try{
            Statement statement = db.createStatement();
            ResultSet nameResult = statement.executeQuery("select forename, surename from author where id_author = " + id);
            nameResult.next();
            return nameResult.getString("forename") + " " + nameResult.getString("surename");   
        } catch(Exception e){
        }
        return "";        
    }

    private String getLanguage(int id){
        try{
            Statement statement = db.createStatement();
            ResultSet nameResult = statement.executeQuery("select language from language where id_language = " + id);
            nameResult.next();
            return nameResult.getString("language");   
        } catch(Exception e){
        }
        return "";        

    }
}
