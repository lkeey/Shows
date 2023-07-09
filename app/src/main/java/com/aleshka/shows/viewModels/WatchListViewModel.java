package com.aleshka.shows.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.aleshka.shows.database.ShowsDatabase;
import com.aleshka.shows.models.Show;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class WatchListViewModel extends AndroidViewModel {

    private ShowsDatabase showsDatabase;

    public WatchListViewModel(@NonNull Application application) {
        super(application);

        showsDatabase = ShowsDatabase.getDatabase(application);
    }

    public Flowable<List<Show>> loadWatchList() {
        return showsDatabase.showDao().getWatchList();
    }

    public Completable removeShowFromWatchList(Show show) {
        return showsDatabase.showDao().removeFromWatchList(show);
    }
}
