package com.ricardonavarrom.mercury;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ricardonavarrom.mercury.domain.model.Artist;

public class MainActivity extends AppCompatActivity implements ArtistsFragment.Callback {

    private int artistsRankingNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        artistsRankingNumber = getPreferredArtistRankingNumber();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        int actualArtistsRankingNumber = getPreferredArtistRankingNumber();
        if (actualArtistsRankingNumber != artistsRankingNumber) {
            ArtistsFragment artistsFragment = (ArtistsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.content_fragment_artists);
            if (artistsFragment != null) {
                artistsFragment.onSharedPreferenceChanged(actualArtistsRankingNumber);
            }
        }
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

    public int getPreferredArtistRankingNumber() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String stringArtistsRankingNumber = sharedPreferences.getString(
                getString(R.string.pref_artists_rank_number_key),
                getString(R.string.pref_artists_rank_number_default)
        );

        return Integer.parseInt(stringArtistsRankingNumber);
    }
}
