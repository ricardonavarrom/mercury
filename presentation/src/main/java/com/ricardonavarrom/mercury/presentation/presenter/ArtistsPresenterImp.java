package com.ricardonavarrom.mercury.presentation.presenter;

import com.ricardonavarrom.mercury.domain.interactor.LoadArtistsInteractor;
import com.ricardonavarrom.mercury.domain.interactor.LoadArtistsInteractor.LoadArtistsInteractorOutput;
import com.ricardonavarrom.mercury.domain.interactor.RefreshArtistsInteractor;
import com.ricardonavarrom.mercury.domain.interactor.RefreshArtistsInteractor.RefreshArtistsInteractorOutput;
import com.ricardonavarrom.mercury.domain.model.Artist;
import com.ricardonavarrom.mercury.presentation.InteractorExecutor;
import com.ricardonavarrom.mercury.presentation.MercuryViewInjector;
import com.ricardonavarrom.mercury.presentation.view.ArtistsView;

import java.util.List;

import me.panavtec.threaddecoratedview.views.ViewInjector;

public class ArtistsPresenterImp implements ArtistsPresenter, LoadArtistsInteractorOutput,
        RefreshArtistsInteractorOutput {

    private ArtistsView view;
    private LoadArtistsInteractor loadArtistsInteractor;
    private RefreshArtistsInteractor refreshArtistsInteractor;
    private final InteractorExecutor interactorExecutor;
    private final MercuryViewInjector viewInjector;

    int artistsRankingNumber;

    public ArtistsPresenterImp(ArtistsView view, LoadArtistsInteractor loadArtistsInteractor,
                               RefreshArtistsInteractor refreshArtistsInteractor,
                               InteractorExecutor interactorExecutor,
                               MercuryViewInjector viewInjector) {
        this.view = view;
        this.loadArtistsInteractor = loadArtistsInteractor;
        this.refreshArtistsInteractor = refreshArtistsInteractor;
        this.interactorExecutor = interactorExecutor;
        this.viewInjector = viewInjector;
    }

    @Override
    public void onUiReady(int artistsRankingNumber) {
        view.showLoading();
        this.artistsRankingNumber = artistsRankingNumber;
        loadArtistsInteractor.setArtistsRankingNumber(artistsRankingNumber);
        loadArtistsInteractor.setOutput(this);
        interactorExecutor.execute(loadArtistsInteractor);
        view = viewInjector.inject(view);
    }

    @Override
    public void onSharedPreferenceChanged(int artistsRankingNumber) {
        view.showLoading();
        this.artistsRankingNumber = artistsRankingNumber;
        refreshArtistsInteractor.setArtistsRankingNumber(artistsRankingNumber);
        refreshArtistsInteractor.setOutput(this);
        interactorExecutor.execute(refreshArtistsInteractor);
        view = viewInjector.inject(view);
    }

    @Override
    public void detachView() {
        view = ViewInjector.nullObjectPatternView(view);
    }

    @Override
    public void onArtistsLoaded(List<Artist> artists) {
        view.hideLoading();
        if (view != null) {
            view.setArtists(artists);
        }
    }

    @Override
    public void onLoadArtistsError() {
    }

    @Override
    public void onArtistsRefreshed(List<Artist> artists) {
        view.hideLoading();
        if (view != null) {
            view.setArtists(artists);
        }
    }

    @Override
    public void onRefreshArtistsError() {
    }
}
