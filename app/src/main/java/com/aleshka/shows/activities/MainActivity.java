package com.aleshka.shows.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aleshka.shows.R;
import com.aleshka.shows.adapters.ShowsAdapter;
import com.aleshka.shows.adapters.ShowsAdapterOld;
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

        getPopularShows();
    }

    private void getPopularShows() {

        activityMainBinding.setIsLoading(true);

        viewModel.getPopularShows(0).observe(
            this,
            showResponse -> {
                activityMainBinding.setIsLoading(false);

                if (showResponse != null) {
                    if (showResponse.getShows() != null) {

                        shows.addAll(showResponse.getShows());
                        adapter.notifyDataSetChanged();

                        Log.i(TAG, "shows added: " + shows.size());

                    }
                }
            });
    }
}