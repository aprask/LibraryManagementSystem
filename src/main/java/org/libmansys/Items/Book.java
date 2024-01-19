package org.libmansys.Items;

import java.util.*;

public class Book extends Item {
    private String author;
    private String isbn;
    private Date year;
    private String genre;
    public Book(String itemName, int itemID, double itemCost, String author, String isbn, Date year, String genre) {
        super(itemName, itemID, itemCost);
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
