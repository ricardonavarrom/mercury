package com.ricardonavarrom.mercury.dependencies;

import android.content.Context;

import com.ricardonavarrom.mercury.BuildConfig;
import com.ricardonavarrom.mercury.api.EchonestApiClient;
import com.ricardonavarrom.mercury.api.NetworkArtistsGatewayImp;
import com.ricardonavarrom.mercury.api.SpotifyApiClient;
import com.ricardonavarrom.mercury.domain.LocalArtistsGateway;
import com.ricardonavarrom.mercury.domain.NetworkArtistsGateway;
import com.ricardonavarrom.mercury.persistence.LocalArtistGatewayImp;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class GatewaysFactory {

    public static LocalArtistsGateway makeLocalArtistsGateway(Context context) {
        return new LocalArtistGatewayImp(context);
    }

    public static NetworkArtistsGateway makeNetworkArtistsGateway() {
        return new NetworkArtistsGatewayImp(makeEchonestApiClient(makeHttpClient()),
                BuildConfig.ECHONEST_API_KEY, makeSpotifyApiClient(makeHttpClient()));
    }

    private static EchonestApiClient makeEchonestApiClient(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.ECHONEST_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(EchonestApiClient.class);
    }

    private static SpotifyApiClient makeSpotifyApiClient(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.SPOTIFY_API_URL)
                .client(client)
                .build();
        return retrofit.create(SpotifyApiClient.class);
    }

    private static OkHttpClient makeHttpClient() {
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);
        return client;
    }
}
