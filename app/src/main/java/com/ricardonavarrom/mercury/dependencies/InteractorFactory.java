package com.ricardonavarrom.mercury.dependencies;

import com.ricardonavarrom.mercury.domain.interactor.LoadArtistsInteractor;

public class InteractorFactory {

    public static LoadArtistsInteractor makeLoadArtistsInteractor() {
        return new LoadArtistsInteractor(GatewaysFactory.makeNetworkArtistsGateway());
    }
}
