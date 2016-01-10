package com.ricardonavarrom.mercury.api;

import com.ricardonavarrom.mercury.api.model.SpotifyArtistsList;

import retrofit.Call;
import retrofit.http.GET;

public interface SpotifyApiClient {
    @GET("/v1/artists/43ZHCT0cAZBISjO8DG9PnE/related-artists")
    Call<SpotifyArtistsList> getArtists();
}
