package com.ricardonavarrom.mercury.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ricardonavarrom.mercury.R;
import com.ricardonavarrom.mercury.domain.model.Artist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistsViewHolder>
        implements View.OnClickListener {

    private List<Artist> artists;
    private View.OnClickListener listener;

    public static class ArtistsViewHolder extends RecyclerView.ViewHolder {
        public final TextView rankView;
        public final ImageView imageView;
        public final TextView nameView;

        public ArtistsViewHolder(View view) {
            super(view);
            rankView = (TextView) view.findViewById(R.id.item_artists_rank);
            imageView = (ImageView) view.findViewById(R.id.item_artists_image);
            nameView = (TextView) view.findViewById(R.id.item_artists_name);
        }

        public void bind(Artist artist) {
            rankView.setText(String.valueOf(artist.getRank()));
            nameView.setText(artist.getName());
            if (artist.getMediumImage() == null) {
                imageView.setImageResource(R.mipmap.no_image);
                imageView.getLayoutParams().width = 200;
                imageView.getLayoutParams().height = 200;

            } else {
                Picasso
                    .with(imageView.getContext())
                    .load(artist.getMediumImage())
                    .placeholder(R.drawable.image_loading)
                    .error(R.mipmap.no_image)
                    .resizeDimen(R.dimen.item_artists_image_width, R.dimen.item_artists_image_height)
                    .centerCrop()
                    .into(imageView);
            }
        }
    }

    public ArtistListAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public ArtistsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_artists, viewGroup, false);

        item.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void updateData(List<Artist> artists) {
        this.artists = artists;
        notifyDataSetChanged();
    }

    public List<Artist> getArtists() {
        return this.artists;
    }
}
