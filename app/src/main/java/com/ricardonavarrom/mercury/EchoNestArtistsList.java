package com.ricardonavarrom.mercury;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EchoNestArtistsList {

    @SerializedName("artists")
    private List<EchoNestArtist> items;

    public List<EchoNestArtist> getItems() {
        return items;
    }
}
