package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RajatSharma on 5/8/16.
 */
public class TrailerListAdapter extends ArrayAdapter {

    private Context context;
    private LayoutInflater inflater;

    private List<TrailersInfo> trailersList;

    public TrailerListAdapter(Context context, List<TrailersInfo> trailersList) {
        super(context, R.layout.listview_trailers_item, trailersList);

        this.context = context;
        this.trailersList = trailersList;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_trailers_item, parent, false);

            ((TextView) convertView.findViewById(R.id.trailerTitle)).setText(trailersList.get(position).getTrailerTitle());

        }

        return convertView;
    }
}
