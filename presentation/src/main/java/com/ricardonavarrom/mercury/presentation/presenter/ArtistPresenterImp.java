package com.ricardonavarrom.mercury.presentation.presenter;

import com.ricardonavarrom.mercury.presentation.view.ArtistView;

public class ArtistPresenterImp implements ArtistPresenter {

    private ArtistView view;

    public ArtistPresenterImp(ArtistView view) {
        this.view = view;
    }

    @Override
    public void onResume(String artist) {
        updateView(artist);
    }

    private void updateView(String artist) {
        view.showName(artist);
    }
}
