package com.ricardonavarrom.mercury.api.mapper;

import com.ricardonavarrom.mercury.api.model.EchoNestArtist;
import com.ricardonavarrom.mercury.api.model.EchoNestResponse;
import com.ricardonavarrom.mercury.api.model.SpotifyArtist;
import com.ricardonavarrom.mercury.api.model.SpotifyArtistsList;
import com.ricardonavarrom.mercury.domain.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class ApiMapper {

    public List<Artist> mapArtists(EchoNestResponse echoNestResponse) {
        List<Artist> list = new ArrayList<>();
        if (echoNestResponse != null) {
            List<EchoNestArtist> items = echoNestResponse.getArtists().getItems();
            for (int i = 0; i < items.size(); i++) {
                list.add(mapArtist(items.get(i)));
            }
        }
        return list;
    }

    public Artist mapArtist(EchoNestArtist echoNestArtist)
    {
        return new Artist.Builder()
            .id(echoNestArtist.getId())
            .name(echoNestArtist.getName())
            .build();
    }

    public List<Artist> mapSpotifyArtists(SpotifyArtistsList spotifyArtistsList) {
        List<Artist> list = new ArrayList<>();
        if (spotifyArtistsList != null) {
            List<SpotifyArtist> items = spotifyArtistsList.getItems();
            for (int i = 0; i < items.size(); i++) {
                list.add(mapSpotifyArtist(items.get(i)));
            }
        }
        return list;
    }

    public Artist mapSpotifyArtist(SpotifyArtist spotifyArtist)
    {
        return new Artist.Builder()
                .id(spotifyArtist.getId())
                .name(spotifyArtist.getName())
                .build();
    }
}
