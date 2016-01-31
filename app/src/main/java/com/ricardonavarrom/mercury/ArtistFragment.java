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

    private TextView mRankView;
    private TextView mNameView;
    private TextView mUrlView;
    private TextView mUriView;
    private TextView mGenresView;
    private ImageView mImageView;

    private ArtistPresenter presenter;
    private int artistId;

    public static ArtistFragment newInstance(int artistId) {
        ArtistFragment artistFragment = new ArtistFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("artistId", artistId);
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
        mRankView.setText(Integer.toString(rank));
    }

    @Override public void showName(String name) {
        mNameView.setText(name);
    }

    @Override public void showUrl(String url) {
        mUrlView.setText(url);
    }

    @Override public void showUri(String uri) {
        mUriView.setText(uri);
    }

    @Override public void showGenres(String genres) {
        mGenresView.setText(genres);
    }

    @Override
    public void showImage(String image) {
        if (image != null) {
            Picasso
                .with(mImageView.getContext())
                .load(image)
                .placeholder(R.drawable.image_loading)
                .error(R.mipmap.no_image)
                .into(mImageView);
        }
    }

    private void bindView(View rootView) {
        mRankView = (TextView) rootView.findViewById(R.id.rank_artist_detail);
        mNameView = (TextView) rootView.findViewById(R.id.name_artist_detail);
        mUrlView = (TextView) rootView.findViewById(R.id.url_artist_detail);
        mUriView = (TextView) rootView.findViewById(R.id.uri_artist_detail);
        mGenresView = (TextView) rootView.findViewById(R.id.genres_artist_detail);
        mImageView = (ImageView) rootView.findViewById(R.id.image_artist_detail);
    }

    private void getArgments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            artistId = arguments.getInt("artistId");
        }
    }
}
