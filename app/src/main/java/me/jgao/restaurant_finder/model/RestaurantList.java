package me.jgao.restaurant_finder.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianxin on 3/25/16.
 */
public class RestaurantList {

    private List<Restaurant> sRestaurants;
    private static RestaurantList sRestaurantList;

    private int count;
    public int getCount() {
        return count;
    }
    public void setCount (int count) {
        this.count = count;
    }

    public static RestaurantList getRestaurantList() {
        if (sRestaurantList == null) {
            sRestaurantList = new RestaurantList();
        }
        return sRestaurantList;
    }

    private RestaurantList() {
        sRestaurants = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            sRestaurants.add(new Restaurant("","",.0,"","",.0,0,"","",.0,.0,"", i));
        }
    }
    public List<Restaurant> getRestaurants() {
        return sRestaurants;
    }
}
