package com.example.android.popularmovies;
//ASYNC TASK

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

public class FetchMovieTask extends AsyncTask<String, Void, List<MovieInfo>>
{
    private  final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    private ImageListAdapter mImageListAdapter;
    private final Context mContext;


    public FetchMovieTask(Context context, ImageListAdapter imageListAdapter) {
        mContext = context;
        mImageListAdapter = imageListAdapter;
    }




    private List<MovieInfo> getMovieDataFromJson(String moviesJsonStr)
            throws JSONException {


        List<MovieInfo> moviesList = new ArrayList<MovieInfo>();

        if(moviesJsonStr != null){

            // These are the names of the JSON objects that need to be extracted.
            final String M_RESULTS = "results";
            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(M_RESULTS);


            for(int i = 0; i < moviesArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String imageUrl;
                String title;
                String plot;
                String rating;
                String releaseDate;
                String id;

                // Get the JSON object representing the day
                JSONObject movie = moviesArray.getJSONObject(i);

                //JSONObject posterObject = movie.get("poster_path");
                //JSONObject titleObject = movie.getJSONObject("title");
                //JSONObject plotObject = movie.getJSONObject("overview");
                //JSONObject ratingObject = movie.getJSONObject("vote_average");
                //JSONObject releaseDateObject = movie.getJSONObject("release_date");

                imageUrl = "http://image.tmdb.org/t/p/w500/" + movie.getString("poster_path");
                title = movie.getString("title");
                plot = movie.getString("overview");
                rating = movie.getString("vote_average");
                releaseDate = movie.getString("release_date");
                id = movie.getString("id");

                MovieInfo movieInfo = new MovieInfo(imageUrl, title, plot, rating, releaseDate, id);

                //highAndLow = formatHighLows(high, low);
                //resultStrs[i] = day + " - " + description + " - " + highAndLow;

                moviesList.add(movieInfo);
            }
        }

        for (MovieInfo movie : moviesList) {
            Log.v(LOG_TAG, "Movie poster: " + movie.getImageUrl());
        }
        return moviesList;

    }


    @Override
    protected void onPostExecute(List<MovieInfo> moviesList) {
        if(moviesList != null)
        {

            mImageListAdapter.clear();
            //mImageListAdapter.notifyDataSetChanged();
            Log.i(LOG_TAG, "CLEAR called");
            for(MovieInfo movie: moviesList)
            {
                mImageListAdapter.add(movie);
            }
            //mImageListAdapter.clear();
            mImageListAdapter.notifyDataSetChanged();



        }
    }

    @Override
    protected List<MovieInfo> doInBackground(String... params)
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

// Will contain the raw JSON response as a string.
        String moviesJsonStr = null;
        String order = params[0];




        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/" + order + "?";
            final String API_KEY = "api_key";

            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
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
            moviesJsonStr = buffer.toString();

            Log.v(LOG_TAG, "Movie JSON String: " + moviesJsonStr);


        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            moviesJsonStr = null;
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
            return getMovieDataFromJson(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }


        return null;

    }
}
