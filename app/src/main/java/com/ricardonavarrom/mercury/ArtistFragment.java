package com.ricardonavarrom.mercury;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ricardonavarrom.mercury.dependency.PresenterFactory;
import com.ricardonavarrom.mercury.presentation.presenter.ArtistPresenter;
import com.ricardonavarrom.mercury.presentation.view.ArtistView;

public class ArtistFragment extends Fragment implements ArtistView {

    private TextView mNameView;

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
        getArgments();
        View rootView = inflater.inflate(R.layout.fragment_artist, container, false);
        bindView(rootView);

        Log.v("****ENTRO AL FRAGMENT CON EL ID", Integer.toString(artistId));

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

    @Override public void showName(String name) {
        mNameView.setText(name);
    }

    private void bindView(View rootView) {
        mNameView = (TextView)rootView.findViewById(R.id.name_artist_detail);
    }

    private void getArgments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            artistId = arguments.getInt("artistId");
        }
    }
}
