package com.ricardonavarrom.mercury.dependency;

import android.content.Context;

import com.ricardonavarrom.mercury.domain.interactor.LoadArtistsInteractor;

public class InteractorFactory {

    public static LoadArtistsInteractor makeLoadArtistsInteractor(Context context) {
        return new LoadArtistsInteractor(GatewaysFactory.makeLocalArtistsGateway(context),
                GatewaysFactory.makeNetworkArtistsGateway());
    }
}
