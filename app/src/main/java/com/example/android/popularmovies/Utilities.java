package com.example.android.popularmovies;

/**
 * Created by RajatSharma on 5/7/16.
 */
public class Utilities {

    public static String detailDateFormatter(String releaseDate){

        char[] releaseYearArr = new char[4];
        StringBuilder releaseYear = new StringBuilder(4);

        for(int i=0; i<4; i++)
        {
            releaseYear.append(releaseDate.charAt(i));
        }
        return releaseYear.toString();
    }

    public static String voteFormatter(String votes){
        String formattedVotes;

        String ten = "10";
        formattedVotes = votes + "\\" + ten;
        return formattedVotes;
    }



}
