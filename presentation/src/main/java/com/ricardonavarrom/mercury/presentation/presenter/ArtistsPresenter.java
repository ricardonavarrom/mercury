package com.ricardonavarrom.mercury.presentation.presenter;

public interface ArtistsPresenter {
    void onUiReady(int artistsRankingNumber, String artistsRankingGenre, boolean isOnline);
    void onRefreshNecessary(int artistsRankingNumber, String artistsRankingGenre, boolean isOnline);
    void detachView();
}
