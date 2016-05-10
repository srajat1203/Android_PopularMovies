package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    MovieInfo curMovie;
    public static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

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




//            Button button = (Button) rootView.findViewById(R.id.favButton);
//            button.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    // Do something in response to button click
//                    Log.d(LOG_TAG, "onClick: ");
//
//
//                }
//            });


            //trailers

            ((TextView)rootView.findViewById(R.id.trailersTitle)).setText("Trailers:");

            TrailersInfo tempTrailer = new TrailersInfo("The Revenant | Official Trailer [HD] | 20th Century FOX", "LoebZZ8K5N0");

            List<TrailersInfo> trailers = new ArrayList<TrailersInfo>();
            trailers.add(tempTrailer);
            trailerListAdapter = new TrailerListAdapter(getActivity(), trailers);
            ListView trailersList = (ListView)rootView.findViewById(R.id.listview_trailers);
            trailersList.setAdapter(trailerListAdapter);






        }




//
//        //trailers
//        TrailersInfo tempTrailer = new TrailersInfo("The Revenant | Official Trailer [HD] | 20th Century FOX", "LoebZZ8K5N0");
//
//        List<TrailersInfo> trailers = new ArrayList<TrailersInfo>();
//        trailers.add(tempTrailer);
//        trailerListAdapter = new TrailerListAdapter(getActivity(), trailers);
//        ListView trailersList = (ListView)rootView.findViewById(R.id.listview_trailers);
//        trailersList.setAdapter(trailerListAdapter);



//        //Button trailerButton = (Button) rootView.findViewById(R.id.playTrailerButton);
//        trailersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                //TrailersInfo trailer = trailerListAdapter.getItem(i);
//                TrailersInfo trailer = (TrailersInfo) trailerListAdapter.getItem(i);
//                String UrlId = trailer.getTrailerUrl();
//                watchYoutubeVideo(UrlId);
//                //Bundle b = new Bundle();
//                //b.putParcelable("curMovie", curMovie);
//                //Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra("curMovie", curMovie);
//                //startActivity(intent);
//            }
//
//        });


        return rootView;
    }


//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        //FetchTrailersTask fetchTrailersTask = new FetchTrailersTask(getActivity(), trailerListAdapter);
//        //fetchTrailersTask.execute(curMovie);
//    }


//
//    public void watchYoutubeVideo(String id){
//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
//            startActivity(intent);
//        } catch (ActivityNotFoundException ex) {
//            Intent intent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("http://www.youtube.com/watch?v=" + id));
//            startActivity(intent);
//        }
//    }




}
