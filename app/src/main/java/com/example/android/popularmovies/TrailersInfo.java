package com.example.android.popularmovies;

/**
 * Created by RajatSharma on 5/8/16.
 */
public class TrailersInfo {

    private String trailerTitle;
    private String trailerUrl;


    public TrailersInfo(String trailerTitle, String trailerUrl){
        this.trailerTitle = trailerTitle;
        this.trailerUrl = trailerUrl;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getTrailerTitle() {
        return trailerTitle;
    }

    public void setTrailerTitle(String trailerTitle) {
        this.trailerTitle = trailerTitle;
    }
}
