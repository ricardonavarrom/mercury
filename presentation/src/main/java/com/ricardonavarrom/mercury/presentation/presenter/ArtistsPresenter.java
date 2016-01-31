package com.ricardonavarrom.mercury.presentation.presenter;

public interface ArtistsPresenter {
    void onUiReady(int artistsRankingNumber, String artistsRankingGenre);
    void onSharedPreferenceChanged(int artistsRankingNumber, String artistsRankingGenre);
    void detachView();
}
