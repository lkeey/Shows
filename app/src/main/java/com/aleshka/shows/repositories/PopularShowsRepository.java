package com.aleshka.shows.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aleshka.shows.models.Show;
import com.aleshka.shows.network.ApiClient;
import com.aleshka.shows.network.ApiService;
import com.aleshka.shows.responces.ShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularShowsRepository {

    private ApiService apiService;

    public PopularShowsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<ShowResponse> getPopularShows(int page) {
        MutableLiveData<ShowResponse> data = new MutableLiveData<>();
        apiService.getPopularShows(page).enqueue(new Callback<ShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<ShowResponse> call, @NonNull Response<ShowResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ShowResponse> call, @NonNull Throwable t) {
                data.setValue(null );
            }
        });

        return data;
    }

}
