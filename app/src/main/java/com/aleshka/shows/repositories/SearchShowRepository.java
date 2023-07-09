package com.aleshka.shows.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aleshka.shows.network.ApiClient;
import com.aleshka.shows.network.ApiService;
import com.aleshka.shows.responces.ShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchShowRepository {

    private static final String TAG = "RepositorySearchShow";
    private final ApiService apiService;

    public SearchShowRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<ShowResponse> searchShow(String query, int page) {
        MutableLiveData<ShowResponse> data = new MutableLiveData<>();

        apiService.searchShow(query, page ).enqueue(new Callback<ShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<ShowResponse> call, @NonNull Response<ShowResponse> response) {

                data.setValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<ShowResponse> call, @NonNull Throwable t) {

                data.setValue(null);

                Log.i(TAG, "error - " + t.getMessage());
            }
        });

        return data;
    }
}
