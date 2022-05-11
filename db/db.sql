DROP DATABASE IF EXISTS library_management;
CREATE DATABASE library_management;
USE library_management;

CREATE TABLE author(
    id_author int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    forename varchar(25),
    surename varchar(25)
);

CREATE TABLE genre(
    id_genre int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(25)
);

CREATE TABLE language(
    id_language int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    language varchar(25) NOT NULL
);

CREATE TABLE user_library(
    id_user_library int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    forename varchar(50) NOT NULL,
    surname varchar(50) NOT NULL,
    email varchar(254),
    username varchar(25) NOT NULL,
    password varchar(42) NOT NULL,
    isLibrarian tinyInt(1)
);

CREATE TABLE book(
    id_book int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title int,
    id_author int,
    id_genre int,
    id_language int,
    description varchar(25),
    rating int(1), 

    FOREIGN KEY (id_author) REFERENCES author(id_author),
    FOREIGN KEY (id_genre) REFERENCES genre(id_genre),
    FOREIGN KEY (id_language) REFERENCES language(id_language)
);

CREATE TABLE borrowed_book(
    id_borrowed_book int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    id_user_library int NOT NULL,
    id_book int NOT NULL,
    borrowed_date date NOT NULL,
    expected_return_date date,
    return_date date,

    FOREIGN KEY (id_user_library) REFERENCES user_library(id_user_library),
    FOREIGN KEY (id_book) REFERENCES book(id_book)
);