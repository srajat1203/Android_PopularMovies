package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ImageListAdapter imageListAdapter;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_main, container, false);

        List<MovieInfo> moviesList = new ArrayList<MovieInfo>();

        imageListAdapter = new ImageListAdapter(getActivity(), moviesList);
        GridView gv = (GridView)rootview.findViewById(R.id.gridView);
        gv.setAdapter(imageListAdapter);


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //String forecast = mForecastAdapter.getItem(i);
                MovieInfo curMovie = (MovieInfo)imageListAdapter.getItem(i);
                //Bundle b = new Bundle();
                //b.putParcelable("curMovie", curMovie);
                Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra("curMovie", curMovie);
                startActivity(intent);
            }

        });


        return rootview;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_order_key), getString(R.string.pref_order_default));
        FetchMovieTask fetchWeatherTask = new FetchMovieTask();
        fetchWeatherTask.execute(sortOrder);
    }



    //IMAGE ADAPTER

    public class ImageListAdapter extends ArrayAdapter {
        private Context context;
        private LayoutInflater inflater;

        private List<MovieInfo> moviesList;

        public ImageListAdapter(Context context, List<MovieInfo> moviesList) {
            super(context, R.layout.grid_item_layout, moviesList);

            this.context = context;
            this.moviesList = moviesList;

            inflater = LayoutInflater.from(context);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.grid_item_layout, parent, false);
                //convertView= (ImageView) rootView.findViewById(R.id.gridViewItem);
            }

            Picasso
                    .with(context)
                    .load(moviesList.get(position).getImageUrl())
                    .fit() // will explain later
                    .into((ImageView) convertView);

            return convertView;
        }
    }


    //ASYNC TASK

    public class FetchMovieTask extends AsyncTask<String, Void, List<MovieInfo>>
    {
        private  final String LOG_TAG = FetchMovieTask.class.getSimpleName();


        private List<MovieInfo> getMovieDataFromJson(String moviesJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String M_RESULTS = "results";
            final String OWM_WEATHER = "weather";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DESCRIPTION = "main";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(M_RESULTS);


            List<MovieInfo> moviesList = new ArrayList<MovieInfo>();


            for(int i = 0; i < moviesArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String imageUrl;
                String title;
                String plot;
                String rating;
                String releaseDate;

                // Get the JSON object representing the day
                JSONObject movie = moviesArray.getJSONObject(i);

                //JSONObject posterObject = movie.get("poster_path");
                //JSONObject titleObject = movie.getJSONObject("title");
                //JSONObject plotObject = movie.getJSONObject("overview");
                //JSONObject ratingObject = movie.getJSONObject("vote_average");
                //JSONObject releaseDateObject = movie.getJSONObject("release_date");

                imageUrl = "http://image.tmdb.org/t/p/w185/" + movie.getString("poster_path");
                title = movie.getString("title");
                plot = movie.getString("overview");
                rating = movie.getString("vote_average");
                releaseDate = movie.getString("release_date");

                MovieInfo movieInfo = new MovieInfo(imageUrl, title, plot, rating, releaseDate);

                //highAndLow = formatHighLows(high, low);
                //resultStrs[i] = day + " - " + description + " - " + highAndLow;

                moviesList.add(movieInfo);
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
                imageListAdapter.clear();
                for(MovieInfo movie: moviesList)
                {
                    imageListAdapter.add(movie);
                }
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




}
