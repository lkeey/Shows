package com.aleshka.shows.responces;

import com.aleshka.shows.models.ShowDetail;
import com.google.gson.annotations.SerializedName;

public class ShowDetailResponse {

    @SerializedName("tvShow")
    private ShowDetail showDetail;

    public ShowDetail getShowDetail() {
        return showDetail;
    }

    public void setShowDetail(ShowDetail showDetail) {
        this.showDetail = showDetail;
    }
}
