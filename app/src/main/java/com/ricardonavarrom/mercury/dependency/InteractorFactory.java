package com.ricardonavarrom.mercury.dependency;

import android.content.Context;

import com.ricardonavarrom.mercury.domain.interactor.LoadArtistInteractor;
import com.ricardonavarrom.mercury.domain.interactor.LoadArtistsInteractor;
import com.ricardonavarrom.mercury.domain.interactor.RefreshArtistsInteractor;

public class InteractorFactory {

    public static LoadArtistsInteractor makeLoadArtistsInteractor(Context context) {
        return new LoadArtistsInteractor(GatewaysFactory.makeLocalArtistsGateway(context),
                GatewaysFactory.makeNetworkArtistsGateway());
    }

    public static RefreshArtistsInteractor makeRefreshArtistsInteractor(Context context) {
        return new RefreshArtistsInteractor(GatewaysFactory.makeLocalArtistsGateway(context),
                GatewaysFactory.makeNetworkArtistsGateway());
    }

    public static LoadArtistInteractor makeLoadArtistInteractor(Context context) {
        return new LoadArtistInteractor(GatewaysFactory.makeLocalArtistsGateway(context));
    }
}
