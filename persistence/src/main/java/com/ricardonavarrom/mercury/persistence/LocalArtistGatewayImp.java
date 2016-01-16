package com.ricardonavarrom.mercury.persistence;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ricardonavarrom.mercury.domain.LocalArtistsGateway;
import com.ricardonavarrom.mercury.domain.model.Artist;

import java.util.ArrayList;
import java.util.List;

import static com.ricardonavarrom.mercury.persistence.ArtistContract.ArtistEntry.CONTENT_URI;
import static com.ricardonavarrom.mercury.persistence.ArtistContract.ArtistEntry.buildArtists;

public class LocalArtistGatewayImp implements LocalArtistsGateway {

    private final ContentResolver contentResolver;
    private final DbArtistMapper mapper = new DbArtistMapper();

    public LocalArtistGatewayImp(Context context) {
        contentResolver = context.getContentResolver();
    }

    @Override
    public List<Artist> load() {
        List<Artist> artistsList = new ArrayList<>();
        Cursor cursor =
                contentResolver.query(buildArtists(), null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                artistsList.add(mapper.mapFromDb(cursor));
            }
            cursor.close();
        }
        return artistsList;
    }

    @Override
    public void update(List<Artist> artistList) {
        if (!artistList.isEmpty()) {
            List<ContentValues> contentValues = mapper.mapToDb(artistList);
            ContentValues[] values = contentValues.toArray(new ContentValues[contentValues.size()]);
            contentResolver.bulkInsert(CONTENT_URI, values);
        }
    }
}