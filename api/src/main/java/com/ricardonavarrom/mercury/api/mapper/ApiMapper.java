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
                list.add(mapSpotifyArtist(i, i, items.get(i)));
            }
        }
        return list;
    }

    public Artist mapSpotifyArtist(int id, int rank, SpotifyArtist spotifyArtist)
    {
        String genres = spotifyArtist.getGenres().toString();
        genres = genres.replace("[", "").replace("]", "");

        return new Artist.Builder()
                .id(id)
                .name(spotifyArtist.getName())
                .rank(rank)
                .externalUrl(spotifyArtist.getExternalUrls().getSpotify())
                .genres(genres)
                .smallImage(spotifyArtist.getSmallImage().getUrl())
                .mediumImage(spotifyArtist.getMediumImage().getUrl())
                .bigImage(spotifyArtist.getBigImage().getUrl())
                .build();
    }
}
