package com.example.android.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RajatSharma on 5/8/16.
 */

//IMAGE ADAPTER

public class ImageListAdapter extends ArrayAdapter {
    private Context context;
    public static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private LayoutInflater inflater;

    private List<MovieInfo> moviesList;
    private View imgView;

    public ImageListAdapter(Context context, List<MovieInfo> moviesList) {
        super(context, R.layout.grid_item_layout, moviesList);

        this.context = context;
        this.moviesList = moviesList;

        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(LOG_TAG, "adapter called now");
        if (convertView == null) {
            Log.i(LOG_TAG, "adapter called only");
            convertView = inflater.inflate(R.layout.grid_item_layout, parent, false);

            //convertView= (ImageView) rootView.findViewById(R.id.gridViewItem);
        }
        imgView = (ImageView) convertView.findViewById(R.id.gridViewItem);
        Log.i(LOG_TAG, "img title is " + moviesList.get(position).getTitle());
        Picasso
                .with(context)
                .load(moviesList.get(position).getImageUrl())
                        // will explain later
                .into((ImageView) imgView);
        return convertView;
    }
}

