package com.ricardonavarrom.mercury;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AboutFragment extends Fragment {

    private ImageView githubImageView;
    private ImageView twitterImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        bindView(rootView);

        return rootView;
    }

    private void bindView(View rootView) {
        githubImageView = (ImageView) rootView.findViewById(R.id.about_github_image);
        githubImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBrowserIntent(getString(R.string.about_github_url));
            }
        });

        twitterImageView = (ImageView) rootView.findViewById(R.id.about_twitter_image);
        twitterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBrowserIntent(getString(R.string.about_twitter_url));
            }
        });
    }

    private void runBrowserIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
