package com.ricardonavarrom.mercury;

import retrofit.Call;
import retrofit.http.GET;

public interface SpotifyApi {
    @GET("/v1/artists/43ZHCT0cAZBISjO8DG9PnE/related-artists")
    Call<SpotifyArtistsList> getArtists();
}
