package com.masai.entities;

import java.util.Date;

public class News {
    private String title;
    private String content;
    private String author;
    private String category;
    private Date publicationDate;

    public News(String title, String content, String author, String category, Date publicationDate) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.publicationDate = publicationDate;
    }

    // Getters and setters for the fields

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "News [title=" + title + ", content=" + content + ", author=" + author + ", category=" + category
                + ", publicationDate=" + publicationDate + "]";
    }
    
    public static News fromString(String newsString) {
        String[] parts = newsString.split("\\|");
        if (parts.length == 5) {
            String title = parts[0].trim();
            String content = parts[1].trim();
            String author = parts[2].trim();
            String category = parts[3].trim();
            Date publicationDate = new Date(Long.parseLong(parts[4].trim()));
            return new News(title, content, author, category, publicationDate);
        } else {
            throw new IllegalArgumentException("Invalid news string format: " + newsString);
        }
    }
}
