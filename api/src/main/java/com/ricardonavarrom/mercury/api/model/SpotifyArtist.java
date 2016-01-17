package com.ricardonavarrom.mercury.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SpotifyArtist {

    private String id;
    private String name;
    @SerializedName("external_urls")
    private SpotifyExternalUrls externalUrls;
    private List<String> genres;
    private List<SpotifyArtistImage> images;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public SpotifyExternalUrls getExternalUrls() {
        return externalUrls;
    }

    public List<SpotifyArtistImage> getImages() {
        return images;
    }

    public SpotifyArtistImage getBigImage()
    {
        return images.size() > 0 ? images.get(0) : null;
    }

    public SpotifyArtistImage getMediumImage()
    {
        return images.size() > 1 ? images.get(1) : null;
    }

    public SpotifyArtistImage getSmallImage()
    {
        return images.size() > 2 ? images.get(2) : null;
    }
}
