package com.ricardonavarrom.mercury.domain.interactor;

import com.ricardonavarrom.mercury.domain.LocalArtistsGateway;
import com.ricardonavarrom.mercury.domain.model.Artist;
import com.ricardonavarrom.mercury.domain.NetworkArtistsGateway;

import java.util.List;

public class LoadArtistsInteractor implements Interactor {

    private LoadArtistsInteractorOutput output;
    private LocalArtistsGateway localArtistsGateway;
    private NetworkArtistsGateway networkArtistsGateway;

    public LoadArtistsInteractor(LocalArtistsGateway localArtistsGateway,
                                 NetworkArtistsGateway networkArtistsGateway) {
        this.localArtistsGateway = localArtistsGateway;
        this.networkArtistsGateway = networkArtistsGateway;
    }

    public void setOutput(LoadArtistsInteractorOutput output) {
        this.output = output;
    }

    @Override
    public void run() {
        try {
            loadArtists();
        } catch (Exception e) {
            e.printStackTrace();
            output.onLoadArtistsError();
        }
    }

    private void loadArtists() {
        List<Artist> artistsList = localArtistsGateway.getArtists();
        if (artistsList.isEmpty()) {
            artistsList = networkArtistsGateway.getArtists();
            output.onArtistsLoaded(artistsList);
            localArtistsGateway.persistsArtists(artistsList);
        } else {
            output.onArtistsLoaded(artistsList);
        }
    }

    public interface LoadArtistsInteractorOutput {
        void onArtistsLoaded(List<Artist> artists);

        void onLoadArtistsError();
    }
}
