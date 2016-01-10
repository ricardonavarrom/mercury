package com.ricardonavarrom.mercury.api.model;

import com.google.gson.annotations.SerializedName;

public class EchoNestResponse {

    @SerializedName("response")
    private EchoNestArtistsList artists;

    public EchoNestArtistsList getArtists() {
        return artists;
    }
}
