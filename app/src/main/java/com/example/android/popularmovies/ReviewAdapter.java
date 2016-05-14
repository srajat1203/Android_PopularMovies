package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
/**
 * Created by RajatSharma on 5/11/16.
 */
public class ReviewAdapter extends ArrayAdapter {

    private Context context;
    private LayoutInflater inflater;

    private List<MovieReview> reviewList;

    public ReviewAdapter(Context context, List<MovieReview> reviewList) {
        super(context, R.layout.listview_reviews_item, reviewList);

        this.context = context;
        this.reviewList = reviewList;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_reviews_item, parent, false);

            ((TextView) convertView.findViewById(R.id.review_author)).setText(reviewList.get(position).getAuthor());
            ((TextView) convertView.findViewById(R.id.review_content)).setText(reviewList.get(position).getContent());

        }

        return convertView;
    }
}
