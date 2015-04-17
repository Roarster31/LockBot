package com.smithyproductions.lockbot;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smithyproductions.lockbot.network.NetworkHelper;
import com.smithyproductions.lockbot.network.NetworkService;
import com.smithyproductions.lockbot.network.model.StandardResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class TextFragment extends Fragment {

    private TextFragmentListener callback;

    public interface TextFragmentListener {
        void onTextConfirmed();
    }

    private static final String UUID_KEY = "UUID_KEY";
    private String uuid;

    BroadcastReceiver receiver = new BroadcastReceiver (){

        @Override
        public void onReceive(Context context, Intent intent) {
            if(Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
                final SmsMessage smsMessage = Telephony.Sms.Intents.getMessagesFromIntent(intent)[0];
                String message = smsMessage.getMessageBody();

                NetworkService service = NetworkHelper.getNetworkService();

                service.validateMessage(uuid, message, new Callback<StandardResponse>() {
                    @Override
                    public void success(StandardResponse standardResponse, Response response) {
                        if(getActivity() != null) {
                            getActivity().unregisterReceiver(receiver);
                        }
                        callback.onTextConfirmed();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("TEXTFRAGMENT","error from validator");
                    }
                });


            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uuid = getArguments().getString(UUID_KEY);

        beginTextTest();
    }

    private void beginTextTest() {

        IntentFilter filter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        getActivity().registerReceiver(receiver, filter);
        NetworkService service = NetworkHelper.getNetworkService();

        service.requestText(uuid, new Callback<StandardResponse>() {
            @Override
            public void success(StandardResponse standardResponse, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        callback = (TextFragmentListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.text_fragment_layout, container, false);

        return view;
    }

    public static Fragment newInstance(String uuid) {
        TextFragment fragment = new TextFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UUID_KEY, uuid);

        fragment.setArguments(bundle);

        return fragment;
    }
}
