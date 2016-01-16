package com.ricardonavarrom.mercury.presentation.presenter;

import com.ricardonavarrom.mercury.domain.interactor.LoadArtistsInteractor;
import com.ricardonavarrom.mercury.domain.interactor.LoadArtistsInteractor.LoadArtistsInteractorOutput;
import com.ricardonavarrom.mercury.domain.model.Artist;
import com.ricardonavarrom.mercury.presentation.InteractorExecutor;
import com.ricardonavarrom.mercury.presentation.MercuryViewInjector;
import com.ricardonavarrom.mercury.presentation.view.ArtistsView;
import java.util.List;

public class ArtistsPresenterImp implements ArtistsPresenter, LoadArtistsInteractorOutput {

    private ArtistsView view;
    private LoadArtistsInteractor interactor;
    private final InteractorExecutor interactorExecutor;
    private final MercuryViewInjector viewInjector;

    public ArtistsPresenterImp(ArtistsView view, LoadArtistsInteractor interactor,
                               InteractorExecutor interactorExecutor,
                               MercuryViewInjector viewInjector) {
        this.view = view;
        this.interactor = interactor;
        this.interactorExecutor = interactorExecutor;
        this.viewInjector = viewInjector;
    }

    @Override
    public void onResume() {
        view.showLoading();
        interactor.setOutput(this);
        interactorExecutor.execute(interactor);
        view = viewInjector.inject(view);
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
}
