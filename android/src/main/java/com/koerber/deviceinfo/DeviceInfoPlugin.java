package com.koerber.deviceinfo;

import static android.content.Context.BATTERY_SERVICE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

@CapacitorPlugin(name = "DeviceInfo",
        permissions = {
        @Permission(alias = "location",strings = {Manifest.permission.ACCESS_FINE_LOCATION})
}
)
public class DeviceInfoPlugin extends Plugin {

    private final DeviceInfo implementation = new DeviceInfo();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void getId(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("value", implementation.echo("demo123456"));
        call.resolve(ret);
    }

    @PluginMethod
    public void get(PluginCall call) {
        if (getPermissionState("location") != PermissionState.GRANTED) {
            requestPermissionForAlias("location", call, "locationPermsCallback");
        } else {
            fetchInfo(call);
        }
    }

    @PermissionCallback
    private void locationPermsCallback(PluginCall call){
        if (getPermissionState("location") == PermissionState.GRANTED) {
            fetchInfo(call);
        } else {
            call.reject("Permission is required to fetch info");
        }
    }

    private void fetchInfo(PluginCall call) {
        JSObject ret = new JSObject();
        // get IMEI number
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String android_id =
                        Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                ret.put("serial", android_id);
            } else {
                ret.put("serial", Build.SERIAL);
            }
        } catch (Exception e) {
            Log.i("blb", "Getting device info Error " + e);
        }

        // get battery percentage
        int batteryPercentage = getBatteryPercentage(getContext());
        ret.put("batteryLevel", batteryPercentage);

        // Network status
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected()) {
                    ret.put("networkType", "WiFi");
                    haveConnectedWifi = true;
                }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected()) {
                    ret.put("networkType", "Mobile");
                    haveConnectedMobile = true;
                }
        }
        ret.put("networkConnected", haveConnectedWifi || haveConnectedMobile);

        ret.put("ssid", getCurrentSSID(getContext()));

        ret.put("platform","Mobile");
        ret.put("operatingSystem","Android");
        ret.put("osVersion",Build.VERSION.RELEASE);
        ret.put("model",Build.MODEL);
        ret.put("manufacturer",Build.MANUFACTURER);

        BatteryManager myBatteryManager = (BatteryManager) getContext().getSystemService(BATTERY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ret.put("batteryCharging",myBatteryManager.isCharging());
        }else{
            ret.put("batteryCharging",isPhonePluggedIn(getContext()));
        }

        JSObject results = new JSObject();
        results.put("results", ret);
        call.resolve(results);
    }

    public static boolean isPhonePluggedIn(Context context){
        boolean charging = false;

        final Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean batteryCharge = status==BatteryManager.BATTERY_STATUS_CHARGING;

        int chargePlug = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        if (batteryCharge) charging=true;
        if (usbCharge) charging=true;
        if (acCharge) charging=true;

        return charging;
    }

    public static String getCurrentSSID(final Context context) {

        String ssid = null;/*ww  w.ja v a  2s . c o m*/

        if (isOnWiFi(context)) {
            WifiManager wifi = (WifiManager) context.getSystemService(android.content.Context.WIFI_SERVICE);
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


    public static int getBatteryPercentage(Context context) {

        if (Build.VERSION.SDK_INT >= 21) {

            BatteryManager bm = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
//            }
            bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
            return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        } else {

            IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, iFilter);

            int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
            int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

            double batteryPct = level / (double) scale;

            return (int) (batteryPct * 100);
        }
    }
}
