package com.aleshka.shows.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aleshka.shows.R;
import com.aleshka.shows.adapters.ShowsAdapter;
import com.aleshka.shows.databinding.ActivityMainBinding;
import com.aleshka.shows.models.Show;
import com.aleshka.shows.viewModels.PopularShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ActivityMain";
    private ActivityMainBinding activityMainBinding;
    private PopularShowsViewModel viewModel;
    private final List<Show> shows = new ArrayList<>();
    private ShowsAdapter adapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(PopularShowsViewModel.class);

        initialize();
    }

    private void initialize() {
        adapter = new ShowsAdapter(shows);

        activityMainBinding.recyclerShows.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityMainBinding.recyclerShows.setAdapter(adapter);

        activityMainBinding.recyclerShows.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!activityMainBinding.recyclerShows.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage++;

                        getPopularShows();
                    }
                }
            }
        });

        getPopularShows();
    }

    private void getPopularShows() {

        toggleLoading();

        viewModel.getPopularShows(currentPage).observe(
            this,
            showResponse -> {

                toggleLoading();

                if (showResponse != null) {
                    if (showResponse.getShows() != null) {
                        totalAvailablePages = showResponse.getTotalPages();

                        int previousCount = shows.size();

                        shows.addAll(showResponse.getShows());

                        adapter.notifyItemRangeInserted(previousCount, shows.size());
                    }
                }
            });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            activityMainBinding.setIsLoading(activityMainBinding.getIsLoading() == null || !activityMainBinding.getIsLoading());
        } else {
            activityMainBinding.setIsLoadingMore(activityMainBinding.getIsLoadingMore() == null || !activityMainBinding.getIsLoadingMore());
        }
    }
}