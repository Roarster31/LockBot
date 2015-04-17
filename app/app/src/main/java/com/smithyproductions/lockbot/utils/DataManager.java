package com.smithyproductions.lockbot.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.smithyproductions.lockbot.MainActivity;

import java.util.prefs.Preferences;

/**
 * Created by rory on 16/04/15.
 */
public class DataManager {

    public static final String UUID_KEY = "UUID_KEY";
    private static final String TEXT_CONFIRMED_KEY = "TEXT_CONFIRMED_KEY";
    private static final String VOICE_CONFIRMED_KEY = "VOICE_CONFIRMED_KEY";
    private final SharedPreferences preferences;

    public DataManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getUUID(){
        return preferences.getString(UUID_KEY,null);
    }

    public void setUUID(String uuid){
        preferences.edit().putString(UUID_KEY, uuid).commit();
    }

    public boolean isTextConfirmed() {
        return preferences.getBoolean(TEXT_CONFIRMED_KEY, false);
    }

    public void setTextConfirmed(boolean confirmed){
        preferences.edit().putBoolean(TEXT_CONFIRMED_KEY, confirmed).commit();
    }

    public boolean isVoiceConfirmed() {
        return preferences.getBoolean(VOICE_CONFIRMED_KEY, false);
    }

    public void setVoiceConfirmed(boolean confirmed){
        preferences.edit().putBoolean(VOICE_CONFIRMED_KEY, confirmed).commit();
    }
}
