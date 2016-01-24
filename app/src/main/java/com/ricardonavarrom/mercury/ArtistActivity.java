package com.ricardonavarrom.mercury;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ricardonavarrom.mercury.domain.model.Artist;

public class ArtistActivity extends AppCompatActivity {

    public static void startActivity(Context context, Artist artist) {
        Intent intent = new Intent(context, ArtistActivity.class);
        intent.putExtra("artistId", artist.getId());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            int artistId = getIntent().getIntExtra("artistId", 0);
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
}
