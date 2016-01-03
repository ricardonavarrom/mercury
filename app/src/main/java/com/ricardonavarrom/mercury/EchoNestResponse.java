package com.ricardonavarrom.mercury;

import com.google.gson.annotations.SerializedName;

public class EchoNestResponse {

    @SerializedName("response")
    private EchoNestArtistsList artists;

    public EchoNestArtistsList getArtists() {
        return artists;
    }
}
