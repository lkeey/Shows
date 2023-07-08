package com.aleshka.shows.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.aleshka.shows.R;
import com.aleshka.shows.adapters.ImageSliderAdapter;
import com.aleshka.shows.databinding.ActivityShowDetailBinding;
import com.aleshka.shows.models.Show;
import com.aleshka.shows.viewModels.ShowDetailViewModel;

public class ShowDetailActivity extends AppCompatActivity {

    private static final String TAG = "ActivityShowDetail";
    private ActivityShowDetailBinding activityShowDetailBinding;
    private ShowDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityShowDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_detail);

        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(ShowDetailViewModel.class);

        getShowDetail();
    }

    private void getShowDetail() {
        activityShowDetailBinding.setIsLoading(true);

        Show show = (Show) getIntent().getSerializableExtra("show");

        viewModel.getShowDetail(String.valueOf(show.getId())).observe(
            ShowDetailActivity.this,
            showDetailResponse -> {
                Log.i(TAG, "received show - " + show.getName());
                Log.i(TAG, "response - " + showDetailResponse.getShowDetail());

                activityShowDetailBinding.setIsLoading(false);

                if (showDetailResponse.getShowDetail() != null) {
                    if (showDetailResponse.getShowDetail().getPictures() != null) {
                        Log.i(TAG, "pictures - " + showDetailResponse.getShowDetail().getPictures().length);

                        loadImgSlider(showDetailResponse.getShowDetail().getPictures());
                    }
                }
            }
        );
    }

    private void loadImgSlider(String[] images) {
        activityShowDetailBinding.viewPager.setOffscreenPageLimit(1);
        activityShowDetailBinding.viewPager.setAdapter(new ImageSliderAdapter(images));
        activityShowDetailBinding.viewPager.setVisibility(View.VISIBLE);
        activityShowDetailBinding.viewFading.setVisibility(View.VISIBLE);

        setUpSliderIndicator(images.length);

        activityShowDetailBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setUpSliderIndicator(int count) {
        ImageView[] indicators = new ImageView[count];

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(8, 0, 0, 0);

        for (int i=0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slider_indicator_inactive
            ));

            indicators[i].setLayoutParams(params);
            activityShowDetailBinding.llSlider.addView(indicators[i]);
        }

        activityShowDetailBinding.llSlider.setVisibility(View.VISIBLE);

        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position) {
        int childCount = activityShowDetailBinding.llSlider.getChildCount();

        for (int i=0; i < childCount; i++) {
            ImageView imageView = (ImageView) activityShowDetailBinding.llSlider.getChildAt(i);

            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(ShowDetailActivity.this, R.drawable.background_slider_indicator));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(ShowDetailActivity.this, R.drawable.background_slider_indicator_inactive));

            }
        }
    }
}