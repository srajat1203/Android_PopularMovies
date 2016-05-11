package com.example.android.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private TrailerListAdapter trailerListAdapter;
    ListView trailersList;
    MovieInfo curMovie;
    public static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    List<TrailersInfo> trailers;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra("curMovie")){

            curMovie = (MovieInfo)intent.getSerializableExtra("curMovie");

            ImageView imageView = (ImageView)rootView.findViewById(R.id.poster);
            Picasso.with(getActivity()).load(curMovie.getImageUrl()).into(imageView);
            ((TextView) rootView.findViewById(R.id.title)).setText(curMovie.getTitle());
            ((TextView) rootView.findViewById(R.id.plot)).setText(curMovie.getPlot());
            ((TextView) rootView.findViewById(R.id.rdate)).setText(Utilities.detailDateFormatter(curMovie.getReleaseDate()));
            ((TextView) rootView.findViewById(R.id.votes)).setText(Utilities.voteFormatter(curMovie.getRating()));



            //fav button click
            Button button = (Button) rootView.findViewById(R.id.favButton);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Do something in response to button click
                    //Log.d(LOG_TAG, "onClick: ");

                    Log.v(LOG_TAG, "qqqqqqqqqqqqqqqqqq");
                    //watchYoutubeVideo("LoebZZ8K5N0");

                }
            });


            //trailers

            ((TextView)rootView.findViewById(R.id.trailersTitle)).setText("Trailers:");

            //TrailersInfo tempTrailer = new TrailersInfo("The Revenant | Official Trailer [HD] | 20th Century FOX", "LoebZZ8K5N0");

            trailers = new ArrayList<TrailersInfo>();
            //trailers.add(tempTrailer);
            trailerListAdapter = new TrailerListAdapter(getActivity(), trailers);
            trailersList = (ListView)rootView.findViewById(R.id.listview_trailers);
            trailersList.setAdapter(trailerListAdapter);
            //getListViewSize(trailersList);


            trailersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //String forecast = mForecastAdapter.getItem(i);
                    //TrailersInfo trailer = (TrailersInfo)trailerListAdapter.getItem(i);
                    //Bundle b = new Bundle();
                    //b.putParcelable("curMovie", trailer);
                    //MovieInfo m = new MovieInfo("As", "ASd", "As", "As", "As", "AS");
                    //Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra("curMovie", m);
                    //startActivity(intent);

                    Log.v(LOG_TAG, "ssssssssssssssssssssss ");
                }

            });
        }

        return rootView;
    }


    @Override
    public void onStart()
    {
        super.onStart();
        FetchTrailersTask fetchTrailersTask = new FetchTrailersTask(getActivity(), trailerListAdapter);
        fetchTrailersTask.execute(curMovie);
        getListViewSize(trailersList);

    }



    public void watchYoutubeVideo(String id){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }

    }

    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i(LOG_TAG, "height of listItem:"+ String.valueOf(totalHeight));
    }
}



