package com.ricardonavarrom.mercury.presentation.presenter;

import com.ricardonavarrom.mercury.domain.interactor.LoadArtistInteractor;
import com.ricardonavarrom.mercury.domain.interactor.LoadArtistInteractor.LoadArtistInteractorOutput;
import com.ricardonavarrom.mercury.domain.model.Artist;
import com.ricardonavarrom.mercury.presentation.InteractorExecutor;
import com.ricardonavarrom.mercury.presentation.view.ArtistView;

import me.panavtec.threaddecoratedview.views.ThreadSpec;
import me.panavtec.threaddecoratedview.views.ViewInjector;

public class ArtistPresenterImp implements ArtistPresenter, LoadArtistInteractorOutput {

    private final ThreadSpec threadSpec;
    private ArtistView view;
    private LoadArtistInteractor interactor;
    private final InteractorExecutor interactorExecutor;

    private int artistId;

    public ArtistPresenterImp(ArtistView view, ThreadSpec threadSpec,
                              LoadArtistInteractor interactor,
                              InteractorExecutor interactorExecutor) {
        this.view = view;
        this.threadSpec = threadSpec;
        this.interactor = interactor;
        this.interactorExecutor = interactorExecutor;
    }

    @Override
    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    @Override
    public void onUiReady() {
        view = ViewInjector.inject(view, threadSpec);
        loadArtist();
    }

    @Override
    public void detachView() {
        view = ViewInjector.nullObjectPatternView(view);
    }

    @Override
    public void onArtistLoaded(Artist artist) {
        updateView(artist);
    }

    private void loadArtist() {
        interactor.setArtistId(artistId);
        interactor.setOutput(this);
        interactorExecutor.execute(interactor);
    }

    private void updateView(Artist artist) {
        view.showRank(artist.getRank());
        view.showName(artist.getName());
        view.showExternalUrl(artist.getExternalUrl());
        view.showGenres(artist.getGenres());
        view.showImage(artist.getMediumImage());
    }
}
