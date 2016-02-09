package com.ricardonavarrom.mercury.presentation.presenter;

import com.ricardonavarrom.mercury.domain.interactor.LoadArtistsInteractor;
import com.ricardonavarrom.mercury.domain.interactor.LoadArtistsInteractor.LoadArtistsInteractorOutput;
import com.ricardonavarrom.mercury.domain.interactor.RefreshArtistsInteractor;
import com.ricardonavarrom.mercury.domain.interactor.RefreshArtistsInteractor.RefreshArtistsInteractorOutput;
import com.ricardonavarrom.mercury.domain.model.Artist;
import com.ricardonavarrom.mercury.presentation.InteractorExecutor;
import com.ricardonavarrom.mercury.presentation.MercuryViewInjector;
import com.ricardonavarrom.mercury.presentation.view.ArtistsView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        view.hideRecyclerView();
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
        view.hideRecyclerView();
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
        refreshArtistsRankingExpirationDate();
        view.resetPreferredArtistsRankingOptionsChanged();
        view.hideLoading();
        view.showRecyclerView();
        view.hideRefreshRankingView();
        if (view != null) {
            view.setArtists(artists);
        }
    }

    @Override
    public void onArtistsRefreshed(List<Artist> artists) {
        refreshArtistsRankingExpirationDate();
        view.resetPreferredArtistsRankingOptionsChanged();
        view.hideLoading();
        view.hideRefreshRankingView();
        view.showRecyclerView();
        if (view != null) {
            view.setArtists(artists);
        }
    }

    @Override
    public void onLoadArtistsError() {
        view.hideLoading();
        view.showRecyclerView();
        view.showLoadArtistsError();
    }

    @Override
    public void onRefreshArtistsError() {
        view.hideLoading();
        view.showRecyclerView();
        view.showLoadArtistsError();
    }

    @Override
    public void onNetworkError() {
        view.hideLoading();
        view.showRecyclerView();
        List<Artist> emptyArtistsList = new ArrayList<>();
        view.setArtists(emptyArtistsList);
        view.showRefreshRankingView();
        view.showNetworkError();
    }

    private void refreshArtistsRankingExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrowDate = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String tomorrowDateString = dateFormat.format(tomorrowDate);
        view.saveArtistsRankingExpirationDate(tomorrowDateString);
    }
}
