package com.smithyproductions.lockbot;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smithyproductions.lockbot.network.NetworkHelper;
import com.smithyproductions.lockbot.network.NetworkService;
import com.smithyproductions.lockbot.network.model.NumberRequestResponse;
import com.smithyproductions.lockbot.network.model.StandardResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class PhoneFragment extends Fragment {

    private PhoneFragmentListener callback;
    private boolean startedIntent;

    public interface PhoneFragmentListener {
        void onVoiceConfirmed();
    }

    private static final String UUID_KEY = "UUID_KEY";
    private String uuid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uuid = getArguments().getString(UUID_KEY);

        beginVoiceTest();
    }

    private void beginVoiceTest() {

        NetworkService service = NetworkHelper.getNetworkService();

        service.requestNumber(new Callback<NumberRequestResponse>() {
            @Override
            public void success(NumberRequestResponse standardResponse, Response response) {
                String phoneNumber = standardResponse.getNumber();

                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + phoneNumber));
                getActivity().startActivity(intent);

                startedIntent = true;

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        callback = (PhoneFragmentListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.voice_fragment_layout, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(startedIntent){
            callback.onVoiceConfirmed();
        }
    }

    public static Fragment newInstance(String uuid) {
        PhoneFragment fragment = new PhoneFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UUID_KEY, uuid);

        fragment.setArguments(bundle);

        return fragment;
    }
}
