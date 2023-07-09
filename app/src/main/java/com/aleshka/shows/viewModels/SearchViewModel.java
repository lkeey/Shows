package com.aleshka.shows.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aleshka.shows.repositories.SearchShowRepository;
import com.aleshka.shows.responces.ShowResponse;

public class SearchViewModel extends ViewModel {

    private final SearchShowRepository repository;

    public SearchViewModel() {
        repository = new SearchShowRepository();
    }

    public LiveData<ShowResponse> searchShow(String query, int page) {
        return repository.searchShow(query, page);
    }

}
