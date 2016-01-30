package com.ricardonavarrom.mercury.domain.interactor;

import com.ricardonavarrom.mercury.domain.LocalArtistsGateway;
import com.ricardonavarrom.mercury.domain.NetworkArtistsGateway;
import com.ricardonavarrom.mercury.domain.model.Artist;

import java.util.List;

public class RefreshArtistsInteractor implements Interactor {

    private RefreshArtistsInteractorOutput output;
    private LocalArtistsGateway localArtistsGateway;
    private NetworkArtistsGateway networkArtistsGateway;

    private int artistsRankingNumber;

    public RefreshArtistsInteractor(LocalArtistsGateway localArtistsGateway,
                                    NetworkArtistsGateway networkArtistsGateway) {
        this.localArtistsGateway = localArtistsGateway;
        this.networkArtistsGateway = networkArtistsGateway;
    }

    public void setOutput(RefreshArtistsInteractorOutput output) {
        this.output = output;
    }

    @Override
    public void run() {
        try {
            refreshArtists();
        } catch (Exception e) {
            e.printStackTrace();
            output.onRefreshArtistsError();
        }
    }

    public void setArtistsRankingNumber(int artistsRankingNumber) {
        this.artistsRankingNumber = artistsRankingNumber;
    }

    private void refreshArtists() {
        List<Artist> artistsList = networkArtistsGateway.getArtists(artistsRankingNumber);
        output.onArtistsRefreshed(artistsList);
        localArtistsGateway.removeAllArtists();
        localArtistsGateway.persistsArtists(artistsList);
    }

    public interface RefreshArtistsInteractorOutput {
        void onArtistsRefreshed(List<Artist> artists);

        void onRefreshArtistsError();
    }
}
