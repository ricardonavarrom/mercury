package com.ricardonavarrom.mercury.domain;

import com.ricardonavarrom.mercury.domain.model.Artist;

import java.util.List;

public interface LocalArtistsGateway {
    List<Artist> getArtists();
    void removeAllArtists();
    void persistsArtists(List<Artist> artistList);
    Artist getArtist(int artistId);
}
