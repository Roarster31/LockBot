package com.smithyproductions.lockbot.network;

import retrofit.RestAdapter;

/**
 * Created by rory on 16/04/15.
 */
public class NetworkHelper {

    public static NetworkService getNetworkService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://home.smithy.productions:3953")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter.create(NetworkService.class);
    }

}
