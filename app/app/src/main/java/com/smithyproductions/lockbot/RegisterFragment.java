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
public class RegisterFragment extends Fragment {

    RegisterFragmentInterface callback;

    public interface RegisterFragmentInterface {
        void onClientRegistered(String uuid);
    }

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        registerClient();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        callback = (RegisterFragmentInterface) activity;
    }

    private void registerClient() {
        NetworkService service = NetworkHelper.getNetworkService();

        TelephonyManager tMgr =(TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String number = tMgr.getLine1Number();

        service.register(number, new Callback<StandardResponse>() {
            @Override
            public void success(StandardResponse standardResponse, Response response) {
                callback.onClientRegistered(standardResponse.getUuid());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


}
