package me.jgao.restaurant_finder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jgao.restaurant_finder.model.Restaurant;
import me.jgao.restaurant_finder.model.RestaurantList;

/**
 * Created by jianxin on 3/24/16.
 */
public class SingleResultFragment extends Fragment {

    private Restaurant mRestaurant;

    private static final String ARG_REST_ID = "rest_id";

    public static SingleResultFragment newInstance(int restId) {
        Bundle args = new Bundle();
        args.putInt(ARG_REST_ID, restId);
        SingleResultFragment resultFragment = new SingleResultFragment();
        resultFragment.setArguments(args);
        return resultFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int rest_id = getArguments().getInt(ARG_REST_ID);
        mRestaurant = RestaurantList.getRestaurantList().getRestaurants().get(rest_id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_single_result, container, false);

        return view;
    }
}
