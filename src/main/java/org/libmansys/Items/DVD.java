package org.libmansys.Items;

import java.util.Date;

public class DVD extends Item {
    private String director;
    private Date year;
    private String genre;
    public DVD(String itemName, int itemID, double itemCost, String director, Date year, String genre) {
        super(itemName, itemID, itemCost);
        this.director = director;
        this.year = year;
        this.genre = genre;
    }

    public String getArtist() {
        return director;
    }

    public void setArtist(String artist) {
        this.director = artist;
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
