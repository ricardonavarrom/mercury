package com.ricardonavarrom.mercury.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ricardonavarrom.mercury.R;
import com.ricardonavarrom.mercury.domain.model.Artist;
import com.ricardonavarrom.mercury.transformation.CropTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistsViewHolder>
        implements View.OnClickListener {

    private List<Artist> artists;
    private View.OnClickListener listener;

    private static final int VIEW_TYPE_TOP_ARTIST  = 0;
    private static final int VIEW_TYPE_NORMAL_ARTIST = 1;

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
            int imageWidth;
            int imageHeight;
            String imageUrl;

            nameView.setText(artist.getName());

            if (artist.getRank() == 1) {
                imageWidth = Integer.parseInt(imageView.getContext().getResources()
                        .getString(R.string.item_artists_top_image_width));
                imageHeight = Integer.parseInt(imageView.getContext().getResources()
                        .getString(R.string.item_artists_top_image_height));
                imageUrl = artist.getBigImage();
            } else {
                rankView.setText(String.valueOf(artist.getRank()));
                imageWidth = Integer.parseInt(imageView.getContext().getResources()
                        .getString(R.string.item_artists_image_width));
                imageHeight = Integer.parseInt(imageView.getContext().getResources()
                        .getString(R.string.item_artists_image_height));
                imageUrl = artist.getMediumImage();
            }

            if (artist.getMediumImage() == null) {
                imageView.setImageResource(R.mipmap.no_image);
                imageView.getLayoutParams().width = imageWidth;
                imageView.getLayoutParams().height = imageHeight;
            } else {
                Picasso
                    .with(imageView.getContext())
                    .load(imageUrl)
                    .placeholder(R.mipmap.loading)
                    .error(R.mipmap.no_image)
                    .transform(new CropTransformation(
                            imageWidth,
                            imageHeight,
                            CropTransformation.CropType.TOP
                    ))
                    .into(imageView);
            }
        }
    }

    public ArtistListAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0
                ? VIEW_TYPE_TOP_ARTIST
                : VIEW_TYPE_NORMAL_ARTIST;
    }

    @Override
    public ArtistsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layoutResource = viewType == VIEW_TYPE_TOP_ARTIST
                ? R.layout.top_item_artist
                : R.layout.item_artists;

        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutResource, viewGroup, false);

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
