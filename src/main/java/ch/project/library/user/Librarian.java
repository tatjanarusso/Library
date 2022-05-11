package ch.project.library.user;

import java.util.Scanner;

public class Librarian extends User {

    String bookTitle;
    String author;
    String category;
    String language;
    Scanner sc = new Scanner(System.in);

    public void addBook(){
        System.out.println("Titel:");
        bookTitle = sc.next();
        System.out.println("Autor:");
        author = sc.next();
        System.out.println("Kategorie:");
        category = sc.next();
        System.out.println("Sprache:");
        language = sc.next();

        System.out.println("Sind ihre Angaben korrekt? Y/N");
        String answer = sc.next();
        if (answer == "Y"){
            //send data to db
        }
        else {
            System.out.println("Ok.");
            //send librarian to main menu
        }
    }

    public void deleteBook(int id){
        searchBookByID(id);
        //select book and delete it.
    }

    public void sellBook(int id){
        deleteBook(id);
    }

    public void seeStatus(){
        //list all books and their status (borrowed or not)
    }

    public void giveFine(){
        
    }
}
