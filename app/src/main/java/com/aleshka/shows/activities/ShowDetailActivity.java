package com.aleshka.shows.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.aleshka.shows.R;
import com.aleshka.shows.adapters.ImageSliderAdapter;
import com.aleshka.shows.databinding.ActivityShowDetailBinding;
import com.aleshka.shows.models.Show;
import com.aleshka.shows.responces.ShowDetailResponse;
import com.aleshka.shows.viewModels.ShowDetailViewModel;

import java.util.Locale;

public class ShowDetailActivity extends AppCompatActivity {

    private static final String TAG = "ActivityShowDetail";
    private ActivityShowDetailBinding activityShowDetailBinding;
    private ShowDetailViewModel viewModel;
    private Show receivedShow;

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

    private void setListeners(ShowDetailResponse showDetailResponse) {

        activityShowDetailBinding.btnBack.setOnClickListener(view -> onBackPressed());

        activityShowDetailBinding.readMore.setOnClickListener(view -> {
            if (activityShowDetailBinding.readMore.getText().toString().equals(getString(R.string.read_more))) {
                activityShowDetailBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                activityShowDetailBinding.textDescription.setEllipsize(null);
                activityShowDetailBinding.readMore.setText(R.string.read_less);
            } else {
                activityShowDetailBinding.textDescription.setMaxLines(4);
                activityShowDetailBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                activityShowDetailBinding.readMore.setText(R.string.read_more);
            }
        });

        activityShowDetailBinding.btnWebSite.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(showDetailResponse.getShowDetail().getUrl()));
            startActivity(intent);
        });

        activityShowDetailBinding.btnWebSite.setVisibility(View.VISIBLE);
        activityShowDetailBinding.btnEpisodes.setVisibility(View.VISIBLE);

    }

    private void getShowDetail() {
        activityShowDetailBinding.setIsLoading(true);

        receivedShow = (Show) getIntent().getSerializableExtra("show");

        viewModel.getShowDetail(String.valueOf(receivedShow.getId())).observe(
            ShowDetailActivity.this,
            showDetailResponse -> {
                Log.i(TAG, "received show - " + receivedShow.getName());
                Log.i(TAG, "response - " + showDetailResponse.getShowDetail());

                activityShowDetailBinding.setIsLoading(false);

                if (showDetailResponse.getShowDetail() != null) {
                    if (showDetailResponse.getShowDetail().getPictures() != null) {
                        Log.i(TAG, "pictures - " + showDetailResponse.getShowDetail().getPictures().length);

                        loadImgSlider(showDetailResponse.getShowDetail().getPictures());
                    }

                    setData(showDetailResponse);

                    setListeners(showDetailResponse);

                    loadBasicShowDetails();
                }
            }
        );
    }

    private void setData(ShowDetailResponse showDetailResponse) {

        activityShowDetailBinding.setShowImgURL(showDetailResponse.getShowDetail().getImgPath());
        activityShowDetailBinding.imgShow.setVisibility(View.VISIBLE);

        Log.i(TAG, "description - " + showDetailResponse.getShowDetail().getDescription());

        activityShowDetailBinding.setDescription(String.valueOf(
                HtmlCompat.fromHtml(
                        showDetailResponse.getShowDetail().getDescription(),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                )
        ));

        activityShowDetailBinding.textDescription.setVisibility(View.VISIBLE);
        activityShowDetailBinding.readMore.setVisibility(View.VISIBLE);

        activityShowDetailBinding.setRating(
                String.format(
                        Locale.getDefault(),
                        "%.2f",
                        Double.parseDouble(showDetailResponse.getShowDetail().getRating())
                )
        );

        if (showDetailResponse.getShowDetail().getGenres() != null) {
            activityShowDetailBinding.setGenre(showDetailResponse.getShowDetail().getGenres()[0]);
        } else {
            activityShowDetailBinding.setGenre(getString(R.string.n_a));
        }

        activityShowDetailBinding.setRuntime(showDetailResponse.getShowDetail().getRuntime() + getString(R.string.min));

        activityShowDetailBinding.divider1st.setVisibility(View.VISIBLE);
        activityShowDetailBinding.llInfo.setVisibility(View.VISIBLE);
        activityShowDetailBinding.divider2nd.setVisibility(View.VISIBLE);

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

    private void loadBasicShowDetails() {
        activityShowDetailBinding.setShow(receivedShow);

        activityShowDetailBinding.textName.setVisibility(View.VISIBLE);
        activityShowDetailBinding.textNetwork.setVisibility(View.VISIBLE);
        activityShowDetailBinding.textStarted.setVisibility(View.VISIBLE);
        activityShowDetailBinding.textStatus.setVisibility(View.VISIBLE);

    }
}