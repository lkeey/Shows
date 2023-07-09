package com.aleshka.shows.listeners;

import com.aleshka.shows.models.Show;

public interface WatchListListener {
    void onShowClicked(Show show);

    void removeShowFromWatchList(Show show, int position );
}
