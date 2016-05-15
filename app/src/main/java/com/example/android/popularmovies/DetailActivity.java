package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class DetailActivity extends AppCompatActivity {

    public static final String LOG_TAG = DetailActivity.class.getSimpleName();


    MovieInfo curMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //FrameLayout includedView = findViewById(R.id.fragment_detail);
        if(savedInstanceState == null){


            Intent intent = this.getIntent();
            if(intent != null && intent.hasExtra("curMovie"))
            {
                curMovie = (MovieInfo)intent.getSerializableExtra("curMovie");

                Log.i(LOG_TAG, "Getting here " + curMovie.getTitle());
            }


            Bundle arguments = new Bundle();
            arguments.putSerializable("curMovie", curMovie);

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detail, fragment)
                    .commit();
        }
    }

}
