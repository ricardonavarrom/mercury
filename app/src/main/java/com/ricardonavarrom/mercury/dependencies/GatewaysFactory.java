package com.ricardonavarrom.mercury.dependencies;

import com.ricardonavarrom.mercury.api.NetworkArtistsGatewayImp;
import com.ricardonavarrom.mercury.api.SpotifyApiClient;
import com.ricardonavarrom.mercury.domain.model.NetworkArtistsGateway;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class GatewaysFactory {

//    public static NetworkArtistsGateway makeNetworkArtistsGateway() {
//        return new NetworkArtistsGatewayImp(makeEchonestApiClient(), BuildConfig.ECHONEST_API_KEY);
//    }
//
//    private static EchoNestApiClient makeEchonestApiClient() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BuildConfig.ECHONEST_API_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        return retrofit.create(EchoNestApiClient.class);
//    }

    public static NetworkArtistsGateway makeNetworkArtistsGateway() {
        return new NetworkArtistsGatewayImp(makeSpotifyApiClient(makeHttpClient()), "KEY QUE NO SIRVE");
    }

    private static SpotifyApiClient makeSpotifyApiClient(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.spotify.com")
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
