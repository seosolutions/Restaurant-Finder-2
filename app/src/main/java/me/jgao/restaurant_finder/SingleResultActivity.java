package me.jgao.restaurant_finder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import me.jgao.restaurant_finder.model.Restaurant;
import me.jgao.restaurant_finder.model.RestaurantFavList;
import me.jgao.restaurant_finder.model.RestaurantList;

/**
 * Created by jianxin on 3/24/16.
 */
public class SingleResultActivity extends AppCompatActivity {

    private static final String EXTRA_REST_ID = "me.jgao.restaurant_finder.restaurant_id";
    //  to distinguish between result list/ fav list
    private static final String EXTRA_TYPE = "me.jgao.restaurant_finder.activity_type";
    private static final String TAG = "SingleResultActivity";

    private ViewPager mViewPager;
    private List<Restaurant> mRestaurants;

    private DrawerLayout mDrawerLayout;

    public static Intent newIntent (Context packageContext, int restaurantIndex, int type) {
        Intent intent = new Intent(packageContext, SingleResultActivity.class);
        intent.putExtra(EXTRA_REST_ID, restaurantIndex);
        // 0 for search result list, 1 for fav list
        intent.putExtra(EXTRA_TYPE, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_result);

        // for toobar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_in_single_result);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.single_result_activity_actionbar_title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_icon_app_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp);

        // for drawer
        String[] mNavigationDrawerTitles;
        ListView mDrawerList;

        mNavigationDrawerTitles = getResources().getStringArray(R.array.navigation_drawer_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_in_single_result_activity);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_in_single_result_activity);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mNavigationDrawerTitles));


        // for view pager
        int rest_id = getIntent().getIntExtra(EXTRA_REST_ID, 0);
        Log.d(TAG, "restaurant pos: " + rest_id);
        final int type = getIntent().getIntExtra(EXTRA_TYPE, 0);
        mViewPager = (ViewPager) findViewById(R.id.activity_single_result_view_pager);

        if (type == 0) {
            //mRestaurants = RestaurantList.getRestaurantList().getRestaurants();
        } else {
            //RestaurantFavList restaurantFavList = new RestaurantFavList();
            //mRestaurants = restaurantFavList.getRestaurants();
        }



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
                    if (type == 0) {
                        Intent i = new Intent(SingleResultActivity.this, FavoriteActivity.class);
                        startActivity(i);
                    } else {
                        onBackPressed();
                    }
                } else {
                    // 'search' clicked
                    Intent intent = new Intent(SingleResultActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });



        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return SingleResultFragment.newInstance(position, type);
            }

            @Override
            public int getCount() {
                if (type == 0) {
                    return RestaurantList.getRestaurantList().getCount();
                } else {
                    return (int) Restaurant.count(Restaurant.class);
                }
            }
        });

        mViewPager.setCurrentItem(rest_id);
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
