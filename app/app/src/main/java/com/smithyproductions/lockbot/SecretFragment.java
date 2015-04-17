package com.smithyproductions.lockbot;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smithyproductions.lockbot.network.NetworkHelper;
import com.smithyproductions.lockbot.network.NetworkService;
import com.smithyproductions.lockbot.network.model.StandardResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class SecretFragment extends Fragment {


    public SecretFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.secret_fragment_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        registerClient();

    }

    private void registerClient() {

    }


}
