package com.ricardonavarrom.mercury;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ricardonavarrom.mercury.dependencies.PresenterFactory;
import com.ricardonavarrom.mercury.domain.model.Artist;
import com.ricardonavarrom.mercury.presentation.presenter.ArtistsPresenter;
import com.ricardonavarrom.mercury.presentation.view.ArtistsView;

import java.util.List;

public class ArtistsFragment extends Fragment implements ArtistsView {

    private ListView listView;
    private ArtistsPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = PresenterFactory.makeArtistsPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.artists_listview);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void setArtists(List<Artist> artists) {
        listView.setAdapter(new ArrayAdapter<>(
                        getActivity(),
                        R.layout.item_artists,
                        R.id.item_artists_textview,
                        artists)
        );
    }

    @Override
    public void showLoadArtistsError() {
        showError(R.string.error_load_artists);
    }

    private void showError(@StringRes int error) {
        Toast.makeText(getActivity(), getString(error), Toast.LENGTH_SHORT).show();
    }
}
