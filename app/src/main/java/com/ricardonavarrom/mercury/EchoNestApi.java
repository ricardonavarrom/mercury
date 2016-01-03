package com.ricardonavarrom.mercury;

import retrofit.Call;
import retrofit.http.GET;

public interface EchoNestApi {
    @GET("/api/v4/artist/search?api_key=RLYRTQDYNZ3W8VMUF&format=json&results=10&sort=hotttnesss-desc&bucket=hotttnesss_rank&bucket=id:spotify")
    Call<EchoNestResponse> getArtists();
}
