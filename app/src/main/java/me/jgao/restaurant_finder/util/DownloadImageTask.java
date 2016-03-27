package me.jgao.restaurant_finder.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by jianxin on 3/25/16.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private final static String TAG = "DownloadImageTask";
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        //Log.d(TAG, urldisplay);
//        int len = urldisplay.length();
//        // image for rating will end with .png
//        // image for restaurant will end with .jpg
//        if (urldisplay.charAt(len - 2) == 'p') {
//            // replace ms.jpg with 348s.jpg
//            // http://stackoverflow.com/questions/17965691/yelp-api-ios-getting-a-larger-image
//            urldisplay = urldisplay.substring(0, len - 6) + "348s.jpg";
//        }

        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}