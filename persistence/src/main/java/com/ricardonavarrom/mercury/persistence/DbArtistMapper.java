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
        String externalUrl = cursor.getString(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_EXTERNAL_URL));
        String genres = cursor.getString(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_GENRES));
        String smallImage = cursor.getString(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_SMALL_IMAGE));
        String mediumImage = cursor.getString(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_MEDIUM_IMAGE));
        String bigImage = cursor.getString(cursor.getColumnIndex(ArtistContract.ArtistEntry.COLUMN_BIG_IMAGE));

        return new Artist.Builder()
            .id(id)
            .name(name)
            .rank(rank)
            .externalUrl(externalUrl)
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
        contentValues.put(ArtistContract.ArtistEntry.COLUMN_EXTERNAL_URL, artist.getExternalUrl());
        contentValues.put(ArtistContract.ArtistEntry.COLUMN_GENRES, artist.getGenres());
        contentValues.put(ArtistContract.ArtistEntry.COLUMN_SMALL_IMAGE, artist.getSmallImage());
        contentValues.put(ArtistContract.ArtistEntry.COLUMN_MEDIUM_IMAGE, artist.getMediumImage());
        contentValues.put(ArtistContract.ArtistEntry.COLUMN_BIG_IMAGE, artist.getBigImage());

        return contentValues;
    }
}
