package com.ricardonavarrom.mercury.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ricardonavarrom.mercury.R;
import com.ricardonavarrom.mercury.domain.model.Artist;

import java.util.List;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistsViewHolder> {

    private List<Artist> artists;

    public static class ArtistsViewHolder extends RecyclerView.ViewHolder {
        public final TextView rankView;
        public final TextView imageView;
        public final TextView nameView;

        public ArtistsViewHolder(View view) {
            super(view);
            rankView = (TextView) view.findViewById(R.id.item_artists_rank);
            imageView = (TextView) view.findViewById(R.id.item_artists_image);
            nameView = (TextView) view.findViewById(R.id.item_artists_name);
        }

        public void bind(Artist artist) {
            rankView.setText(String.valueOf(artist.getRank()));
            imageView.setText(artist.getSmallImage());
            nameView.setText(artist.getName());
        }
    }

    public ArtistListAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public ArtistsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_artists, viewGroup, false);

        ArtistsViewHolder viewHolder = new ArtistsViewHolder(item);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArtistsViewHolder viewHolder, int pos) {
        Artist artist = artists.get(pos);

        viewHolder.bind(artist);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public void updateData(List<Artist> artists) {
        this.artists = artists;
        notifyDataSetChanged();
    }
}
