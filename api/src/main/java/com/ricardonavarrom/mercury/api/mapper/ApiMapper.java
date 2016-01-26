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
                list.add(mapSpotifyArtist(i+1, i+1, items.get(i)));
            }
        }
        return list;
    }

    public Artist mapSpotifyArtist(int id, int rank, SpotifyArtist spotifyArtist)
    {
        return new Artist.Builder()
                .id(id)
                .name(spotifyArtist.getName())
                .rank(rank)
                .url(spotifyArtist.getExternalUrls().getSpotify())
                .uri(spotifyArtist.getSpotifyUri())
                .genres(genresToString(spotifyArtist.getGenres()))
                .smallImage(spotifyArtist.getSmallImage().getUrl())
                .mediumImage(spotifyArtist.getMediumImage().getUrl())
                .bigImage(spotifyArtist.getBigImage().getUrl())
                .build();
    }

    private String genresToString(List<String> genres) {
        String genresString = null;

        if (!genres.isEmpty()) {
            genresString = genres.toString();
            genresString = genresString.replace("[", "").replace("]", "");
        }

        return genresString;
    }
}
