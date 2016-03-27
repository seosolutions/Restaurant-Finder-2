package me.jgao.restaurant_finder;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Coordinate;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jgao.restaurant_finder.model.Restaurant;
import me.jgao.restaurant_finder.model.RestaurantList;
import me.jgao.restaurant_finder.util.AppConstants;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jianxin on 3/18/16.
 */
public class SearchActivity extends AppCompatActivity{
    private final String TAG = "SearchActivity";

    private String[] mNavigationDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    Map<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_in_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.search_activity_actionbar_title);
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
                    Intent i = new Intent(SearchActivity.this, FavoriteActivity.class);
                    startActivity(i);
                } else {
                    // 'Search' clicked
                    onBackPressed();
                }
            }
        });




        // initialize yelp api params
        params = new HashMap<>();
        params.put("limit", "20");
        // locale params
        //params.put("lang", "en");

        handleIntent(getIntent());
    }


    //If your searchable activity launches in single top mode (android:launchMode="singleTop"), also handle the ACTION_SEARCH intent in the onNewIntent() method. In single top mode, only one instance of your activity is created and subsequent calls to start your activity do not create a new activity on the stack. This launch mode is useful so users can perform searches from the same activity without creating a new activity instance every time.
    @Override
    protected void onNewIntent(Intent intent) {
        //super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent()");
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Log.d(TAG, "handleIntent()");
        // Get the intent, verify the action and get the query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {

        Log.d(TAG, "doMySearch(): query is: " + query);

        int sortingOption = getSharedPreferences(AppConstants.SORT_OPTION_PREF, MODE_PRIVATE).getInt(AppConstants.SORT_OPTION_KEY, AppConstants.SORT_BY_DIS);
        params.put("sort", String.valueOf(sortingOption));
        params.put("term", query);

        YelpAPIFactory apiFactory = new YelpAPIFactory(getString(R.string.yelp_consumer_key), getString(R.string.yelp_consumer_secret), getString(R.string.yelp_token), getString(R.string.yelp_token_secret));
        YelpAPI yelpAPI = apiFactory.createAPI();


        SharedPreferences pref = getSharedPreferences(AppConstants.LATLNG_PREF, MODE_PRIVATE);
        float lat = pref.getFloat(AppConstants.LATITUDE, AppConstants.SJDT_LAT);
        float lon = pref.getFloat(AppConstants.LONGITUDE, AppConstants.SJDT_LONG);
        CoordinateOptions coordinate = CoordinateOptions.builder()
                .latitude((double) lat)
                .longitude((double) lon).build();

        Call<SearchResponse> call = yelpAPI.search(coordinate, params);
        //Response<SearchResponse> response = call.execute();
        // You can also pass in a Callback object to send the request asynchronously.
        Callback<SearchResponse> callback = new Callback<SearchResponse>() {
            @Override
            public void onResponse(Response<SearchResponse> response, Retrofit retrofit) {
                SearchResponse searchResponse = response.body();
                int total = searchResponse.total();
                List<Business> businesses = searchResponse.businesses();
                int actual_returned = businesses.size();
                int num_to_use = Math.min(20, actual_returned);

                RestaurantList.getRestaurantList().setCount(num_to_use);
                List<Restaurant> restaurants = RestaurantList.getRestaurantList().getRestaurants();
                for (int i = 0; i < num_to_use; ++i) {
                    Business business = businesses.get(i);
                    String address = business.location().displayAddress().toString();
                    address = address.substring(1, address.length() - 1);
                    Coordinate coordinate = business.location().coordinate();
                    String coordStr = coordinate.latitude() + "," + coordinate.longitude();
                    restaurants.set(i, new Restaurant(
                            business.name(),
                            business.displayPhone(),
                            business.distance(),
                            business.imageUrl().substring(0, business.imageUrl().length() - 6) + "348s.jpg",
                            business.ratingImgUrlLarge(),
                            business.rating(),
                            business.reviewCount(),
                            business.snippetImageUrl(),
                            business.snippetText(),
                            business.location().coordinate().latitude(),
                            business.location().coordinate().longitude(),
                            address,
                            i,
                            "http://maps.google.com/maps/api/staticmap?center=" + coordStr + "&zoom=15&size=600x300&markers=color:yellow%7C" + coordStr
                    ));

                    // Tell if the restaurant is in the database
                    List<Restaurant> dbRes = Restaurant.findWithQuery(Restaurant.class, "Select * from Restaurant where name = ?", business.name());
                    if (dbRes != null && dbRes.size() > 0) {
                        restaurants.get(i).setIsFavorited(true);
                    }

                }

                // Update UI text with the searchResponse.
                startSearchResultFragment();

            }
            @Override
            public void onFailure(Throwable t) {
                // HTTP error happened, do something to handle it.
            }
        };

        call.enqueue(callback);

    }

    private void startSearchResultFragment() {
        Log.d(TAG, "startSearchResultFragment()");
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.result_fragment_container);

        if (fragment == null) {
            fragment = new SearchResultFragment();

            // added myself:
            // so the new fragment knows if it's to display search result list or favorite list
            Bundle args = new Bundle();
            args.putString(AppConstants.RES_FRAGMENT_ARG, AppConstants.FRAGMENT_FOR_SEARCH);
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
