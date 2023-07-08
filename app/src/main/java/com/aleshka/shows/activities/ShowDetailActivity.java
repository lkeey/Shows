package com.aleshka.shows.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.aleshka.shows.R;
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
                    activityShowDetailBinding.setIsLoading(false);
                    Log.i(TAG, "received show - " + show.getName());
                }
        );
    }
}