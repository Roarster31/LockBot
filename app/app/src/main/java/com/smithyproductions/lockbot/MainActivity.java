package com.smithyproductions.lockbot;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.smithyproductions.lockbot.utils.DataManager;


public class MainActivity extends Activity implements RegisterFragment.RegisterFragmentInterface, UnlockFragment.UnlockFragmentListener, TextFragment.TextFragmentListener, PhoneFragment.PhoneFragmentListener {

    DataManager dataManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManager = new DataManager(this);


        Fragment fragment;

        final String uuid = dataManager.getUUID();
        if (uuid == null) {
            fragment = new RegisterFragment();
        } else {
            fragment = UnlockFragment.newInstance(uuid);
        }

        setFragment(fragment, false);


    }

    private void setFragment(Fragment fragment, boolean saveFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.container, fragment);

        if(saveFragment) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void onClientRegistered(String uuid) {
        dataManager.setUUID(uuid);
        setFragment(UnlockFragment.newInstance(uuid), false);
    }

    @Override
    public void onTextClick() {
        setFragment(TextFragment.newInstance(dataManager.getUUID()), true);
    }

    @Override
    public void onVoiceClick() {
        setFragment(PhoneFragment.newInstance(dataManager.getUUID()), true);
    }

    @Override
    protected void onStop() {
        super.onStop();

        dataManager.setTextConfirmed(false);
        dataManager.setVoiceConfirmed(false);
    }

    @Override
    public void onTextConfirmed() {
        getFragmentManager().popBackStack();
        dataManager.setTextConfirmed(true);

        syncUI();
    }

    private void syncUI() {
        if(dataManager.isTextConfirmed() && dataManager.isVoiceConfirmed()){
            setFragment(new SecretFragment(),false);
        }else {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.container);

            if (fragment instanceof UnlockFragment) {
                ((UnlockFragment) fragment).syncUI(dataManager.isTextConfirmed(), dataManager.isVoiceConfirmed());
            }
        }
    }

    @Override
    public void onVoiceConfirmed() {
        getFragmentManager().popBackStack();
        dataManager.setVoiceConfirmed(true);

        syncUI();
    }
}
