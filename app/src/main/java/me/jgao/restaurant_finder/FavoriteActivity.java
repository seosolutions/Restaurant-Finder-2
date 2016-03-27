package me.jgao.restaurant_finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;

import me.jgao.restaurant_finder.util.AppConstants;

/**
 * Created by jianxin on 3/26/16.
 */
public class FavoriteActivity extends AppCompatActivity{

    private final String TAG = "FavoriteActivity";

    private String[] mNavigationDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_in_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.fav_activity_actionbar_title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_icon_app_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp);


        mNavigationDrawerTitles = getResources().getStringArray(R.array.navigation_drawer_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_in_search_activity);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_in_search_activity);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mNavigationDrawerTitles));

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Log.d(TAG, "" + position);
                mDrawerLayout.closeDrawers();
                if (position == 2) {
                    AboutDialogFragment dialog = new AboutDialogFragment();
                    dialog.show(getSupportFragmentManager(), "AboutDialog");
                } else if (position == 1) {
                    // show favorites page:
                } else {
                    //Intent i = new Intent(FavoriteActivity.this, MainActivity.class);
                    //startActivity(i);
                    onBackPressed();
                }
            }
        });



        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.result_fragment_container);

        if (fragment == null) {
            fragment = new SearchResultFragment();

            // added myself:
            // so the new fragment knows if it's to display search result list or favorite list
            Bundle args = new Bundle();
            args.putString(AppConstants.RES_FRAGMENT_ARG, AppConstants.FRAGMENT_FOR_FAV);
            fragment.setArguments(args);
            fm.beginTransaction()
                    .add(R.id.result_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
