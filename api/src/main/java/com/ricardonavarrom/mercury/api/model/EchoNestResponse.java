package com.ricardonavarrom.mercury.api.model;

import com.google.gson.annotations.SerializedName;

public class EchonestResponse {

    @SerializedName("response")
    private EchonestArtistsList artists;

    public EchonestArtistsList getArtists() {
        return artists;
    }
}
