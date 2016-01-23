package com.ricardonavarrom.mercury;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private String artistName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = PresenterFactory.makeArtistPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            artistName = arguments.getString("artistName");
        }

        bindView(rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(artistName);
    }

    @Override public void showName(String name) {
        mNameView.setText(name);
    }

    private void bindView(View rootView) {
        mNameView = (TextView)rootView.findViewById(R.id.name_artist_detail);
    }
}
