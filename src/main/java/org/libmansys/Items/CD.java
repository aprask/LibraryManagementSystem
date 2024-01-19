package org.libmansys.Items;

import java.util.*;

public class CD extends Item {
    private String artist;
    private Date year;
    private String genre;
    public CD(String itemName, int itemID, double itemCost, String artist, Date year, String genre) {
        super(itemName, itemID, itemCost);
        this.artist = artist;
        this.year = year;
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
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
