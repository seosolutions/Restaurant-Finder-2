package me.jgao.restaurant_finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import me.jgao.restaurant_finder.model.Restaurant;
import me.jgao.restaurant_finder.model.RestaurantFavList;
import me.jgao.restaurant_finder.model.RestaurantList;
import me.jgao.restaurant_finder.util.AppConstants;
import me.jgao.restaurant_finder.util.DownloadImageTask;

/**
 * Created by jianxin on 3/18/16.
 */
public class SearchResultFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RestaurantAdapter mRestaurantAdapter;
    private String frag_type;


    private void updateUI() {

        if (mRestaurantAdapter == null) {

            if (frag_type.equals(AppConstants.FRAGMENT_FOR_FAV)) {
                RestaurantFavList restaurantFavList = new RestaurantFavList();
                mRestaurantAdapter = new RestaurantAdapter(restaurantFavList.getRestaurants(), restaurantFavList.getCount());
            } else {
                mRestaurantAdapter = new RestaurantAdapter(RestaurantList.getRestaurantList().getRestaurants(), RestaurantList.getRestaurantList().getCount());
            }

            mRecyclerView.setAdapter(mRestaurantAdapter);
        } else {
            //mRestaurantAdapter.notifyDataSetChanged();
        }
    }


    // added for differentiating between favorite list or result list
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag_type = getArguments().getString(AppConstants.RES_FRAGMENT_ARG);
    }


    private class RestaurantHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mAddressTextView;
        private TextView mRatingTextView;
        private ImageView mRatingImgView;
        private ImageView mRestaurantImgView;

        private Restaurant mRestaurant;

        public RestaurantHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            mNameTextView = (TextView) view.findViewById(R.id.restaurant_title);
            mAddressTextView = (TextView) view.findViewById(R.id.restaurant_address);
            mRatingTextView = (TextView) view.findViewById(R.id.restaurant_rating_value);
            mRatingImgView = (ImageView) view.findViewById(R.id.restaurant_rating_image);
            mRestaurantImgView = (ImageView) view.findViewById(R.id.restaurant_image);
        }

        public void bindRestaurant(Restaurant restaurant) {
            mRestaurant = restaurant;
            mNameTextView.setText(String.valueOf(mRestaurant.getPos() + 1) + ". " + mRestaurant.getName());
            mAddressTextView.setText(mRestaurant.getDisplayAddress());
            mRatingTextView.setText(String.valueOf(mRestaurant.getRating()));
            new DownloadImageTask(mRatingImgView).execute(mRestaurant.getRatingImgUrl());
            new DownloadImageTask(mRestaurantImgView).execute(mRestaurant.getImageUrl());
        }

        @Override
        public void onClick(View view) {
            Intent intent;
            if (frag_type.equals(AppConstants.FRAGMENT_FOR_SEARCH)) {
                intent = SingleResultActivity.newIntent(getActivity(), mRestaurant.getPos(), 0);
            } else {
                intent = SingleResultActivity.newIntent(getActivity(), mRestaurant.getPos(), 1);
            }
            startActivity(intent);
        }
    }


    private class RestaurantAdapter extends RecyclerView.Adapter<RestaurantHolder> {
        private List<Restaurant> mRestaurants;
        private RestaurantList mRestaurantList;
        private int count;
        public RestaurantAdapter(List<Restaurant> restaurants, int count) {
            //mRestaurantList = res;
            mRestaurants = restaurants;
            this.count = count;
        }

        @Override
        public void onBindViewHolder(RestaurantHolder holder, int position) {
            Restaurant restaurant = mRestaurants.get(position);
            holder.bindRestaurant(restaurant);
        }

        @Override
        public RestaurantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_restaurant, parent, false);
            return new RestaurantHolder(view);
        }

        @Override
        public int getItemCount() {
            //return mRestaurantList.getCount();
            return count;
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.result_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
