package ca.sahiljain.androidcodingchallenge;

import android.content.Context;
import android.content.SharedPreferences;

public class IPManager {

    private static final String PREFERENCES_KEY = "mySharedPrefs";
    private static final String IP_KEY = "serverIp";

    public static String getIP(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        return prefs.getString(IP_KEY, "");
    }

    public static void setIP(String ip, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(IP_KEY, ip);
        editor.apply();
    }
}
