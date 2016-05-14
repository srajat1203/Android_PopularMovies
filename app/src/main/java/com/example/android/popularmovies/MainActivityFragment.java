package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {



    Uri uri;
    private static final int DETAIL_LOADER = 0;

    String[] MOVIE_PROJECTION = new String[] {
            ContentProvider.Movie.KEY_IMGURL,
            ContentProvider.Movie.KEY_TITLE,
            ContentProvider.Movie.KEY_PLOT,
            ContentProvider.Movie.KEY_RATING,
            ContentProvider.Movie.KEY_RELEASEDATE,
            ContentProvider.Movie.KEY_MOVIEID,
    };
    // these indices must match the projection
    int INDEX_IMAGEURL = 0;
    int INDEX_TITLE = 1;
    int INDEX_PLOT = 2;
    int INDEX_RATING = 3;
    int INDEX_RELEASEDATE = 4;
    int INDEX_MOVIEID = 5;




    public static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
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
                MovieInfo curMovie = (MovieInfo) imageListAdapter.getItem(i);
                //Bundle b = new Bundle();
                //b.putParcelable("curMovie", curMovie);
                Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra("curMovie", curMovie);
                startActivity(intent);

            }

        });

        uri = Uri.parse("content://com.example.android.popularmovies/movies");


        return rootview;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        getMovies();

    }

    public void getMovies(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_order_key), getString(R.string.pref_order_default));
        if(sortOrder.equals("favorites")) {
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        }
        else{
            Log.i(LOG_TAG, "MOVIETASKCALLED with sortorder" + sortOrder);
            FetchMovieTask fetchMovieTask = new FetchMovieTask(getActivity(), imageListAdapter);
            fetchMovieTask.execute(sortOrder);
        }

    }

    public void onOrderChanged(){
        getMovies();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_order_key), getString(R.string.pref_order_default));
        if(sortOrder.equals("favorites")) {
            getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
        }
    }


    //LOADERS

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        if ( null != uri ) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    uri,
                    MOVIE_PROJECTION,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {

            String img = data.getString(INDEX_IMAGEURL);
            String title = data.getString(INDEX_TITLE);
            String plot = data.getString(INDEX_PLOT);
            String rating = data.getString(INDEX_RATING);
            String rdate = data.getString(INDEX_RELEASEDATE);
            String id = data.getString(INDEX_MOVIEID);

            Log.v(LOG_TAG, "img title plot rating rdate id " + img + " " + title + " " + plot + " " + rdate + " " + id);

            MovieInfo favMovie = new MovieInfo(img, title, plot, rating, rdate, id);

            imageListAdapter.add(favMovie);

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        imageListAdapter.clear();
    };





}
