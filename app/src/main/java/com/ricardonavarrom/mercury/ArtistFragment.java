package com.ricardonavarrom.mercury;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ricardonavarrom.mercury.dependency.PresenterFactory;
import com.ricardonavarrom.mercury.presentation.presenter.ArtistPresenter;
import com.ricardonavarrom.mercury.presentation.view.ArtistView;
import com.squareup.picasso.Picasso;

public class ArtistFragment extends Fragment implements ArtistView {

    private TextView rankView;
    private TextView rankGenreView;
    private TextView rankDateView;
    private TextView nameView;
    private TextView genresView;
    private ImageView imageView;

    private ArtistPresenter presenter;
    private int artistId;
    private String genreRankString;
    private String rankLastUpdateDateString;

    public static ArtistFragment newInstance(int artistId, String genreRankString,
                                             String rankLastUpdateDateString) {
        ArtistFragment artistFragment = new ArtistFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("artistId", artistId);
        arguments.putString("genreRankString", genreRankString);
        arguments.putString("rankLastUpdateDateString", rankLastUpdateDateString);
        artistFragment.setArguments(arguments);

        return artistFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = PresenterFactory.makeArtistPresenter(getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist, container, false);
        getArgments();
        bindView(rootView);

        return rootView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setArtistId(artistId);
        presenter.onUiReady();
    }

    @Override public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override public void showRank(int rank) {
        rankView.setText(Integer.toString(rank));
    }

    @Override public void showRankGenre(String rankGenre) {
        rankView.setText(rankGenre);
    }

    @Override public void showRankDate(String rankDate) {
        rankView.setText(rankDate);
    }

    @Override public void showName(String name) {
        nameView.setText(name);
    }

    @Override public void showGenres(String genres) {
        genresView.setText(getText(R.string.artist_genres) + ": " + genres);
    }

    @Override
    public void showImage(String image) {
        if (image != null) {
            Picasso
                .with(imageView.getContext())
                .load(image)
                .placeholder(R.drawable.image_loading)
                .error(R.mipmap.no_image)
                .into(imageView);
        }
    }

    private void bindView(View rootView) {
        rankView = (TextView) rootView.findViewById(R.id.rank_artist_detail);
        nameView = (TextView) rootView.findViewById(R.id.name_artist_detail);
        imageView = (ImageView) rootView.findViewById(R.id.image_artist_detail);

        rankGenreView = (TextView) rootView.findViewById(R.id.rank_genre_artist_detail);
        rankGenreView.setText(getText(R.string.selected_genre) + ": " + genreRankString);
        rankDateView = (TextView) rootView.findViewById(R.id.rank_date_artist_detail);
        rankDateView.setText(getText(R.string.last_update_date) + ": " + rankLastUpdateDateString);
        genresView = (TextView) rootView.findViewById(R.id.genres_artist_detail);
    }

    private void getArgments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            artistId = arguments.getInt("artistId");
            genreRankString = arguments.getString("genreRankString");
            rankLastUpdateDateString = arguments.getString("rankLastUpdateDateString");
        }
    }
}
