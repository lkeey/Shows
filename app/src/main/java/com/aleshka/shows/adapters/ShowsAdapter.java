package com.aleshka.shows.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aleshka.shows.R;
import com.aleshka.shows.databinding.ItemContainerTvShowBinding;
import com.aleshka.shows.models.Show;

import java.util.List;

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ShowViewHolder> {

    private static final String TAG = "AdapterShows";
    private final List<Show> shows;
    private LayoutInflater inflater;

    public ShowsAdapter(List<Show> shows) {
        Log.i(TAG, "shows - " + shows.size());
        this.shows = shows;
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
        try {
            holder.bindShow(shows.get(position));
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return shows.size();
    }

     static class ShowViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerTvShowBinding binding;

        public ShowViewHolder(ItemContainerTvShowBinding itemContainer) {
            super(itemContainer.getRoot());

            binding = itemContainer;

        }

        public void bindShow(Show show) {
            binding.setShow(show);
            binding.executePendingBindings();
        }

    }
}
