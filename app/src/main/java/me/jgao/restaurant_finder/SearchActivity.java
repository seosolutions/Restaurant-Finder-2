package me.jgao.restaurant_finder;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by jianxin on 3/18/16.
 */
public class SearchActivity extends AppCompatActivity{
    private final String TAG = "SearchActivity";

    private String[] mNavigationDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_in_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.search_activity_actionbar_title);

        handleIntent(getIntent());
    }


    //If your searchable activity launches in single top mode (android:launchMode="singleTop"), also handle the ACTION_SEARCH intent in the onNewIntent() method. In single top mode, only one instance of your activity is created and subsequent calls to start your activity do not create a new activity on the stack. This launch mode is useful so users can perform searches from the same activity without creating a new activity instance every time.
    @Override
    protected void onNewIntent(Intent intent) {
        //super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        // Get the intent, verify the action and get the query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {

        Log.d(TAG, "doMySearch(): query is: " + query);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.result_fragment_container);

        if (fragment == null) {
            fragment = new SearchResultFragment();
            fm.beginTransaction()
                    .add(R.id.result_fragment_container, fragment)
                    .commit();
        }
    }
}
