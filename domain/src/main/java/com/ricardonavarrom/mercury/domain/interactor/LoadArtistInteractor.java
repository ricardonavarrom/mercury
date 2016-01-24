package com.ricardonavarrom.mercury.domain.interactor;

import com.ricardonavarrom.mercury.domain.LocalArtistsGateway;
import com.ricardonavarrom.mercury.domain.model.Artist;

public class LoadArtistInteractor implements Interactor {

    private LoadArtistInteractorOutput output;
    private LocalArtistsGateway localArtistsGateway;
    private int artistId;

    public LoadArtistInteractor(LocalArtistsGateway localArtistsGateway) {
        this.localArtistsGateway = localArtistsGateway;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public void setOutput(LoadArtistInteractorOutput output) {
        this.output = output;
    }

    @Override
    public void run() {
        loadArtist();
    }

    private void loadArtist() {
        Artist artist = localArtistsGateway.getArtist(artistId);
        output.onArtistLoaded(artist);
    }

    public interface LoadArtistInteractorOutput {
        void onArtistLoaded(Artist artist);
    }
}
