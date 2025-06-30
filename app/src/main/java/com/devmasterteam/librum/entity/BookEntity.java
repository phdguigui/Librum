package com.devmasterteam.librum.entity;

public class BookEntity {

    private int id;
    private String title;
    private String author;
    private boolean favorite;
    private String genre;

    public BookEntity(int id, String title, String author, boolean favorite, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.favorite = favorite;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) { this.author = author; }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) { this.genre = genre; }
}