package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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
        FetchMovieTask fetchMovieTask = new FetchMovieTask(getActivity(), imageListAdapter);
        fetchMovieTask.execute(sortOrder);
    }



}
