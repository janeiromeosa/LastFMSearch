package com.example.lastfmMVVMJava.ui.home;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lastfmMVVMJava.data.albumResults.Album;
import com.example.lastfmMVVMJava.data.albumResults.Image;
import com.example.lastfmMVVMJava.databinding.ItemAlbumBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder> {

    private final List<Album> albums = new ArrayList<>();


    public void setData(List<Album> data) {
        albums.clear();
        albums.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new AlbumViewHolder(ItemAlbumBinding
                .inflate(layoutInflater, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder albumViewHolder, int position) {
        albumViewHolder.bind(albums.get(position));
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        private final ItemAlbumBinding itemAlbumBinding;

        AlbumViewHolder(ItemAlbumBinding itemAlbumBinding) {
            super(itemAlbumBinding.getRoot());
            this.itemAlbumBinding = itemAlbumBinding;
        }

        void bind(Album album) {
            itemAlbumBinding.setAlbum(album);
            itemAlbumBinding.executePendingBindings();
        }

        @BindingAdapter("image_url")
        public static void loadAlbumArt(ImageView imageView, List<Image> images) {
            Image largestImage = null;
            if (!images.isEmpty()) {
                largestImage = images.get(images.size() - 1);
            }
            if(largestImage != null && largestImage.getText() != null) {
                Picasso.get()
                        .load(largestImage.getText())
                        .into(imageView);
            }
        }
    }
}
