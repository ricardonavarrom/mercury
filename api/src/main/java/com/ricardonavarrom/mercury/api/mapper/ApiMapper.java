package com.ricardonavarrom.mercury.api.mapper;

import com.ricardonavarrom.mercury.api.model.SpotifyArtist;
import com.ricardonavarrom.mercury.api.model.SpotifyArtistsList;
import com.ricardonavarrom.mercury.domain.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class ApiMapper {

    public List<Artist> mapSpotifyArtists(SpotifyArtistsList spotifyArtistsList) {
        List<Artist> list = new ArrayList<>();
        if (spotifyArtistsList != null) {
            List<SpotifyArtist> items = spotifyArtistsList.getItems();
            for (int i = 0; i < items.size(); i++) {
                list.add(mapSpotifyArtist(i, items.get(i)));
            }
        }
        return list;
    }

    public Artist mapSpotifyArtist(int id, SpotifyArtist spotifyArtist)
    {
        return new Artist.Builder()
                .id(id)
                .name(spotifyArtist.getName())
                .build();
    }
}
