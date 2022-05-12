package ch.project.library.db;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ch.project.library.Book;
import ch.project.library.user.Customer;
import ch.project.library.user.Librarian;

public class DBCommands {
    private DBHandler dbHandler;

    public DBCommands(){
        DBConnector connector = new DBConnector();
        dbHandler = new DBHandler(connector.getConnection());
    }

    public void createUser(String userName, String surename, String forename, String email, String password){
        dbHandler.executeCommand("INSERT INTO `user_library`(`forename`, `surname`, `email`, `username`, `password`) VALUES ('" + forename + "','" + surename + "','" + email + "','" + userName + "','" + password + "");
    }

    public Customer logIn(String username, String pwd){
        try{
            ResultSet userData = dbHandler.executeCommand("select forename, surname, email, username, isLibrarian from user_library where username = '" + username + "' and password = '" + pwd + "'");
            if(userData.next()){
                return new Customer(userData.getString("forename"), userData.getString("surname"), userData.getString("email"), userData.getString("username"));
            } else {
                return null;
            }
            
        } catch(Exception e){
        }
        return null;
    }

    public Librarian logInAdministartor(String password){
        try{
            ResultSet result = dbHandler.executeCommand("select isLibrarian from user_library where username = 'admin' and password = '" + password + "'");
            if(result.next()){
                if(result.getBoolean("isLibrarian")){
                    return new Librarian();
                }else {
                    return null;
                }
            } else {
                return null;
            }
            
        } catch(Exception e){
        }
        return null;

    }

    public List<Book> searchBook(String searchedName){
        try{
            ResultSet bookResult = dbHandler.executeCommand("select * from book where title like %" + searchedName + "%");
            List<Book> bookList = new ArrayList<Book>();
            while(bookResult.next()){
                bookList.add(createBook(bookResult));
            }
            return bookList;
            
        } catch(Exception e){
        }
        return null;
    }

    public List<Book> searchBookByGenre(String searchedGenre){
        try{
            ResultSet categorieResult = dbHandler.executeCommand("select name from genre where genre like %" + searchedGenre + "%");
            List<Book> bookList = new ArrayList<Book>();
            while(categorieResult.next()){
                ResultSet bookResult = dbHandler.executeCommand("select * from book where id_genre = " + categorieResult.getInt("id_genre"));    
                while(bookResult.next()){
                    bookList.add(createBook(bookResult));
                }
            }
            return bookList;
        } catch(Exception e){
        }
        return null;
    }

    public List<Book> searchBookByAuthor(String name, String sureName){
        try{
            ResultSet authorResult = dbHandler.executeCommand("select forename, surename from author where forename like %" + name + "% and surename like %" + sureName +"%");
            List<Book> bookList = new ArrayList<Book>();
            while(authorResult.next()){
                ResultSet bookResult = dbHandler.executeCommand("select * from book where id_author = " + authorResult.getInt("id_author"));    
                while(bookResult.next()){
                    bookList.add(createBook(bookResult));                    
                }
            }
            return bookList;
        } catch(Exception e){
        }
        return null;
    }

    public Book searchBookByID(int id){
        try{
            ResultSet bookResult = dbHandler.executeCommand("select * from book where id_book = " + id);
            return createBook(bookResult);            
        } catch(Exception e){
        }
        return null;
    }


    public boolean rentBook(int bookID, int userID, int days){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expectedReturnDate = LocalDateTime.now().plusDays(days);
            dbHandler.executeCommand("INSERT INTO `borrowed_book`(`id_user_library`, `id_book`, `borrowed_date`, `return_date`) VALUES (" + userID + ", " + bookID + ", " + now.format(formatter) + ", " + expectedReturnDate.format(formatter) + ")");

        } catch(Exception e){
        }
        return false;
    }

    public boolean buyBook(int bookID, int userID){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
            LocalDateTime now = LocalDateTime.now();
            dbHandler.executeCommand("INSERT INTO `borrowed_book`(`id_user_library`, `id_book`, `borrowed_date`) VALUES (" + userID + ", " + bookID + ", " + now.format(formatter) + ")");
        } catch(Exception e){
        }
        return false;
    }
    
    private String getGenre(int id){
        try{
            ResultSet nameResult = dbHandler.executeCommand("select name from genre where id_genre = " + id);
            nameResult.next();
            return nameResult.getString("name");   
        } catch(Exception e){
        }
        return "";
    }

    private String getAuthorName(int id){
        try{
            ResultSet nameResult = dbHandler.executeCommand("select forename, surename from author where id_author = " + id);
            nameResult.next();
            return nameResult.getString("forename") + " " + nameResult.getString("surename");   
        } catch(Exception e){
        }
        return "";        
    }

    private String getLanguage(int id){
        try{
            ResultSet nameResult = dbHandler.executeCommand("select language from language where id_language = " + id);
            nameResult.next();
            return nameResult.getString("language");   
        } catch(Exception e){
        }
        return "";        

    }

    private Book createBook(ResultSet bookResult) throws SQLException{
        String author = getAuthorName(bookResult.getInt("id_author"));
        String language = getLanguage(bookResult.getInt("id_language"));
        String genre = getGenre(bookResult.getInt("id_genre"));
        return new Book(bookResult.getString("title"), author, genre, language, bookResult.getString("description"), bookResult.getInt("rating"), bookResult.getInt("id_book"));
    }

    ///
    /// Admin specific functions
    ///

    public void addBook(String title, int author, int language, int genre, String description){
        dbHandler.executeCommand("INSERT INTO `book`(`title`, `id_author`, `id_genre`, `id_language`, `description`, `rating`) VALUES ('" + title + "' , " + author + ", " + genre + ", " + language + ",'" + description + "')");
    }

    public void addAuthor(String surename, String forename){
        dbHandler.executeCommand("INSERT INTO `author`(`forename`, `surename`) VALUES ('" + forename + "','" + surename + "')");
    }

    public void bookIsReturned(int returnedBook, int userID){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
        LocalDateTime now = LocalDateTime.now();
        dbHandler.executeCommand("INSERT INTO borrowed_book('return_date') values('" + now.format(formatter) + "') WHERE id_user_library = " + userID + " and id_book = " + returnedBook);
    }

    public void removeBook(int bookToRemove){
        dbHandler.executeCommand("delete from book where id_book = " + bookToRemove);
    }

}
