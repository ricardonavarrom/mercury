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
    private String artistsRankingGenre;
    private boolean isOnline;

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

    public void setArtistsRankingGenre(String artistsRankingGenre) {
        this.artistsRankingGenre = artistsRankingGenre;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    private void refreshArtists() {
        localArtistsGateway.removeAllArtists();
        if (!isOnline) {
            output.onNetworkError();
        } else {
            List<Artist> artistsList = networkArtistsGateway.getArtists(artistsRankingNumber,
                    artistsRankingGenre);
            output.onArtistsRefreshed(artistsList);
            localArtistsGateway.persistsArtists(artistsList);
        }
    }

    public interface RefreshArtistsInteractorOutput {
        void onArtistsRefreshed(List<Artist> artists);

        void onRefreshArtistsError();

        void onNetworkError();
    }
}
