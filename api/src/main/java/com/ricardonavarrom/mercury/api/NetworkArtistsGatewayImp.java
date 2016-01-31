package com.ricardonavarrom.mercury.api;

import com.ricardonavarrom.mercury.api.mapper.ApiMapper;
import com.ricardonavarrom.mercury.api.model.EchonestArtist;
import com.ricardonavarrom.mercury.api.model.EchonestArtistForeignId;
import com.ricardonavarrom.mercury.api.model.EchonestResponse;
import com.ricardonavarrom.mercury.api.model.SpotifyArtistsList;
import com.ricardonavarrom.mercury.domain.NetworkArtistsGateway;
import com.ricardonavarrom.mercury.domain.model.Artist;

import java.io.IOException;
import java.net.URLDecoder;
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

    @Override public List<Artist> getArtists(int artistsRankingNumber, String artistsRankingGenre) {
        try {
            String genresValue = artistsRankingGenre.equals("all")
                    ? null
                    : URLDecoder.decode(artistsRankingGenre, "UTF-8");
            EchonestResponse echonestResponse =
                  echoNestApiClient.getArtistsRanking(
                          echonestApiKey, "json", artistsRankingNumber, genresValue)
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
            List<EchonestArtistForeignId> echonestArtistForeignIds = echonestArtist.getForeignIds();
            if (echonestArtistForeignIds != null) {
                String spotifyId = echonestArtist.getForeignIds().get(0).getForeignId();
                spotifyId = spotifyId.replace("spotify:artist:", "");

                spotifyArtistIdsString = spotifyArtistIdsString == ""
                        ? spotifyId
                        : spotifyArtistIdsString + "," + spotifyId;
            }
        }

        return spotifyArtistIdsString;
    }
}
