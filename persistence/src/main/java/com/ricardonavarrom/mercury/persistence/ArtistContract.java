package com.ricardonavarrom.mercury.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


public class ArtistContract {

    public static final String CONTENT_AUTHORITY = "com.ricardonavarrom.mercury.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ARTIST = "artist";

    public static final class ArtistEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTIST).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTIST;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTIST;

        public static final String TABLE_NAME = "artist";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_RANK = "rank";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URI = "uri";
        public static final String COLUMN_GENRES = "genres";
        public static final String COLUMN_SMALL_IMAGE = "small_image";
        public static final String COLUMN_MEDIUM_IMAGE = "medium_image";
        public static final String COLUMN_BIG_IMAGE = "big_image";

        public static Uri buildArtistUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildArtistsUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildArtistWithIdUri(int artistId) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(artistId)).build();
        }

        public static String getArtistIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
