package com.ricardonavarrom.mercury.domain.interactor;

import com.ricardonavarrom.mercury.domain.LocalArtistsGateway;
import com.ricardonavarrom.mercury.domain.NetworkArtistsGateway;
import com.ricardonavarrom.mercury.domain.model.Artist;

import java.util.List;

public class LoadArtistsInteractor implements Interactor {

    private LoadArtistsInteractorOutput output;
    private LocalArtistsGateway localArtistsGateway;
    private NetworkArtistsGateway networkArtistsGateway;

    private int artistsRankingNumber;
    private String artistsRankingGenre;
    private boolean isOnline;

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

    public void setArtistsRankingNumber(int artistsRankingNumber) {
        this.artistsRankingNumber = artistsRankingNumber;
    }

    public void setArtistsRankingGenre(String artistsRankingGenre) {
        this.artistsRankingGenre = artistsRankingGenre;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    private void loadArtists() {
        List<Artist> artistsList = localArtistsGateway.getArtists();
        if (artistsList.isEmpty()) {
            if (!isOnline) {
                output.onNetworkError();
            } else {
                artistsList = networkArtistsGateway.getArtists(artistsRankingNumber,
                        artistsRankingGenre);
                output.onArtistsLoaded(artistsList);
                localArtistsGateway.persistsArtists(artistsList);
            }
        } else {
            output.onArtistsLoaded(artistsList);
        }
    }

    public interface LoadArtistsInteractorOutput {
        void onArtistsLoaded(List<Artist> artists);

        void onLoadArtistsError();

        void onNetworkError();
    }
}
