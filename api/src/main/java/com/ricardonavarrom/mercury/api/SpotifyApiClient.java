package com.ricardonavarrom.mercury.api;

import com.ricardonavarrom.mercury.api.model.SpotifyArtistsList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface SpotifyApiClient {

    String ARTIST_IDS_PARAM = "ids";

    @GET("artists")
    Call<SpotifyArtistsList> getSeveralArtists(@Query(ARTIST_IDS_PARAM) String artistIds);
}
