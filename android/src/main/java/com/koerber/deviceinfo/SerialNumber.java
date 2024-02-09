package com.koerber.deviceinfo;

import static java.security.AccessController.getContext;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SerialNumber {
    public String get(ContentResolver contentResolver, Context context){
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                String android_id =
                        Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
                return android_id;
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                String android_id =
                        Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
                return android_id;
            }
            return Build.SERIAL;
        }catch (Exception e){
            Log.i("blb",e.toString());
            //throw e;
            return e.toString();
        }
    }
}
