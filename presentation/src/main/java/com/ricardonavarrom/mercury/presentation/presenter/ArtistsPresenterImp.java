package com.ricardonavarrom.mercury.presentation.presenter;

import com.ricardonavarrom.mercury.domain.interactor.LoadArtistsInteractor;
import com.ricardonavarrom.mercury.domain.interactor.LoadArtistsInteractor.LoadArtistsInteractorOutput;
import com.ricardonavarrom.mercury.domain.interactor.RefreshArtistsInteractor;
import com.ricardonavarrom.mercury.domain.interactor.RefreshArtistsInteractor.RefreshArtistsInteractorOutput;
import com.ricardonavarrom.mercury.domain.model.Artist;
import com.ricardonavarrom.mercury.presentation.InteractorExecutor;
import com.ricardonavarrom.mercury.presentation.MercuryViewInjector;
import com.ricardonavarrom.mercury.presentation.view.ArtistsView;

import java.util.ArrayList;
import java.util.List;

import me.panavtec.threaddecoratedview.views.ViewInjector;

public class ArtistsPresenterImp implements ArtistsPresenter, LoadArtistsInteractorOutput,
        RefreshArtistsInteractorOutput {

    private ArtistsView view;
    private LoadArtistsInteractor loadArtistsInteractor;
    private RefreshArtistsInteractor refreshArtistsInteractor;
    private final InteractorExecutor interactorExecutor;
    private final MercuryViewInjector viewInjector;

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
    public void onUiReady(int artistsRankingNumber, String artistsRankingGenre, boolean isOnline) {
        view.showLoading();
        loadArtistsInteractor.setArtistsRankingNumber(artistsRankingNumber);
        loadArtistsInteractor.setArtistsRankingGenre(artistsRankingGenre);
        loadArtistsInteractor.setIsOnline(isOnline);
        loadArtistsInteractor.setOutput(this);
        interactorExecutor.execute(loadArtistsInteractor);
        view = viewInjector.inject(view);
    }

    @Override
    public void onRefreshNecessary(int artistsRankingNumber, String artistsRankingGenre,
                                   boolean isOnline) {
        view.showLoading();
        refreshArtistsInteractor.setArtistsRankingNumber(artistsRankingNumber);
        refreshArtistsInteractor.setArtistsRankingGenre(artistsRankingGenre);
        refreshArtistsInteractor.setIsOnline(isOnline);
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
        view.hideRefreshButton();
        if (view != null) {
            view.setArtists(artists);
        }
    }

    @Override
    public void onArtistsRefreshed(List<Artist> artists) {
        view.hideLoading();
        view.hideRefreshButton();
        if (view != null) {
            view.setArtists(artists);
        }
    }

    @Override
    public void onLoadArtistsError() {
        view.hideLoading();
        view.showLoadArtistsError();
    }

    @Override
    public void onRefreshArtistsError() {
        view.hideLoading();
        view.showLoadArtistsError();
    }

    @Override
    public void onNetworkError() {
        view.hideLoading();
        List<Artist> emptyArtistsList = new ArrayList<>();
        view.setArtists(emptyArtistsList);
        view.showRefreshButton();
        view.showNetworkError();
    }
}
