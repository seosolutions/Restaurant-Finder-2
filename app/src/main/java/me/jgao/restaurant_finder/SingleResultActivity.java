package me.jgao.restaurant_finder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

import me.jgao.restaurant_finder.model.Restaurant;
import me.jgao.restaurant_finder.model.RestaurantList;

/**
 * Created by jianxin on 3/24/16.
 */
public class SingleResultActivity extends FragmentActivity {

    private static final String EXTRA_REST_ID = "me.jgao.restaurant_finder.restaurant_id";

    private ViewPager mViewPager;
    private List<Restaurant> mRestaurants;

    public static Intent newIntent (Context packageContext, int restaurantIndex) {
        Intent intent = new Intent(packageContext, SingleResultActivity.class);
        intent.putExtra(EXTRA_REST_ID, restaurantIndex);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_result);

        int rest_id = getIntent().getIntExtra(EXTRA_REST_ID, 0);
        mViewPager = (ViewPager) findViewById(R.id.activity_single_result_view_pager);
        mRestaurants = RestaurantList.getRestaurantList().getRestaurants();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return SingleResultFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return RestaurantList.getRestaurantList().getCount();
            }
        });

        mViewPager.setCurrentItem(rest_id);
    }
}
