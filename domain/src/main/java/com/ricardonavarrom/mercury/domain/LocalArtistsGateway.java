package com.ricardonavarrom.mercury.domain;

import com.ricardonavarrom.mercury.domain.model.Artist;

import java.util.List;

public interface LocalArtistsGateway {
    List<Artist> load();
    void update(List<Artist> artistList);
}
