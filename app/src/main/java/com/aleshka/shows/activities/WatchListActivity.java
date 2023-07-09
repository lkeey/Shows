package com.aleshka.shows.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.aleshka.shows.R;
import com.aleshka.shows.adapters.WatchListAdapter;
import com.aleshka.shows.databinding.ActivityWatchListBinding;
import com.aleshka.shows.listeners.WatchListListener;
import com.aleshka.shows.models.Show;
import com.aleshka.shows.utilities.TempDataHolder;
import com.aleshka.shows.viewModels.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements WatchListListener {

    private static final String TAG = "ActivityWatchList";
    private final List<Show> watchList = new ArrayList<>();
    private ActivityWatchListBinding binding;
    private WatchListViewModel viewModel;
    private WatchListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(WatchListActivity.this, R.layout.activity_watch_list);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (TempDataHolder.IS_WATCH_LIST_UPDATED) {
            loadWatchList();

            TempDataHolder.IS_WATCH_LIST_UPDATED = false;
        }
    }

    private void init() {
        viewModel = new ViewModelProvider(WatchListActivity.this).get(WatchListViewModel.class);

        binding.btnBack.setOnClickListener(view -> onBackPressed());

        loadWatchList();
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

                if (watchList.size() > 0) {
                    watchList.clear();
                }

                watchList.addAll(shows);

                adapter = new WatchListAdapter(watchList, WatchListActivity.this);

                binding.watchList.setAdapter(adapter);
                binding.watchList.setVisibility(View.VISIBLE);

                compositeDisposable.dispose();
            })
        );
    }

    @Override
    public void onShowClicked(Show show) {
        Intent intent = new Intent(WatchListActivity.this, ShowDetailActivity.class);
        intent.putExtra("show", show);
        startActivity(intent);
    }

    @Override
    public void removeShowFromWatchList(Show show, int position) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
            viewModel.removeShowFromWatchList(show)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    watchList.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                    compositeDisposable.dispose();
                })
        );
    }


}