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
        return images.get(1);
    }

    public SpotifyArtistImage getMediumImage()
    {
        return images.get(2);
    }

    public SpotifyArtistImage getSmallImage()
    {
        return images.get(3);
    }
}
