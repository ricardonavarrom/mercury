package com.ricardonavarrom.mercury.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class EchonestArtist {

    private String id;
    private String name;
    @SerializedName("foreign_ids")
    private List<EchonestArtistForeignId> foreignIds;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<EchonestArtistForeignId> getForeignIds() {
        return foreignIds;
    }
}
