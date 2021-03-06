package com.ricardonavarrom.mercury.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.ricardonavarrom.mercury.domain.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class DbArtistMapper {

    public Artist mapFromDb(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_NAME));
        int rank = cursor.getInt(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_RANK));
        String url = cursor.getString(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_URL));
        String uri = cursor.getString(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_URI));
        String genres = cursor.getString(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_GENRES));
        String smallImage = cursor.getString(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_SMALL_IMAGE));
        String mediumImage = cursor.getString(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_MEDIUM_IMAGE));
        String bigImage = cursor.getString(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_BIG_IMAGE));

        return new Artist.Builder()
            .id(id)
            .name(name)
            .rank(rank)
            .url(url)
            .uri(uri)
            .genres(genres)
            .smallImage(smallImage)
            .mediumImage(mediumImage)
            .bigImage(bigImage)
            .build();
    }

    public List<ContentValues> mapToDb(List<Artist> artistList) {
        List<ContentValues> contentValues = new ArrayList<>(artistList.size());
        for (Artist artist : artistList) {
            contentValues.add(mapArtistItemToDb(artist));
        }
        return contentValues;
    }

    private ContentValues mapArtistItemToDb(Artist artist) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ArtistContract.ArtistEntry.COLUMN_ID, artist.getId());
        contentValues.put(ArtistContract.ArtistEntry.COLUMN_NAME, artist.getName());
        contentValues.put(ArtistContract.ArtistEntry.COLUMN_RANK, artist.getRank());
        contentValues.put(ArtistContract.ArtistEntry.COLUMN_URL, artist.getUrl());
        contentValues.put(ArtistContract.ArtistEntry.COLUMN_URI, artist.getUri());
        contentValues.put(ArtistContract.ArtistEntry.COLUMN_GENRES, artist.getGenres());

        if (artist.getGenres() != null) {
            contentValues.put(ArtistContract.ArtistEntry.COLUMN_GENRES, artist.getGenres());
        } else {
            contentValues.putNull(ArtistContract.ArtistEntry.COLUMN_GENRES);
        }

        if (artist.getSmallImage() != null) {
            contentValues.put(ArtistContract.ArtistEntry.COLUMN_SMALL_IMAGE, artist.getSmallImage());
        } else {
            contentValues.putNull(ArtistContract.ArtistEntry.COLUMN_SMALL_IMAGE);
        }

        if (artist.getMediumImage() != null) {
            contentValues.put(ArtistContract.ArtistEntry.COLUMN_MEDIUM_IMAGE, artist.getMediumImage());
        } else {
            contentValues.putNull(ArtistContract.ArtistEntry.COLUMN_MEDIUM_IMAGE);
        }

        if (artist.getBigImage() != null) {
            contentValues.put(ArtistContract.ArtistEntry.COLUMN_BIG_IMAGE, artist.getBigImage());
        } else {
            contentValues.putNull(ArtistContract.ArtistEntry.COLUMN_BIG_IMAGE);
        }

        return contentValues;
    }
}
