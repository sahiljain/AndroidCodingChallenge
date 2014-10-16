package ca.sahiljain.androidcodingchallenge;


/**
 * Created by Sahil Jain on 15/10/2014.
 */
public class IPManager {

    //private static final String PREFERENCES_KEY = "mySharedPrefs";
    //private static final String IP_KEY = "serverIp";
    private static String IP = "192.168.1.131";

    public static String getIP() {
        //SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        //String ip = prefs.getString(IP_KEY, "192.168.1.131");
        return IP;
    }

    public static void setIP(String ip) {
        //SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = prefs.edit();
        //editor.putString(IP_KEY, ip);
        //editor.apply();
        IP = ip;
    }
}
