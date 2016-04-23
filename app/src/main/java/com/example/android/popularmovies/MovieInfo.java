package com.example.android.popularmovies;

import java.io.Serializable;

/**
 * Created by RajatSharma on 4/21/16.
 */
@SuppressWarnings("serial")
public class MovieInfo implements Serializable {


    String imageUrl;
    String title;
    String plot;
    String rating;
    String releaseDate;


    public MovieInfo(String imageUrl, String title, String plot, String rating, String releaseDate){

        this.imageUrl = imageUrl;
        this.title = title;
        this.plot = plot;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



}
