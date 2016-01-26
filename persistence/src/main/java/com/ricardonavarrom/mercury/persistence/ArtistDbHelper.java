package com.ricardonavarrom.mercury.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ArtistDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "mercury_artist.db";

    public ArtistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_ARTIST_TABLE = "CREATE TABLE " + ArtistContract.ArtistEntry.TABLE_NAME + " (" +
            ArtistContract.ArtistEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
            ArtistContract.ArtistEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            ArtistContract.ArtistEntry.COLUMN_RANK + " INTEGER NOT NULL, " +
            ArtistContract.ArtistEntry.COLUMN_URL + " TEXT NOT NULL, " +
            ArtistContract.ArtistEntry.COLUMN_URI + " TEXT NOT NULL, " +
            ArtistContract.ArtistEntry.COLUMN_GENRES + " TEXT, " +
            ArtistContract.ArtistEntry.COLUMN_SMALL_IMAGE + " TEXT NOT NULL, " +
            ArtistContract.ArtistEntry.COLUMN_MEDIUM_IMAGE + " TEXT NOT NULL, " +
            ArtistContract.ArtistEntry.COLUMN_BIG_IMAGE + " TEXT NOT NULL" +
            " );";

            sqLiteDatabase.execSQL(SQL_CREATE_ARTIST_TABLE);
        }

    @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ArtistContract.ArtistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
