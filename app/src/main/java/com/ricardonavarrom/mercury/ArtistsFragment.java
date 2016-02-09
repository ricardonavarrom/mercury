package com.ricardonavarrom.mercury;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ricardonavarrom.mercury.adapter.ArtistListAdapter;
import com.ricardonavarrom.mercury.decoration.DividerItemDecoration;
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
    private LinearLayout refreshRankingView;

    public interface Callback {
        void onItemSelected(Artist artist);
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
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        progressBar = (ProgressBar) rootView.findViewById(R.id.artists_progress_bar);
        refreshRankingView = (LinearLayout) rootView.findViewById(R.id.refresh_ranking_view);

        return rootView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int artistsRankingNumber = ((MainActivity) getActivity()).getPreferredArtistsRankingNumber();
        String artistsRankingGenre = ((MainActivity) getActivity()).getPreferredArtistsRankingGenre();
        onUiReady(artistsRankingNumber, artistsRankingGenre);
    }

    @Override public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    public void onUiReady(int actualArtistsRankingNumber, String actualArtistsRankingGenre) {
        boolean isOnline = ((MainActivity) getActivity()).isOnline();
        presenter.onUiReady(actualArtistsRankingNumber, actualArtistsRankingGenre, isOnline);
    }

    public void onRefreshNecessary(int actualArtistsRankingNumber,
                                   String actualArtistsRankingGenre, boolean isOnline) {
        presenter.onRefreshNecessary(actualArtistsRankingNumber, actualArtistsRankingGenre,
                isOnline);
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
    public void showRecyclerView() {
        recyclerView.setVisibility(VISIBLE);
    }

    @Override
    public void hideRecyclerView() {
        recyclerView.setVisibility(GONE);
    }

    @Override
    public void showRefreshRankingView() {
        refreshRankingView.setVisibility(VISIBLE);
    }

    @Override
    public void hideRefreshRankingView() {
        refreshRankingView.setVisibility(GONE);
    }

    @Override
    public void showLoadArtistsError() {
        showError(R.string.error_load_artists);
    }

    @Override
    public void showNetworkError() {
        showError(R.string.error_network);
    }

    @Override
    public void saveArtistsRankingExpirationDate(String artistRankingExpirationDateString) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.pref_artists_rank_expiration_date_key),
                artistRankingExpirationDateString);
        editor.commit();
    }

    @Override
    public void resetPreferredArtistsRankingOptionsChanged() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.pref_artists_rank_settings_changed_key), 0);
        editor.commit();
    }

    private void showError(@StringRes int error) {
        Toast.makeText(getActivity(), getString(error), Toast.LENGTH_LONG).show();
    }
}
