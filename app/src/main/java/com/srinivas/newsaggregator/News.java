package com.srinivas.newsaggregator;

public class News {



    private String author;
    private String date;
    private String title;
    private String imageUrl;
    private String description;
    private String url;

    public News(String author, String date, String title, String imageUrl, String description,String url) {
        this.author = author;
        this.date = date;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
