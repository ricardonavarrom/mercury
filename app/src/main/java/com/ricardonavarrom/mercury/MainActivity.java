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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (isNecessaryRefreshRanking()) {
                ArtistsFragment artistsFragment = (ArtistsFragment)
                        getSupportFragmentManager().findFragmentById(R.id.content_fragment_artists);
                if (artistsFragment != null) {
                    artistsFragment.onRefreshNecessary(getPreferredArtistsRankingNumber(),
                            getPreferredArtistsRankingGenre(), isOnline());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
        int actualArtistsRankingNumber = getPreferredArtistsRankingNumber();
        String actualArtistsRankingGenre = getPreferredArtistsRankingGenre();

        return actualArtistsRankingNumber != artistsRankingNumber
                || !actualArtistsRankingGenre.equals(artistsRankingGenre) || artistsRankIsExpired();
    }

    private void runFloatingActionButtonAction(View view) throws ParseException {
        String actualGenre = getPreferredArtistsRankingGenre();
        String snackBarText = actualGenre.equals(getString(R.string.pref_artists_rank_genre_default))
                ? getString(R.string.echonest_data_message_all_genres)
                : getString(R.string.echonest_data_message) + ": " + getPreferredArtistsRankingGenre();
        snackBarText = snackBarText.concat(". " + getString(R.string.last_data_update_date)
                + ": " + getLastRankUpdateDateString());

        Snackbar.make(view, snackBarText, Snackbar.LENGTH_LONG).show();
    }

    private String getLastRankUpdateDateString() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getExpirationDateRank());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

        return dateFormat.format(calendar.getTime());
    }
}
