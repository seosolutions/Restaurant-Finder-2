package me.jgao.restaurant_finder;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import me.jgao.restaurant_finder.util.AppConstants;


public class MainActivity extends AppCompatActivity {
    private String[] mNavigationDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private AppCompatButton mPickPlaceButton;
    private final String TAG = "MainActivity";
    private Fragment mCardFragment;
    private String mSearchViewText = "";
    /**
     * Request code passed to the PlacePicker intent to identify its result when it returns.
     */
    private static final int REQUEST_PLACE_PICKER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mNavigationDrawerTitles = getResources().getStringArray(R.array.navigation_drawer_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

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
                    Intent i = new Intent(MainActivity.this, FavoriteActivity.class);
                    startActivity(i);
                }
            }
        });


        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_closed);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);


        mPickPlaceButton = (AppCompatButton) findViewById(R.id.pick_place_button);
        mPickPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Use the PlacePicker Builder to construct an Intent.
            Note: This sample demonstrates a basic use case.
            The PlacePicker Builder supports additional properties such as search bounds.
             */
                try {
                    Log.d(TAG, "pick place button clicked");
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(MainActivity.this);
                    // Start the Intent by requesting a result, identified by a request code.
                    startActivityForResult(intent, REQUEST_PLACE_PICKER);

                    // Hide the pick option in the UI to prevent users from starting the picker
                    // multiple times.
                    //showPickAction(false);

                } catch (GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), MainActivity.this, 0);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(MainActivity.this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }

            }
        });


        // set the default place as San Jose
        FragmentManager fm = getSupportFragmentManager();
        mCardFragment = fm.findFragmentById(R.id.main_layout);

        if (mCardFragment == null) {
            mCardFragment = new CardFragment();
            fm.beginTransaction()
                    .add(R.id.main_layout, mCardFragment)
                    .commit();
        }

        ((CardFragment) mCardFragment).updateCardContents("Downtown San Jose", "Downtown San Jose, San Jose, CA, USA", "");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        //added by myself
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchViewText = newText;
                return true;
            }
        });

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Extracts data from PlacePicker result.
     * This method is called when an Intent has been started by calling
     * {@link #startActivityForResult(android.content.Intent, int)}. The Intent for the
     * {@link com.google.android.gms.location.places.ui.PlacePicker} is started with
     * {@link #REQUEST_PLACE_PICKER} request code. When a result with this request code is received
     * in this method, its data is extracted by converting the Intent data to a {@link Place}
     * through the
     * {@link com.google.android.gms.location.places.ui.PlacePicker#getPlace(android.content.Intent,
     * android.content.Context)} call.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // BEGIN_INCLUDE(activity_result)
        Log.d(TAG, "onActivityResult()");
        Log.d(TAG, "requestCode: " + requestCode + "\nresultCode: " + resultCode);
        if (requestCode == REQUEST_PLACE_PICKER) {
            // This result is from the PlacePicker dialog.

            // Enable the picker option
            //showPickAction(true);

            if (resultCode == Activity.RESULT_OK) {
                /* User has picked a place, extract data.
                   Data is extracted from the returned intent by retrieving a Place object from
                   the PlacePicker.
                 */
                final Place place = PlacePicker.getPlace(data, MainActivity.this);

                /* A Place object contains details about that place, such as its name, address
                and phone number. Extract the name, address, phone number, place ID and place types.
                 */
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                final CharSequence phone = place.getPhoneNumber();
                final String placeId = place.getId();
                LatLng latLng = place.getLatLng();
                Log.d(TAG, "Longitude: " + latLng.longitude + "\nLatitude: " + latLng.latitude);

                SharedPreferences.Editor editor = getSharedPreferences(AppConstants.LATLNG_PREF, MODE_PRIVATE).edit();
                editor.putFloat(AppConstants.LATITUDE, (float) latLng.latitude);
                editor.putFloat(AppConstants.LONGITUDE, (float) latLng.longitude);
                editor.apply();

                String attribution = PlacePicker.getAttributions(data);
                if(attribution == null){
                    attribution = "";
                }

                // Update data on card.
//                getCardStream().getCard(CARD_DETAIL)
//                        .setTitle(name.toString())
//                        .setDescription(getString(R.string.detail_text, placeId, address, phone,
//                                attribution));

                // Print data to debug log
                Log.d(TAG, "Place selected: " + placeId + " (" + name.toString() + ")");

                FragmentManager fm = getSupportFragmentManager();
                mCardFragment = fm.findFragmentById(R.id.main_layout);

                if (mCardFragment == null) {
                    mCardFragment = new CardFragment();
                    fm.beginTransaction()
                            .add(R.id.main_layout, mCardFragment)
                            .commit();
                }

                ((CardFragment) mCardFragment).updateCardContents(String.valueOf(name), String.valueOf(address), String.valueOf(phone));
                //((TextView) mCardFragment.getView().findViewById(R.id.card_detail)).setText("HI");


                // Show the card.
                //getCardStream().showCard(CARD_DETAIL);

            } else {
                // User has not selected a place, hide the card.
                //getCardStream().hideCard(CARD_DETAIL);
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        // END_INCLUDE(activity_result)
    }

    // this method should not be private:
    // otherwise error in runtime: could not find this method
    public void onSearchButtonClicked(View view) {
        if ("".equals(mSearchViewText)) {
            NoKeywordDialogFragment dialogFragment = new NoKeywordDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "WarningDialog");
            return;
        }
        startSearch();
    }

    public void startSearch() {
        Log.d(TAG, "startSearch()");
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.setAction(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY, mSearchViewText);
        startActivity(intent);
    }


    // for RadioButtons
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        if (checked) {
            SharedPreferences.Editor editor = getSharedPreferences(AppConstants.SORT_OPTION_PREF, MODE_PRIVATE).edit();

            switch(view.getId()) {
                case R.id.radio_button_distance:
                    editor.putInt(AppConstants.SORT_OPTION_KEY, AppConstants.SORT_BY_DIS);
                    break;
                case R.id.radio_button_relevance:
                    editor.putInt(AppConstants.SORT_OPTION_KEY, AppConstants.SORT_BY_RELEVANCE);
                    break;
            }
            editor.apply();
        }
    }
}
