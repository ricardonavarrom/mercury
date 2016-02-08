package com.ricardonavarrom.mercury;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ricardonavarrom.mercury.domain.model.Artist;

public class ArtistActivity extends AppCompatActivity {

    private int artistId;
    private String artistUrl;
    private String artistUri;
    private String genreRankString;
    private String rankLastUpdateDateString;
    private String artistShareString;


    private ShareActionProvider shareActionProvider;

    public static void startActivity(Context context, Artist artist, String genreRankString,
                                     String rankLastUpdateDateString) {
        Intent intent = new Intent(context, ArtistActivity.class);
        intent.putExtra("artistId", artist.getId());
        intent.putExtra("artistUrl", artist.getUrl());
        intent.putExtra("artistUri", artist.getUri());
        intent.putExtra("genreRankString", genreRankString);
        intent.putExtra("rankLastUpdateDateString", rankLastUpdateDateString);

        String artistShareString = context.getString(
                R.string.artist_share_string,
                artist.getName(),
                artist.getRank(),
                genreRankString
        );
        intent.putExtra("artistShareString", artistShareString);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.artist_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runListenArtistIntent();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        artistId = getIntent().getIntExtra("artistId", 0);
        artistUrl = getIntent().getStringExtra("artistUrl");
        artistUri = getIntent().getStringExtra("artistUri");
        genreRankString = getIntent().getStringExtra("genreRankString");
        rankLastUpdateDateString = getIntent().getStringExtra("rankLastUpdateDateString");
        artistShareString = getIntent().getStringExtra("artistShareString");
        if (savedInstanceState == null) {
            ArtistFragment artistFragment = ArtistFragment.newInstance(artistId, genreRankString,
                    rankLastUpdateDateString);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_fragment_artist_container, artistFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_artist, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (artistShareString != null) {
            shareActionProvider.setShareIntent(createShareArtistIntent());
        }

        return true;
    }

    private boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void runListenArtistIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String intentData = isPackageInstalled(BuildConfig.SPOTIFY_PACKAGE_NAME, this)
                ? artistUri
                : artistUrl;
        intent.setData(Uri.parse(intentData));
        startActivity(intent);
    }

    private Intent createShareArtistIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, artistShareString);
        return shareIntent;
    }
}
