package me.jgao.restaurant_finder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jianxin on 3/19/16.
 */
public class CardFragment extends Fragment {

    private TextView mCardTitle;
    private TextView mCardDetail;
    private String mTitle;
    private String mAddress;
    private String mPhone;
    private View mView;
    private final String TAG = "CardFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView()");
        mView = inflater.inflate(R.layout.fragment_card, container, false);
        Log.d(TAG, "" + mView);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreate()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
        update();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach()");
        super.onAttach(context);
    }

    public void updateCardContents(String title, String address, String phone) {
        Log.d(TAG, "updateCardContents()");
        mAddress = address;
        if ("".equals(phone)) {
            mPhone = "Not available.";
        } else {
            mPhone = phone;
        }
        mTitle = title;

        if (mView != null) {
            update();
        }
    }

    private void update() {
        if (mCardDetail == null || mCardTitle == null) {
            mCardDetail = (TextView) mView.findViewById(R.id.card_detail);
            mCardTitle = (TextView) mView.findViewById(R.id.card_title);
        }
        mCardTitle.setText(mTitle);
        mCardDetail.setText(getString(R.string.card_detail, mAddress, mPhone));
    }

}
