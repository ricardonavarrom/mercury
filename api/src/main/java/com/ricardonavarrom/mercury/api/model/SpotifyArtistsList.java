package com.ricardonavarrom.mercury.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpotifyArtistsList {

    @SerializedName("artists")
    private List<SpotifyArtist> items;

    public List<SpotifyArtist> getItems() {
        return items;
    }
}
