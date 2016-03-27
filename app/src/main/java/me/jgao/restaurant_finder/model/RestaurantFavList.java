package me.jgao.restaurant_finder.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianxin on 3/26/16.
 */
public class RestaurantFavList{

//    private final String TAG = "RestaurantFavList";
//
//    private List<Restaurant> mRestaurants;
//    private int count;
//
//    public List<Restaurant> getRestaurants () {
//
//        mRestaurants = new ArrayList<>();
//
//        long longcount = Restaurant.count(Restaurant.class);
//        Log.d(TAG, "count: " + longcount);
//
//        int i = 0;
//        for (int j = 0; j < 20; ++i, ++j) {
//            Restaurant restaurant = Restaurant.findById(Restaurant.class, i + 1);
//            Log.d(TAG, "i: " + i + "\nRestaurant: " + restaurant);
//
//            if (restaurant != null) {
//                restaurant.setPos(i);
//                restaurant.setIsFavorited(true);
//                mRestaurants.add(i, restaurant);
//                i++;
//            }
//
//        }
//        count = i;
//        return mRestaurants;
//    }
//
//    public int getCount() {
//        return count;
//    }

    private final String TAG = "RestaurantFavList";

    private List<Restaurant> mRestaurants;
    private long count;

    public List<Restaurant> getRestaurants () {

        mRestaurants = new ArrayList<>();

        count = Restaurant.count(Restaurant.class);


        for (int i = 0; i < count; ++i) {
            Restaurant restaurant = Restaurant.findById(Restaurant.class, i + 1);
            Log.d(TAG, "i: " + i + "\nRestaurant: " + restaurant);

            if (restaurant != null) {
                restaurant.setPos(i);
                restaurant.setIsFavorited(true);
                mRestaurants.add(i, restaurant);
            }

        }
        return mRestaurants;
    }

    public int getCount() {
        return (int) count;
    }
}
