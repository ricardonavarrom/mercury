package com.ricardonavarrom.mercury.dependency;

import android.content.Context;
import android.os.Handler;

import com.ricardonavarrom.mercury.presentation.InteractorExecutor;
import com.ricardonavarrom.mercury.presentation.InteractorExecutorImp;
import com.ricardonavarrom.mercury.presentation.MercuryViewInjector;
import com.ricardonavarrom.mercury.presentation.ViewInjectorImp;
import com.ricardonavarrom.mercury.presentation.presenter.ArtistPresenter;
import com.ricardonavarrom.mercury.presentation.presenter.ArtistPresenterImp;
import com.ricardonavarrom.mercury.presentation.presenter.ArtistsPresenter;
import com.ricardonavarrom.mercury.presentation.presenter.ArtistsPresenterImp;
import com.ricardonavarrom.mercury.presentation.view.ArtistView;
import com.ricardonavarrom.mercury.presentation.view.ArtistsView;

import java.util.concurrent.Executors;

import me.panavtec.threaddecoratedview.views.ThreadSpec;

public class PresenterFactory {

    public static final int THREADS = 3;

    public static ArtistsPresenter makeArtistsPresenter(Context context, ArtistsView view) {
        return new ArtistsPresenterImp(view, InteractorFactory.makeLoadArtistsInteractor(context),
                InteractorFactory.makeRefreshArtistsInteractor(context),
                makeInteractorExecutor(), makeViewInjector());
    }

    public static ArtistPresenter makeArtistPresenter(Context context, ArtistView view) {
        return new ArtistPresenterImp(view, makeThreadSpec(), InteractorFactory.makeLoadArtistInteractor(context),
                makeInteractorExecutor());
    }

    private static InteractorExecutor makeInteractorExecutor() {
        return new InteractorExecutorImp(Executors.newFixedThreadPool(THREADS));
    }

    private static MercuryViewInjector makeViewInjector() {
        return new ViewInjectorImp(makeThreadSpec());
    }

    private static ThreadSpec makeThreadSpec() {
        return new MainThreadSpec();
    }

    private static class MainThreadSpec implements ThreadSpec {
        final Handler handler = new Handler();

        @Override public void execute(Runnable action) {
            handler.post(action);
        }
    }
}
