package com.smithyproductions.lockbot;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class UnlockFragment extends Fragment {

    private UnlockFragmentListener callback;
    private Button textButton;
    private Button voiceButton;


    public interface UnlockFragmentListener {
        void onTextClick();
        void onVoiceClick();
    }

    private static final String UUID_KEY = "UUID_KEY";
    private String uuid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uuid = getArguments().getString(UUID_KEY);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        callback = (UnlockFragmentListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.unlock_fragment_layout, container, false);

        TextView uuidTextView = (TextView) view.findViewById(R.id.uuid);

        uuidTextView.setText(uuid);

        textButton = (Button) view.findViewById(R.id.num_confirm_btn);
        voiceButton = (Button) view.findViewById(R.id.voice_confirm_btn);

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onTextClick();
            }
        });

        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onVoiceClick();
            }
        });

        return view;
    }

    public void syncUI(boolean textConfirmed, boolean voiceConfirmed) {
        textButton.setEnabled(!textConfirmed);
        voiceButton.setEnabled(!voiceConfirmed);
    }

    public static Fragment newInstance(String uuid) {
        UnlockFragment fragment = new UnlockFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UUID_KEY, uuid);

        fragment.setArguments(bundle);

        return fragment;
    }
}
