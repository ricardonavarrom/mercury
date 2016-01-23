package com.ricardonavarrom.mercury;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ricardonavarrom.mercury.adapter.ArtistListAdapter;
import com.ricardonavarrom.mercury.dependency.PresenterFactory;
import com.ricardonavarrom.mercury.domain.model.Artist;
import com.ricardonavarrom.mercury.presentation.presenter.ArtistsPresenter;
import com.ricardonavarrom.mercury.presentation.view.ArtistsView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ArtistsFragment extends Fragment implements ArtistsView {

    private RecyclerView recyclerView;
    private ArtistListAdapter adapter;
    private ArtistsPresenter presenter;
    private ProgressBar progressBar;

    public interface Callback {
        public void onItemSelected(Artist artist);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = PresenterFactory.makeArtistsPresenter(getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        adapter = new ArtistListAdapter(new ArrayList<Artist>());
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int artistPosition = recyclerView.getChildAdapterPosition(view);
                Artist selectedArtist = adapter.getArtists().get(artistPosition);
                ((Callback) getActivity()).onItemSelected(selectedArtist);
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.artists_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        progressBar = (ProgressBar) rootView.findViewById(R.id.artists_progress_bar);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void setArtists(List<Artist> artists) {
        adapter.updateData(artists);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(GONE);
    }

    @Override
    public void showLoadArtistsError() {
        showError(R.string.error_load_artists);
    }

    private void showError(@StringRes int error) {
        Toast.makeText(getActivity(), getString(error), Toast.LENGTH_SHORT).show();
    }
}
