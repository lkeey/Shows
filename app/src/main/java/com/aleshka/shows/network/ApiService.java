package com.aleshka.shows.network;

import com.aleshka.shows.responces.ShowDetailResponse;
import com.aleshka.shows.responces.ShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<ShowResponse> getPopularShows(
            @Query("page") int page
    );

    @GET("show-details")
    Call<ShowDetailResponse> getShowDetail(
            @Query("q ") String showId
    );

}
