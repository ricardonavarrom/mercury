package com.ricardonavarrom.mercury.api;

import com.ricardonavarrom.mercury.api.mapper.ApiMapper;
import com.ricardonavarrom.mercury.api.model.EchonestArtist;
import com.ricardonavarrom.mercury.api.model.EchonestResponse;
import com.ricardonavarrom.mercury.api.model.SpotifyArtistsList;
import com.ricardonavarrom.mercury.domain.model.Artist;
import com.ricardonavarrom.mercury.domain.model.NetworkArtistsGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkArtistsGatewayImp implements NetworkArtistsGateway {

    private final EchonestApiClient echoNestApiClient;
    private final String echonestApiKey;
    private final SpotifyApiClient spotifyApiClient;
    private final ApiMapper apiMapper;

    public NetworkArtistsGatewayImp(EchonestApiClient echonestApiClient, String echonestApiKey,
                                  SpotifyApiClient spotifyApiClient) {
        this.echoNestApiClient = echonestApiClient;
        this.echonestApiKey = echonestApiKey;
        this.spotifyApiClient = spotifyApiClient;
        apiMapper = new ApiMapper();
    }

    @Override public List<Artist> getTop10ArtistsRanking() {
        try {
            EchonestResponse echonestResponse =
                  echoNestApiClient.getArtistsRanking(echonestApiKey, "json", 10, "hotttnesss-desc")
                          .execute().body();
            List<EchonestArtist> echonestArtists = echonestResponse.getArtists().getItems();

            String spotifyArtistIdsString =
                    this.getSpotifyArtistIdsStringFromEchonestArtists(echonestArtists);
            SpotifyArtistsList spotifyArtistsList =
                    spotifyApiClient.getSeveralArtists(spotifyArtistIdsString).execute().body();

            return apiMapper.mapSpotifyArtists(spotifyArtistsList);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private String getSpotifyArtistIdsStringFromEchonestArtists(
            List<EchonestArtist> echonestArtists) {

        String spotifyArtistIdsString = "";

        for (EchonestArtist echonestArtist : echonestArtists) {
            String spotifyId = echonestArtist.getForeignIds().get(0).getForeignId();
            spotifyId = spotifyId.replace("spotify:artist:", "");

            spotifyArtistIdsString = spotifyArtistIdsString == ""
                    ? spotifyId
                    : spotifyArtistIdsString + "," + spotifyId;
        }

        return spotifyArtistIdsString;
    }
}
