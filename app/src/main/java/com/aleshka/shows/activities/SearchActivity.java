package com.aleshka.shows.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.aleshka.shows.R;
import com.aleshka.shows.adapters.ShowsAdapter;
import com.aleshka.shows.databinding.ActivitySearchBinding;
import com.aleshka.shows.listeners.ShowListener;
import com.aleshka.shows.models.Show;
import com.aleshka.shows.viewModels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements ShowListener {

    private static final String TAG = "ActivitySearch";
    private final List<Show> shows = new ArrayList<>();
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;
    private ShowsAdapter adapter;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SearchActivity.this, R.layout.activity_search);

        init();
    }

    private void init() {
        binding.btnBack.setOnClickListener(view -> onBackPressed());

        binding.recyclerShows.setHasFixedSize(true);

        viewModel = new ViewModelProvider(SearchActivity.this).get(SearchViewModel.class);

        adapter = new ShowsAdapter(shows, SearchActivity.this);

        binding.recyclerShows.setAdapter(adapter);

        binding.editShow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()) {
                    timer = new Timer();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                currentPage = 1;
                                totalAvailablePages = 1;
                                searchShow(editable.toString());
                            });
                        }
                    }, 1000);
                } else {
                    shows.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        binding.recyclerShows.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!binding.recyclerShows.canScrollVertically(1)) {
                    if (!binding.editShow.getText().toString().trim().isEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage ++;
                            searchShow(binding.editShow.getText().toString());
                        }
                    }
                }
            }
        });

        binding.editShow.requestFocus();
    }

    private void searchShow(String query) {
        try {
            toggleLoading();

            viewModel.searchShow(query, currentPage).observe(this, showResponse -> {
                toggleLoading();

                if (showResponse != null) {
                    totalAvailablePages = showResponse.getTotalPages();

                    if (showResponse.getShows() != null) {
                        int oldCount = shows.size();

                        shows.addAll(showResponse.getShows());

                        adapter.notifyItemRangeInserted(oldCount, shows.size());
                    }
                }

            });
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            binding.setIsLoading(binding.getIsLoading() == null || !binding.getIsLoading());
        } else {
            binding.setIsLoadingMore(binding.getIsLoadingMore() == null || !binding.getIsLoadingMore());
        }
    }

    @Override
    public void onShowClicked(Show show) {
        Intent intent = new Intent(SearchActivity.this, ShowDetailActivity.class);
        intent.putExtra("show", show);
        startActivity(intent);
    }
}