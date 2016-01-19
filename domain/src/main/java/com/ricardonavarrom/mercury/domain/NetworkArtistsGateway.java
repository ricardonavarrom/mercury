package com.ricardonavarrom.mercury.domain;

import com.ricardonavarrom.mercury.domain.model.Artist;

import java.util.List;

public interface NetworkArtistsGateway {
    List<Artist> getArtists();
}
