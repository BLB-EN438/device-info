package com.koerber.deviceinfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {
    // Network status
    private boolean haveConnectedWifi = false;
    private boolean haveConnectedMobile = false;
    private String networkType="";
    Network(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnectedOrConnecting()) {
                    haveConnectedWifi = true;
                    networkType="WiFi";
                }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnectedOrConnecting()) {
                    haveConnectedMobile = true;
                    networkType="MOBILE";
                }
        }
    }
    public String type(){
        return networkType;
    }

    public boolean isConnected(){
        return haveConnectedWifi || haveConnectedMobile;
    }
}
