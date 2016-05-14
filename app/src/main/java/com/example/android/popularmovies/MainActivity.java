package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private String mSortOrder;

    public static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSortOrder = Utilities.getPreferredOrder(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        String sortOder = Utilities.getPreferredOrder(this);
//        // update the location in our second pane using the fragment manager
//        if (sortOder != null && !sortOder.equals(mSortOrder)) {
//            MainActivityFragment maf = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_main);
//            if ( null != maf ) {
//                maf.onOrderChanged();
//                Log.i(LOG_TAG, "onOrderChanged called");
//            }
//            mSortOrder = sortOder;
//
//        }
//    }
}
