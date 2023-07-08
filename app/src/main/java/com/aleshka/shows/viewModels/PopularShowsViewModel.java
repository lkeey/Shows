package com.aleshka.shows.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aleshka.shows.repositories.PopularShowsRepository;
import com.aleshka.shows.responces.ShowResponse;

public class PopularShowsViewModel extends ViewModel {

    private final PopularShowsRepository popularShowsRepository;

    public PopularShowsViewModel() {
        popularShowsRepository = new PopularShowsRepository();
    }

    public LiveData<ShowResponse> getPopularShows(int page) {
        return popularShowsRepository.getPopularShows(page);
    }

}
