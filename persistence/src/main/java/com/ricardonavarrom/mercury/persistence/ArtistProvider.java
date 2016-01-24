package com.ricardonavarrom.mercury.persistence;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class ArtistProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private ArtistDbHelper mOpenHelper;
    static final int ARTIST = 100;
    static final int ARTIST_WHITH_ID = 101;

    private static final SQLiteQueryBuilder sQueryBuilder;

    static {
        sQueryBuilder = new SQLiteQueryBuilder();
        sQueryBuilder.setTables(ArtistContract.ArtistEntry.TABLE_NAME);
    }

    private static final String sArtistIdSelection = ArtistContract.ArtistEntry.TABLE_NAME +
            "." + ArtistContract.ArtistEntry.COLUMN_ID + " = ? ";

    private Cursor getArtistById(Uri uri, String[] projection, String sortOrder) {
        String artistId = ArtistContract.ArtistEntry.getArtistIdFromUri(uri);

        return sQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection, sArtistIdSelection,
                new String[] {artistId}, null, null, sortOrder);
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ArtistContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ArtistContract.PATH_ARTIST, ARTIST);
        matcher.addURI(authority, ArtistContract.PATH_ARTIST + "/*", ARTIST_WHITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new ArtistDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case ARTIST: {
                retCursor = mOpenHelper.getReadableDatabase()
                        .query(ArtistContract.ArtistEntry.TABLE_NAME, projection, selection,
                                selectionArgs, null, null, sortOrder);
                break;
            }
            case ARTIST_WHITH_ID: {
                retCursor = getArtistById(uri, projection, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Override public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case ARTIST:
                return ArtistContract.ArtistEntry.CONTENT_TYPE;
            case ARTIST_WHITH_ID:
                return ArtistContract.ArtistEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case ARTIST: {
                long _id = db.insert(ArtistContract.ArtistEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = ArtistContract.ArtistEntry.buildArtistUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        if (null == selection) {
            selection = "1";
        }
        switch (match) {
            case ARTIST:
                rowsDeleted = db.delete(ArtistContract.ArtistEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case ARTIST:
                rowsUpdated = db.update(ArtistContract.ArtistEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override @TargetApi(11) public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}