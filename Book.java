package com.example.demoreadingnow;

public class Book {
    private String name;
    private String author_name;
    private String category;
    private String description;
    private String url;
    private int year;
    private int rating_count;
    private int pages;
    private double rating;
    private boolean isRead;

    public Book(String name, String author_name) {
        this.name = name;
        this.author_name = author_name;
        this.isRead = false;
    }
    public Book(String name, String author_name, String category){
        this.name = name;
        this.author_name = author_name;
        this.category = category;
        this.isRead = false;
    }

    public Book(String name, String author_name, String category, String description, String url, int year, int rating_count, int pages, double rating) {
        this.name = name;
        this.author_name = author_name;
        this.category = category;
        this.description = description;
        this.url = url;
        this.year = year;
        this.rating_count = rating_count;
        this.pages = pages;
        this.rating = rating;
        this.isRead = false;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRating_count() {
        return rating_count;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
