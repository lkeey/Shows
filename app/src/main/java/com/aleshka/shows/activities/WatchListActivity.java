package com.aleshka.shows.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aleshka.shows.R;
import com.aleshka.shows.databinding.ActivityShowDetailBinding;
import com.aleshka.shows.databinding.ActivityWatchListBinding;
import com.aleshka.shows.viewModels.WatchListViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity {

    private static final String TAG = "ActivityWatchList";
    private ActivityWatchListBinding binding;
    private WatchListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(WatchListActivity.this, R.layout.activity_watch_list);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadWatchList();
    }

    private void init() {
        viewModel = new ViewModelProvider(WatchListActivity.this).get(WatchListViewModel.class);

        binding.btnBack.setOnClickListener(view -> onBackPressed());
    }

    private void loadWatchList() {
        binding.setIsLoading(true);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.loadWatchList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shows -> {
                    binding.setIsLoading(false);

                    Log.i(TAG, "amount - " + shows.size());
                })
        );
    }
}