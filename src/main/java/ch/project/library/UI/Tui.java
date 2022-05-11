package ch.project.library.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import ch.project.library.Book;
import ch.project.library.db.DBCommands;
import ch.project.library.user.Customer;
import ch.project.library.user.User;

public class Tui {
    private TextIO console;
    private DBCommands db;

    public Tui() throws IOException{
        console = TextIoFactory.getTextIO();
        db = new DBCommands();
    }

    public void run(){
        boolean runs = true;
        while(runs) {
            boolean hasAccount = console.newBooleanInputReader()
                .read("Do you have a account?");

            if(hasAccount){
                Customer user = logIn();
                if(!Objects.equals(user, null)){
                    userAction(user);
                }
            }

        }
                
    }

    private void userAction(Customer user){
        boolean isLogedIn = true;
        while(isLogedIn){
            char actionType = console.newCharInputReader()
                .read("Choose your Actiontype: \n- s -> Search for books \n- b -> borrow a book \n- p -> purchase a book \n- l -> logout \n:");
            
            switch (actionType) {
                case 's':
                    clearConsole();
                    showBookList(searchPrompt());
                    break;

                case 'b':
                    clearConsole();
                    int id = console.newIntInputReader()
                        .read("Enter the ID of the book you want to borrow");
                    Book book = db.searchBookByID(id);
                    if(book != null) {
                        int days = console.newIntInputReader()
                            .read("Enter how long you want to borrow the book");
                        user.rentBook(id, user.getId(), days);
                    } else {
                        System.out.println("The ID of the book is unknown");
                    }
                    break;

                case 'p':
                    clearConsole();
                    int bookId = console.newIntInputReader()
                        .read("Enter the ID of the book you want to purchase");
                    Book bookPurchase = db.searchBookByID(bookId);
                    if(bookPurchase != null) {
                        user.buyBook(bookId, user.getId());
                    } else {
                        System.out.println("The ID of the book is unknown");
                    }
                    break;

                case 'l':
                    isLogedIn = false;
                    clearConsole();
                    break;
            
                default:
                    clearConsole();
                    System.out.println("The command '" + actionType + "' is unknown");
                    break;
            }
        }
    }

    public Customer logIn(){
        boolean isLogIn = true;
        while(isLogIn){
            String userName = console.newStringInputReader()
                .read("Enter your username");
        
            String password = console.newStringInputReader()
                .withMinLength(6)
                .withInputMasking(true)
                .read("Password");
            
            Customer user = db.logIn(userName, password);
            if(user != null){
                return user;
            } 

            System.out.println("Your user credentials dont match with any of the existing users");
            isLogIn = console.newBooleanInputReader()
                .read("Do you want to try again?");
            clearConsole();
        }
        return null;
    }

    public List<Book> searchPrompt(){
        boolean isSearching = true;
        while(isSearching){
            char searchType = console.newCharInputReader()
            .read("Choose your Searchtype: \n- g -> Search by genre \n- b -> Search by bookname \n- a -> Search by bookauthor \n- i Search by id \n:");
            switch (searchType) {
                case 'g':
                    String genre = console.newStringInputReader()
                        .read("Enter the genre you want to search");
                    return db.searchBookByGenre(genre);
                
                case 'b':
                    String name = console.newStringInputReader()
                        .read("Enter the book you want to search");
                    return db.searchBook(name);

                case 'i':
                    int id = console.newIntInputReader()
                        .read("Enter the book id you want to search");
                    List<Book> book = new ArrayList<Book>();
                    book.add(db.searchBookByID(id));
                    return book;

                case 'a':
                    String surename = console.newStringInputReader()
                        .read("Enter the surename from the Author");
                    String forename = console.newStringInputReader()
                        .read("Enter the forename from the Author");
                    return db.searchBookByAuthor(forename, surename);

                default:
                    System.out.println("The command '" + searchType + "' is unknown");
                    isSearching = console.newBooleanInputReader()
                        .read("Do you want to try again?");
                    clearConsole();
                    break;
            }
        }
        return null;
    }

    private void showBookList(List<Book> books){
        if(books != null) {
            for (Book book : books) {
                System.out.println("===================================");
                System.out.println("Bookname: " + book.getBookName());
                System.out.println("Author  : " + book.getAuthor());
                System.out.println("Book id : " + book.getId());
                System.out.println("Language: " + book.getLanguage());
                System.out.println("Genre   : " + book.getGenre());    
                System.out.println("Rating  : " + book.getRating() + "/5");
                System.out.println("===================================");
            }    
        } else {
            System.out.println("No books could be found");
        }
    } 
    private void clearConsole(){
        System.out.print("\033\143");
    }
}