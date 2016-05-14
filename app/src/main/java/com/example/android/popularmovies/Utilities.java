package com.example.android.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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


    public static String getPreferredOrder(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sortOrder = prefs.getString(context.getString(R.string.pref_order_key), context.getString(R.string.pref_order_default));
        return sortOrder;
    }


}
