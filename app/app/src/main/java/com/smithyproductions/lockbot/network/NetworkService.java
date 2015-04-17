package com.smithyproductions.lockbot.network;

import com.smithyproductions.lockbot.network.model.NumberRequestResponse;
import com.smithyproductions.lockbot.network.model.StandardResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

public interface NetworkService {
    @PUT("/register")
    void register(@Query("uuid") String number, Callback<StandardResponse> callback);

    @POST("/requestText")
    void requestText(@Query("uuid") String number, Callback<StandardResponse> callback);

    @GET("/validateMessage")
    void validateMessage(@Query("uuid") String uuid, @Query("message") String message, Callback<StandardResponse> callback);

    @GET("/requestPhoneNumber")
    void requestNumber(Callback<NumberRequestResponse> callback);
}
