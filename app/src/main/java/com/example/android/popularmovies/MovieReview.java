package com.example.android.popularmovies;

/**
 * Created by RajatSharma on 5/11/16.
 */
public class MovieReview {

    String author;
    String content;

    public MovieReview(String author, String content){
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
