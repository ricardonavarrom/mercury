package com.ricardonavarrom.mercury;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ricardonavarrom.mercury.domain.model.Artist;

public class MainActivity extends AppCompatActivity implements ArtistsFragment.Callback {

    private int artistsRankingNumber;
    private String artistsRankingGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.main_fab);
        fab.setVisibility(View.GONE);

        artistsRankingNumber = getPreferredArtistsRankingNumber();
        artistsRankingGenre = getPreferredArtistsRankingGenre();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        int actualArtistsRankingNumber = getPreferredArtistsRankingNumber();
        String actualArtistsRankingGenre = getPreferredArtistsRankingGenre();
        if (actualArtistsRankingNumber != artistsRankingNumber
                || !actualArtistsRankingGenre.equals(artistsRankingGenre)) {
            ArtistsFragment artistsFragment = (ArtistsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.content_fragment_artists);
            if (artistsFragment != null) {
                artistsFragment.onRefreshNecessary(actualArtistsRankingNumber,
                        actualArtistsRankingGenre, isOnline());
            }
        }
        artistsRankingNumber = actualArtistsRankingNumber;
        artistsRankingGenre = actualArtistsRankingGenre;
    }

    @Override
    public void onItemSelected(Artist artist) {
        ArtistActivity.startActivity(this, artist);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int getPreferredArtistsRankingNumber() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String stringArtistsRankingNumber = sharedPreferences.getString(
                getString(R.string.pref_artists_rank_number_key),
                getString(R.string.pref_artists_rank_number_default)
        );

        return Integer.parseInt(stringArtistsRankingNumber);
    }

    public String getPreferredArtistsRankingGenre() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(
                getString(R.string.pref_artists_rank_genre_key),
                getString(R.string.pref_artists_rank_genre_default)
        );
    }

    public void refreshArtistsRanking(View view) {
        int actualArtistsRankingNumber = getPreferredArtistsRankingNumber();
        String actualArtistsRankingGenre = getPreferredArtistsRankingGenre();

        ArtistsFragment artistsFragment = (ArtistsFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_fragment_artists);
        if (artistsFragment != null) {
            artistsFragment.onRefreshNecessary(actualArtistsRankingNumber,
                    actualArtistsRankingGenre, isOnline());
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
