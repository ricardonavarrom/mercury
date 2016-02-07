package com.ricardonavarrom.mercury.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EchonestArtistsList {

    @SerializedName("artists")
    private List<EchonestArtist> items;

    public List<EchonestArtist> getItems() {
        return items;
    }
}
