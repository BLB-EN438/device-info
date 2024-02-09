package com.koerber.deviceinfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WiFiName {
    public static String getCurrentSSID(Context context) {

        String ssid = null;/*ww  w.ja v a  2s . c o m*/

        if (isOnWiFi(context)) {
            WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(android.content.Context.WIFI_SERVICE);
            ssid = wifi.getConnectionInfo().getSSID();

            if (ssid != null) {
                ssid = ssid.replaceAll("\"", "");
                Log.i("blb", "ssid " + ssid);
            }
        }

        return ssid;
    }
    public static boolean isOnWiFi(final Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni == null || !ni.isConnected() || (ni.getType() != ConnectivityManager.TYPE_WIFI)) {
            return false;
        }

        return true;
    }

}
