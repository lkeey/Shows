package com.aleshka.shows.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aleshka.shows.R;
import com.aleshka.shows.models.Show;

import java.util.List;

public class ShowsAdapterOld extends RecyclerView.Adapter<ShowsAdapterOld.ViewHolder> {

    private static final String TAG = "ShowsAdapterOld";
    private final List<Show> shows;

    public ShowsAdapterOld(List<Show> shows) {
        this.shows = shows;
    }

    @NonNull
    @Override
    public ShowsAdapterOld.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_show, parent, false);

        return new ShowsAdapterOld.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowsAdapterOld.ViewHolder holder, int position) {
        holder.setData(shows.get(position));
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.i(TAG, "item - " + itemView);

            name = itemView.findViewById(R.id.textName);
        }

        public void setData(Show show){
            name.setText(show.getName());
        }
    }
}
