package com.aleshka.shows.network;

import com.aleshka.shows.models.Show;
import com.aleshka.shows.responces.ShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<ShowResponse> getPopularShows(
            @Query("page") int page
    );

}
