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
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

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




    private TrailerListAdapter trailerListAdapter;
    ListView trailersList;
    MovieInfo curMovie;
    public static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    List<TrailersInfo> trailers;

    List<MovieReview> reviews;
    ReviewAdapter reviewAdapter;
    ListView reviewsListview;

    String movieId = null;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

//        Intent intent = getActivity().getIntent();
//        if(intent != null && intent.hasExtra("curMovie")){
//
//            curMovie = (MovieInfo)intent.getSerializableExtra("curMovie");
//
//            ImageView imageView = (ImageView)rootView.findViewById(R.id.poster);
//            Picasso.with(getActivity()).load(curMovie.getImageUrl()).into(imageView);
//            ((TextView) rootView.findViewById(R.id.title)).setText(curMovie.getTitle());
//            ((TextView) rootView.findViewById(R.id.plot)).setText(curMovie.getPlot());
//            ((TextView) rootView.findViewById(R.id.rdate)).setText(Utilities.detailDateFormatter(curMovie.getReleaseDate()));
//            ((TextView) rootView.findViewById(R.id.votes)).setText(Utilities.voteFormatter(curMovie.getRating()));
//
//
//
//
//
//            //FAV BUTTON CLICK
//            Button button = (Button) rootView.findViewById(R.id.favButton);
//            button.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    uri = Uri.parse("content://com.example.android.popularmovies/movies");
//
//                    //CHECK IF ALREADY MARKED AS FAVORITE
//                    Cursor cursor = getContext().getContentResolver().query(uri, MOVIE_PROJECTION, ContentProvider.Movie.KEY_MOVIEID + " = ?", new String[]{curMovie.getId()}, null);
//                    if (!cursor.moveToFirst()) {
//
//                        //INSERT INTO DB
//                        ContentValues movieValue = new ContentValues();
//
//                        movieValue.put(ContentProvider.Movie.KEY_IMGURL, curMovie.getImageUrl());
//                        movieValue.put(ContentProvider.Movie.KEY_TITLE, curMovie.getTitle());
//                        movieValue.put(ContentProvider.Movie.KEY_PLOT, curMovie.getPlot());
//                        movieValue.put(ContentProvider.Movie.KEY_RATING, curMovie.getRating());
//                        movieValue.put(ContentProvider.Movie.KEY_RELEASEDATE, curMovie.getReleaseDate());
//                        movieValue.put(ContentProvider.Movie.KEY_MOVIEID, curMovie.getId());
//
//                        getContext().getContentResolver().insert(uri, movieValue);
//
//
//                        //getContext().getContentResolver().query(uri, MOVIE_PROJECTION, null, null, null);
//                        //ContentProvider a = MOVIE_PROJECTION[0];
//
////                        //String a = "saddasda";
////                        String a = MOVIE_PROJECTION[0];
////                        Log.v(LOG_TAG, "qmovie is: " + a);
//
//                    }
//
//
//                }
//            });
//
//
//            //TRAILERS
//            ((TextView)rootView.findViewById(R.id.trailersTitle)).setText("Trailers:");
//            trailers = new ArrayList<TrailersInfo>();
//            trailerListAdapter = new TrailerListAdapter(getActivity(), trailers);
//            trailersList = (ListView)rootView.findViewById(R.id.listview_trailers);
//            trailersList.setAdapter(trailerListAdapter);
//
//            //getListViewSize(trailersList);
//
//
//
//            //View mLinearView = inflater.inflate(R.layout.listview_reviews_item, null);
//
//
//
//
//            trailersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    TrailersInfo trailer = (TrailersInfo) trailerListAdapter.getItem(i);
//                    String UrlId = trailer.getTrailerUrl();
//                    watchYoutubeVideo(UrlId);
//                }
//
//            });
//
//
//            //REVIEWS
//            ((TextView)rootView.findViewById(R.id.reviewsTitle)).setText("Reviews:");
//            reviews = new ArrayList<MovieReview>();
//            //MovieReview tempr = new MovieReview("JK", "ROWLING, harry potter is great");
//            //reviews.add(tempr);
//            reviewAdapter = new ReviewAdapter(getActivity(), reviews);
//            reviewsListview = (ListView)rootView.findViewById(R.id.listview_reviews);
//            reviewsListview.setAdapter(reviewAdapter);
//            //getListViewSize(reviewsListview);
//
//
//
//
//
//
//        }

        return rootView;
    }

    public void TrailersUpdated(){
        getListViewSize(trailersList);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        FetchTrailersTask fetchTrailersTask = new FetchTrailersTask(getActivity(), trailerListAdapter);
        fetchTrailersTask.execute(curMovie);
        getListViewSize(trailersList);

        FetchReviewsTask fetchReviewsTask = new FetchReviewsTask(getActivity(), reviewAdapter);
        fetchReviewsTask.execute(curMovie);
        getListViewSize(reviewsListview);

    }


//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
//        super.onActivityCreated(savedInstanceState);
//    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        if ( null != uri ) {
//            // Now create and return a CursorLoader that will take care of
//            // creating a Cursor for the data being displayed.
//            return new CursorLoader(
//                    getActivity(),
//                    uri,
//                    MOVIE_PROJECTION,
//                    null,
//                    null,
//                    null
//            );
//        }
//        return null;
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        if (data != null && data.moveToFirst()) {
//
//                // Read description from cursor and update view
//                String title = data.getString(INDEX_TITLE);
//                Log.v(LOG_TAG, "loadertitle :" + title);
//                //data.moveToNext();
//            }
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) { }


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
        Log.i(LOG_TAG, "height of listItem:" + String.valueOf(totalHeight));
    }

}
//
//<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
//        xmlns:tools="http://schemas.android.com/tools"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        tools:context="com.example.android.popularmovies.DetailActivityFragment"
//        tools:showIn="@layout/activity_detail"


