package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra("curMovie")){

            MovieInfo curMovie = (MovieInfo)intent.getSerializableExtra("curMovie");

            ImageView imageView = (ImageView)rootView.findViewById(R.id.poster);
            Picasso.with(getActivity()).load(curMovie.getImageUrl()).into(imageView);
            ((TextView) rootView.findViewById(R.id.title)).setText(curMovie.getTitle());
            ((TextView) rootView.findViewById(R.id.plot)).setText(curMovie.getPlot());
            ((TextView) rootView.findViewById(R.id.rdate)).setText(curMovie.getReleaseDate());
            ((TextView) rootView.findViewById(R.id.votes)).setText(curMovie.getRating());
        }

        return rootView;
    }
}
