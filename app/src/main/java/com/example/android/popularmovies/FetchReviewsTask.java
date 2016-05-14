package com.example.android.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RajatSharma on 5/11/16.
 */
public class FetchReviewsTask extends AsyncTask<MovieInfo, Void, List<MovieReview>> {

    private final String LOG_TAG = FetchReviewsTask.class.getSimpleName();

    private ReviewAdapter mReviewAdapter;
    private final Context mContext;


    public FetchReviewsTask(Context context, ReviewAdapter reviewAdapter) {
        mContext = context;
        mReviewAdapter = reviewAdapter;
    }


    @Override
    protected List<MovieReview> doInBackground(MovieInfo... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String reviewsJsonStr = null;
        MovieInfo curMovie = params[0];
        final String movieId = curMovie.getId();
        Log.v(LOG_TAG, "Movie id:" + movieId);


        try {

            // final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/" + order + "?";

            final String TRAILERS_BASE_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/reviews?";
            final String API_KEY = "api_key";

            Uri builtUri = Uri.parse(TRAILERS_BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY, BuildConfig.MOVIE_API_KEY)
                    .build();

            // String apiKey = "&APPID=" + BuildConfig.OPEN_WEATHER_MAP_API_KEY;
            //URL url = new URL(bui.concat(apiKey));

            URL url = new URL(builtUri.toString());

            //Log.v(LOG_TAG, "Built URI: " + builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            reviewsJsonStr = buffer.toString();

            Log.v(LOG_TAG, "Review JSON String: " + reviewsJsonStr);


        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            reviewsJsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }

        }

        try {
            return getReviewDataFromJson(reviewsJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;

    }



    private List<MovieReview> getReviewDataFromJson(String reviewsJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String T_RESULTS = "results";


        JSONObject reviewsJson = new JSONObject(reviewsJsonStr);
        JSONArray reviewsArray = reviewsJson.getJSONArray(T_RESULTS);


        List<MovieReview> reviewsList = new ArrayList<MovieReview>();


        for (int i = 0; i < reviewsArray.length(); i++) {
            // For now, using the format "Day, description, hi/low"
            String author;
            String content;

            // Get the JSON object representing the day
            JSONObject review = reviewsArray.getJSONObject(i);

            author = review.getString("author");
            content = review.getString("content");

            MovieReview movieReview = new MovieReview(author, content);
            //Log.v(LOG_TAG, "author" + movieReview.getAuthor());

            reviewsList.add(movieReview);
        }

        return reviewsList;

    }



    @Override
    protected void onPostExecute(List<MovieReview> reviewsList) {
        if(reviewsList != null)
        {
            mReviewAdapter.clear();
            for(MovieReview review: reviewsList)
            {
                Log.v(LOG_TAG, "nauthor" + review.getAuthor());
                mReviewAdapter.add(review);
            }
        }
    }

}
