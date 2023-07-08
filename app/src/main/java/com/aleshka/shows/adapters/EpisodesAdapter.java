package com.aleshka.shows.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aleshka.shows.R;
import com.aleshka.shows.databinding.ItemContainerEpisodeBinding;
import com.aleshka.shows.models.Episode;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder> {

    private List<Episode> episodes;
    private LayoutInflater inflater;

    public EpisodesAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        ItemContainerEpisodeBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_container_episode,
                parent,
                false
        );

        return new EpisodeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        holder.bindEpisode(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    static class EpisodeViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerEpisodeBinding binding;

        public EpisodeViewHolder(@NonNull ItemContainerEpisodeBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bindEpisode(Episode episode) {
            String title = "S";
            String season = episode.getSeason();

            if (season.length() == 1) {
                season = "0".concat(season);
            }

            String episodeNumber = episode.getEpisode();

            if (episodeNumber.length() == 1) {
                episodeNumber = "0".concat(episodeNumber);
            }

            episodeNumber = "E".concat(episodeNumber);
            title = title.concat(season).concat(episodeNumber);

            binding.setTitle(title);
            binding.setName(episode.getName());
            binding.setAirDate(episode.getAirDate());

        }
    }

}
