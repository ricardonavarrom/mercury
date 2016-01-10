package com.ricardonavarrom.mercury.api;

import com.ricardonavarrom.mercury.api.model.EchoNestResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface EchoNestApiClient {
    String API_KEY_PARAM = "api_key";
    String FORMAT_PARAM = "format";
    String NUM_RESULTS_PARAM = "results";
    String SORT_PARAM = "sort";

    @GET("artist/search?bucket=hotttnesss_rank&bucket=id:spotify")
    Call<EchoNestResponse> getSpotifyArtistsRanking(
            @Query(API_KEY_PARAM) String apiKey,
            @Query(FORMAT_PARAM) String format,
            @Query(NUM_RESULTS_PARAM) int numResults,
            @Query(SORT_PARAM) String sort
    );
}
