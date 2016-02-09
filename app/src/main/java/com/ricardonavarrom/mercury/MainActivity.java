package com.ricardonavarrom.mercury;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ricardonavarrom.mercury.domain.model.Artist;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ArtistsFragment.Callback,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private int artistsRankingNumber;
    private String artistsRankingGenre;
    private boolean rankSettingsChanged;

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
                try {
                    runFloatingActionButtonAction(view);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        artistsRankingNumber = getPreferredArtistsRankingNumber();
        artistsRankingGenre = getPreferredArtistsRankingGenre();
        rankSettingsChanged = getPreferredArtistsRankingOptionsChanged();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle(null);

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        artistsRankingNumber = getPreferredArtistsRankingNumber();
        artistsRankingGenre = getPreferredArtistsRankingGenre();
        rankSettingsChanged = getPreferredArtistsRankingOptionsChanged();
        try {
            if (isNecessaryRefreshRanking()) {
                ArtistsFragment artistsFragment = (ArtistsFragment)
                        getSupportFragmentManager().findFragmentById(R.id.content_fragment_artists);
                if (artistsFragment != null) {
                    artistsFragment.onRefreshNecessary(artistsRankingNumber, artistsRankingGenre,
                            isOnline());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(Artist artist) {
        String genreRankString = getPreferredArtistsRankingGenre();
        if (genreRankString.equals("all"))
            genreRankString = getString(R.string.pref_artists_rank_genre_default_name);
        if (genreRankString.equals("r%26b"))
            genreRankString = "rhythm and blues";

        try {
            ArtistActivity.startActivity(this, artist, genreRankString,
                    getLastRankUpdateDateString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    public boolean getPreferredArtistsRankingOptionsChanged() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int rankSettingsChanged =  sharedPreferences.getInt(
                getString(R.string.pref_artists_rank_settings_changed_key),
                Integer.parseInt(getString(R.string.pref_artists_rank_settings_changed_default))
        );

        return rankSettingsChanged == 1;
    }

    public void refreshArtistsRanking(View view) {
        ArtistsFragment artistsFragment = (ArtistsFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_fragment_artists);
        if (artistsFragment != null) {
            artistsFragment.onRefreshNecessary(artistsRankingNumber,
                    artistsRankingGenre, isOnline());
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private boolean artistsRankIsExpired() throws ParseException {
        Date expirationDateRank = getExpirationDateRank();
        Date todayDate = getTodayDate();

        return expirationDateRank != null && todayDate.compareTo(expirationDateRank) >= 0;
    }

    private Date getExpirationDateRank() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String stringDate = sharedPreferences.getString(
                getString(R.string.pref_artists_rank_expiration_date_key),
                null
        );

        return stringDate == null
                ? null
                : dateFormat.parse(stringDate);
    }

    private Date getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    private boolean isNecessaryRefreshRanking() throws ParseException {

        return rankSettingsChanged || artistsRankIsExpired();
    }

    private void runFloatingActionButtonAction(View view) throws ParseException {
        String actualGenre = getPreferredArtistsRankingGenre();
        String snackBarText;

        if (actualGenre.equals(getString(R.string.pref_artists_rank_genre_default))) {
            snackBarText = getString(
                    R.string.echonest_data_message_all_genres,
                    getLastRankUpdateDateString()
            );
        } else {
            if (actualGenre.equals("r%26b")) {
                snackBarText = getString(
                        R.string.echonest_data_message,
                        "r&b",
                        getLastRankUpdateDateString()
                );
            } else {
                snackBarText = getString(
                        R.string.echonest_data_message,
                        actualGenre,
                        getLastRankUpdateDateString()
                );
            }
        }

        Snackbar.make(view, snackBarText, Snackbar.LENGTH_LONG).show();
    }

    private String getLastRankUpdateDateString() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getExpirationDateRank());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

        return dateFormat.format(calendar.getTime());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (!key.equals(getString(R.string.pref_artists_rank_settings_changed_key))) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_artists_rank_settings_changed_key), 1);
            editor.commit();
        }
    }
}
