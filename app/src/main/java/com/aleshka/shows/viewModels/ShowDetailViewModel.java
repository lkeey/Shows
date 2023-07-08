package com.aleshka.shows.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aleshka.shows.repositories.ShowDetailRepository;
import com.aleshka.shows.responces.ShowDetailResponse;

public class ShowDetailViewModel extends ViewModel {

    private final ShowDetailRepository showDetailRepository;

    public ShowDetailViewModel() {
        showDetailRepository = new ShowDetailRepository();
    }

    public LiveData<ShowDetailResponse> getShowDetail(String showId) {
         return showDetailRepository.getShowDetail(showId);
    }

}
