package com.ricardonavarrom.mercury.presentation.presenter;

public interface ArtistsPresenter {
    void onUiReady(int artistsRankingNumber);
    void onSharedPreferenceChanged(int artistsRankingNumber);
    void detachView();
}
