package com.ricardonavarrom.mercury.api;

import com.ricardonavarrom.mercury.api.model.EchonestResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface EchonestApiClient {
    String API_KEY_PARAM = "api_key";
    String FORMAT_PARAM = "format";
    String NUM_RESULTS_PARAM = "results";
    String GENRE_PARAM = "genre";

    @GET("artist/top_hottt?bucket=hotttnesss_rank&bucket=id:spotify")
    Call<EchonestResponse> getArtistsRanking(
            @Query(API_KEY_PARAM) String apiKey,
            @Query(FORMAT_PARAM) String format,
            @Query(NUM_RESULTS_PARAM) int numResults,
            @Query(GENRE_PARAM) String genre
    );
}
