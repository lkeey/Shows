package com.aleshka.shows.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aleshka.shows.R;
import com.aleshka.shows.databinding.ItemContainerImageBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {

    private String[] images;
    private LayoutInflater inflater;

    public ImageSliderAdapter(String[] images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        ItemContainerImageBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_container_image,
                parent,
                false
        );

        return new ImageSliderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindSlideImage(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class ImageSliderViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerImageBinding itemContainerImageBinding;

        public ImageSliderViewHolder(@NonNull ItemContainerImageBinding binding) {
            super(binding.getRoot());

            this.itemContainerImageBinding = binding;
        }

        public void bindSlideImage(String imgURL) {
            itemContainerImageBinding.setImgURL(imgURL);
        }
    }

}
