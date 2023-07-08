package com.aleshka.shows.repositories;

import android.util.Log;

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

    private static final String TAG = "RepositoryShowDetail";
    private ApiService apiService;

    public ShowDetailRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<ShowDetailResponse> getShowDetail(String showId) {
        MutableLiveData<ShowDetailResponse> data = new MutableLiveData<>(); 

        apiService.getShowDetail(showId).enqueue(new Callback<ShowDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ShowDetailResponse> call, @NonNull Response<ShowDetailResponse> response) {
                Log.i(TAG, "value - " + response.body());

                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ShowDetailResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "error - " + t.getMessage());

                data.setValue(null);
            }
        });

        return data;
    }

}
