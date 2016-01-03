package com.ricardonavarrom.mercury;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivityFragment extends Fragment {

//    ArrayAdapter<SpotifyArtist> arrayAdapter;
    ArrayAdapter<EchoNestArtist> arrayAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        arrayAdapter = new ArrayAdapter<SpotifyArtist>(
//                getActivity(),
//                R.layout.list_item_artists,
//                R.id.list_item_artists_textview,
//                new ArrayList<SpotifyArtist>()
//        );
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://api.spotify.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        SpotifyApi spotifyApi = retrofit.create(SpotifyApi.class);
//        Call<SpotifyArtistsList> call = spotifyApi.getArtists();
//        call.enqueue(new Callback<SpotifyArtistsList>() {
//            @Override
//            public void onResponse(Response<SpotifyArtistsList> response, Retrofit retrofit) {
//                List<SpotifyArtist> artistsList = response.body().getItems();
//                arrayAdapter.clear();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                    arrayAdapter.addAll(artistsList);
//                } else {
//                    for (int i = 0; i < artistsList.size(); i++) {
//                       arrayAdapter.add(artistsList.get(i));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.e("error", t.getMessage());
//            }
//        });


        arrayAdapter = new ArrayAdapter<EchoNestArtist>(
                getActivity(),
                R.layout.list_item_artists,
                R.id.list_item_artists_textview,
                new ArrayList<EchoNestArtist>()
        );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://developer.echonest.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EchoNestApi echoNestApi = retrofit.create(EchoNestApi.class);
        Call<EchoNestResponse> call = echoNestApi.getArtists();
        call.enqueue(new Callback<EchoNestResponse>() {
            @Override
            public void onResponse(Response<EchoNestResponse> response, Retrofit retrofit) {
                List<EchoNestArtist> artistsList = response.body().getArtists().getItems();
                arrayAdapter.clear();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    arrayAdapter.addAll(artistsList);
                } else {
                    for (int i = 0; i < artistsList.size(); i++) {
                        arrayAdapter.add(artistsList.get(i));
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("error", t.getMessage());
            }
        });

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.artists_listview);
        listView.setAdapter(arrayAdapter);

        return rootView;
    }
}
