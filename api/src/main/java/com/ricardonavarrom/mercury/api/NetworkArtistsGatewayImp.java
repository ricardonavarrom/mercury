package com.ricardonavarrom.mercury.api;

import com.ricardonavarrom.mercury.api.mapper.ApiMapper;
import com.ricardonavarrom.mercury.api.model.EchoNestResponse;
import com.ricardonavarrom.mercury.domain.model.Artist;
import com.ricardonavarrom.mercury.domain.model.NetworkArtistsGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkArtistsGatewayImp implements NetworkArtistsGateway {

  private final EchoNestApiClient apiClient;
  private final String apiKey;
  private final ApiMapper apiMapper = new ApiMapper();

  public NetworkArtistsGatewayImp(EchoNestApiClient apiClient, String apiKey) {
    this.apiClient = apiClient;
    this.apiKey = apiKey;
  }

  @Override public List<Artist> getArtistsRanking() {
    try {
      EchoNestResponse echoNestResponse =
              apiClient.getSpotifyArtistsRanking(apiKey, "json", 10, "hotttnesss-desc")
                      .execute().body();

      return apiMapper.mapArtists(echoNestResponse);
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }


//    private final SpotifyApiClient apiClient;
//    private final String apiKey;
//    private final ApiMapper apiMapper = new ApiMapper();
//
//    public NetworkArtistsGatewayImp(SpotifyApiClient apiClient, String apiKey) {
//        this.apiClient = apiClient;
//        this.apiKey = apiKey;
//    }
//
//    @Override public List<Artist> getArtistsRanking() {
//
//        try {
//            SpotifyArtistsList spotifyArtistsList =
//                    apiClient.getArtists().execute().body();
//
//            return apiMapper.mapSpotifyArtists(spotifyArtistsList);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
}
