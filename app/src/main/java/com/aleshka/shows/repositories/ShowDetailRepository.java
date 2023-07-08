package com.aleshka.shows.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aleshka.shows.network.ApiClient;
import com.aleshka.shows.network.ApiService;
import com.aleshka.shows.responces.ShowDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailRepository {

    private ApiService apiService;

    public ShowDetailRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<ShowDetailResponse> getShowDetail(String showId) {
        MutableLiveData<ShowDetailResponse> data = new MutableLiveData<>();

        apiService.getShowDetail(showId).enqueue(new Callback<ShowDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ShowDetailResponse> call, @NonNull Response<ShowDetailResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ShowDetailResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

}
