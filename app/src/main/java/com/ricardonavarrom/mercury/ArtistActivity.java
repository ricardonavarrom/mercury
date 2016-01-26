package com.ricardonavarrom.mercury;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ricardonavarrom.mercury.domain.model.Artist;

public class ArtistActivity extends AppCompatActivity {

    private int artistId;
    private String artistUrl;
    private String artistUri;

    public static void startActivity(Context context, Artist artist) {
        Intent intent = new Intent(context, ArtistActivity.class);
        intent.putExtra("artistId", artist.getId());
        intent.putExtra("artistUrl", artist.getUrl());
        intent.putExtra("artistUri", artist.getUri());
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

        if (savedInstanceState == null) {
            artistId = getIntent().getIntExtra("artistId", 0);
            artistUrl = getIntent().getStringExtra("artistUrl");
            artistUri = getIntent().getStringExtra("artistUri");
            ArtistFragment artistFragment = ArtistFragment.newInstance(artistId);
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
}
