package hu.petrik.javafxrestclientdolgozat;

import com.google.gson.annotations.Expose;

public class Music {
    private int id;
    @Expose
    private String title;
    @Expose
    private String artist;
    @Expose
    private int length;
    @Expose
    private String genre;
    private boolean hasLyrics;

    public Music(int id, String title, String artist, int length, String genre, boolean hasLyrics) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.length = length;
        this.genre = genre;
        this.hasLyrics = hasLyrics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isHasLyrics() {
        return hasLyrics;
    }

    public void setHasLyrics(boolean hasLyrics) {
        this.hasLyrics = hasLyrics;
    }
}
