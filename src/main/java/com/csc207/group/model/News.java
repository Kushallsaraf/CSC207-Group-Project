package com.csc207.group.model;

public class News {
    private String source;
    private String author;
    private String title;
    private String content;
    private String date;
    private String url;

    public News(String source, String author, String title, String content, String date, String url) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.content = content;
        this.date = date;
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}
