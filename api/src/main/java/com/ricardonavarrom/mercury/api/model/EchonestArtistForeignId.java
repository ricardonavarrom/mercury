package com.ricardonavarrom.mercury.api.model;

import com.google.gson.annotations.SerializedName;

public class EchonestArtistForeignId {

    private String catalog;
    @SerializedName("foreign_id")
    private String foreignId;

    public String getCatalog() {
        return catalog;
    }

    public String getForeignId() {
        return foreignId;
    }
}
