package me.jgao.restaurant_finder;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.jgao.restaurant_finder.model.Restaurant;
import me.jgao.restaurant_finder.model.RestaurantFavList;
import me.jgao.restaurant_finder.model.RestaurantList;
import me.jgao.restaurant_finder.util.DownloadImageTask;

/**
 * Created by jianxin on 3/24/16.
 */
public class SingleResultFragment extends Fragment {

    private final static String TAG = "SingleResultFragment";

    private Restaurant mRestaurant;
    private ImageView mRestaurantImage;
    private TextView mRestaurantName;
    private ImageView mHeartIconImage;
    private TextView mRestaurantAddress;
    private TextView mPhoneTextView;
    private ImageView mRatingBarImg;
    private TextView mRatingValue;
    private TextView mReviewCount;
    private TextView mSnippetText;
    private ImageView mSnippetImage;

    private static final String ARG_REST_ID = "rest_id";
    private static final String TYPE = "me.jgao.rest.type";

    public static SingleResultFragment newInstance(int restId, int type) {
        Bundle args = new Bundle();
        args.putInt(ARG_REST_ID, restId);
        args.putInt(TYPE, type);
        SingleResultFragment resultFragment = new SingleResultFragment();
        resultFragment.setArguments(args);
        return resultFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int rest_id = getArguments().getInt(ARG_REST_ID);
        int type = getArguments().getInt(TYPE);
        if (type == 0) {
            mRestaurant = RestaurantList.getRestaurantList().getRestaurants().get(rest_id);
        } else {
            mRestaurant = new RestaurantFavList().getRestaurants().get(rest_id);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_single_result, container, false);

        ImageView staticMapImage = (ImageView) view.findViewById(R.id.static_google_map_image);
        new DownloadImageTask(staticMapImage).execute(mRestaurant.getStaticMapUrl());

        mRestaurantImage = (ImageView) view.findViewById(R.id.restaurant_image);
        new DownloadImageTask(mRestaurantImage).execute(mRestaurant.getImageUrl());
        mRestaurantName = (TextView) view.findViewById(R.id.restaurant_name);
        mRestaurantName.setText(mRestaurant.getName());

        // for Heart Icon
        mHeartIconImage = (ImageView) view.findViewById(R.id.restaurant_fav_icon);
        if (mRestaurant.isFavorited()) {
            mHeartIconImage.setBackgroundResource(R.drawable.ic_favorite_red_40dp);
        } else {
            mHeartIconImage.setBackgroundResource(R.drawable.ic_favorite_border_red_40dp);
        }

        mHeartIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRestaurant.isFavorited()) {
                    mHeartIconImage.setBackgroundResource(R.drawable.ic_favorite_border_red_40dp);
                    mRestaurant.delete();
                    Log.d(TAG, "Removing from favorites.");
                    mRestaurant.setIsFavorited(false);
                } else {
                    mHeartIconImage.setBackgroundResource(R.drawable.ic_favorite_red_40dp);
                    mRestaurant.save();
                    Log.d(TAG, "Adding to favorites.");
                    mRestaurant.setIsFavorited(true);
                }
            }
        });


        mRestaurantAddress = (TextView) view.findViewById(R.id.restaurant_address);
        mRestaurantAddress.setText(mRestaurant.getDisplayAddress());
        mPhoneTextView = (TextView) view.findViewById(R.id.restaurant_phone);
        mPhoneTextView.setText(mRestaurant.getDisplayPhone());
        mRatingBarImg = (ImageView) view.findViewById(R.id.restaurant_rating_image);
        new DownloadImageTask(mRatingBarImg).execute(mRestaurant.getRatingImgUrl());
        mRatingValue = (TextView) view.findViewById(R.id.restaurant_rating_value);
        mRatingValue.setText(String.valueOf(mRestaurant.getRating()));
        mReviewCount = (TextView) view.findViewById(R.id.restaurant_review_count);
        mReviewCount.setText("Reviews: " + String.valueOf(mRestaurant.getReviewCount()));
        mSnippetImage = (ImageView) view.findViewById(R.id.snippet_image);
        new DownloadImageTask(mSnippetImage).execute(mRestaurant.getSnippetImgUrl());
        mSnippetText = (TextView) view.findViewById(R.id.snippet_text);
        String format = mRestaurant.getSnippetText();
        int pos = 68;
        if (format.length() > 68) {
            while (format.charAt(pos) != ' ') {
                pos--;
            }
            mSnippetText.setText(format.substring(0, pos) + "...");
        } else {
            mSnippetText.setText(format);
        }


        return view;
    }
}
