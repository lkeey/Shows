package com.aleshka.shows.utilities;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingAdapters {

    private static final String TAG = "AdapterBind";

    @BindingAdapter("android:imageURL")
    public static void setImgURL(ImageView imgView, String URL) {
        try {

            Log.i(TAG, "setImg");

            imgView.setAlpha(0f);

            Picasso.get()
                    .load(URL)
                    .noFade()
                    .into(imgView, new Callback() {
                        @Override
                        public void onSuccess() {
                            imgView.animate()
                                    .setDuration(300)
                                    .alpha(1f)
                                    .start();
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });

        } catch (Exception e) {
            Log.i(TAG, "error - " + e.getMessage());
        }
    }
}
