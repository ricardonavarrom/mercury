package com.ricardonavarrom.mercury.presentation.presenter;


public interface ArtistPresenter {
    void setArtistId(int artistId);
    void onUiReady();
    void detachView();
}
