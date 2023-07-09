package com.aleshka.shows.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aleshka.shows.R;
import com.aleshka.shows.databinding.ItemContainerTvShowBinding;
import com.aleshka.shows.listeners.WatchListListener;
import com.aleshka.shows.models.Show;

import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.ShowViewHolder> {

    private static final String TAG = "AdapterWatchList";
    private final List<Show> shows;
    private final WatchListListener listener;

    private LayoutInflater inflater;

    public WatchListAdapter(List<Show> shows, WatchListListener listener) {
        this.shows = shows;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        ItemContainerTvShowBinding showBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_container_tv_show,
                parent,
                false
        );

        return new ShowViewHolder(showBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        holder.bindShow(shows.get(position));
    }


    @Override
    public int getItemCount() {
        return shows.size();
    }

    class ShowViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerTvShowBinding binding;

        public ShowViewHolder(ItemContainerTvShowBinding itemContainer) {
            super(itemContainer.getRoot());

            binding = itemContainer;

        }

        public void bindShow(Show show) {

            Log.i(TAG, "show - " + show.getName());

            binding.setShow(show);
            binding.executePendingBindings();

            binding.getRoot().setOnClickListener(view -> listener.onShowClicked(show));

            binding.imgRemove.setOnClickListener(view -> listener.removeShowFromWatchList(show, getAdapterPosition()));
            binding.imgRemove.setVisibility(View.VISIBLE);
        }

    }
}
