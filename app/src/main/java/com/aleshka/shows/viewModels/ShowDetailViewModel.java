package com.aleshka.shows.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aleshka.shows.database.ShowsDatabase;
import com.aleshka.shows.models.Show;
import com.aleshka.shows.repositories.ShowDetailRepository;
import com.aleshka.shows.responces.ShowDetailResponse;

import io.reactivex.rxjava3.core.Completable;

public class ShowDetailViewModel extends AndroidViewModel {

    private final ShowDetailRepository showDetailRepository;
    private ShowsDatabase showsDatabase;

    public ShowDetailViewModel(@NonNull Application app) {
        super(app);

        showDetailRepository = new ShowDetailRepository();
        showsDatabase = ShowsDatabase.getDatabase(app);
    }

    public LiveData<ShowDetailResponse> getShowDetail(String showId) {
         return showDetailRepository.getShowDetail(showId);
    }

    public Completable addToWatchList(Show show) {
        return showsDatabase.showDao().addToWatchList(show);
    }

}
