package com.ricardonavarrom.mercury.domain.interactor;

import com.ricardonavarrom.mercury.domain.model.Artist;
import com.ricardonavarrom.mercury.domain.model.NetworkArtistsGateway;

import java.util.Arrays;
import java.util.List;

public class LoadArtistsInteractor implements Interactor {

    private LoadArtistsInteractorOutput output;
    private NetworkArtistsGateway networkArtistsGateway;

    public LoadArtistsInteractor(NetworkArtistsGateway networkArtistsGateway) {
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
//        List<Artist> artistsList = createArrayList();
        List<Artist> artistsList = networkArtistsGateway.getArtistsRanking();
        output.onArtistsLoaded(artistsList);

    }

    private List<Artist> createArrayList() {

        return Arrays.asList(
                new Artist.Builder().id("1").name("Coldplay").build(),
                new Artist.Builder().id("1").name("Coldplay").build(),
                new Artist.Builder().id("1").name("Coldplay").build(),
                new Artist.Builder().id("1").name("Coldplay").build(),
                new Artist.Builder().id("1").name("Coldplay").build(),
                new Artist.Builder().id("1").name("Coldplay").build(),
                new Artist.Builder().id("1").name("Coldplay").build(),
                new Artist.Builder().id("1").name("Coldplay").build(),
                new Artist.Builder().id("1").name("Coldplay").build(),
                new Artist.Builder().id("1").name("Coldplay").build()
        );
    }

    public interface LoadArtistsInteractorOutput {
        void onArtistsLoaded(List<Artist> artists);

        void onLoadArtistsError();
    }
}
