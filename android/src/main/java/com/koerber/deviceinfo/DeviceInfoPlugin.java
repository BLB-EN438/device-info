package com.koerber.deviceinfo;

import static android.content.Context.BATTERY_SERVICE;

import static androidx.core.content.ContextCompat.getSystemService;

import static java.security.AccessController.getContext;

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
    public void serialId(PluginCall call){
        JSObject ret = new JSObject();
        ret.put("value", new SerialNumber().get(getContext().getContentResolver(),getContext()));
        call.resolve(ret);
    }
    @PluginMethod
    public void batteryLevel(PluginCall call){
        JSObject ret = new JSObject();
        ret.put("value", BatteryPercentage.get(getContext()));
        call.resolve(ret);
    }
    @PluginMethod
    public void isBatteryCharging(PluginCall call){
        JSObject ret = new JSObject();
        ret.put("value", BatteryCharging.isCharging(getContext()));
        call.resolve(ret);
    }
    @PluginMethod
    public void manufacturer(PluginCall call){
        JSObject ret = new JSObject();
        ret.put("value", Build.MANUFACTURER);
        call.resolve(ret);
    }
    @PluginMethod
    public void model(PluginCall call){
        JSObject ret = new JSObject();
        ret.put("value", Build.MODEL);
        call.resolve(ret);
    }
    @PluginMethod
    public void operatingSystem(PluginCall call){
        JSObject ret = new JSObject();
        ret.put("value", "Android");
        call.resolve(ret);
    }
    @PluginMethod
    public void osVersion(PluginCall call){
        JSObject ret = new JSObject();
        ret.put("value", Build.VERSION.RELEASE);
        call.resolve(ret);
    }
    @PluginMethod
    public void platform(PluginCall call){
        JSObject ret = new JSObject();
        ret.put("value", "Mobile");
        call.resolve(ret);
    }
    @PluginMethod
    public void networkType(PluginCall call){
        JSObject ret = new JSObject();
        ret.put("value", new Network(getContext()).type());
        call.resolve(ret);
    }
    @PluginMethod
    public void isNetworkConnected(PluginCall call){
        JSObject ret = new JSObject();
        ret.put("value", new Network(getContext()).isConnected());
        call.resolve(ret);
    }

    @PluginMethod
    public void wifiSSID(PluginCall call){
        if (getPermissionState("location") != PermissionState.GRANTED) {
            requestPermissionForAlias("location", call, "locationPermsCallback");
        } else {
            JSObject ret = new JSObject();
            ret.put("value", WiFiName.getCurrentSSID(getContext()));
            call.resolve(ret);
        }

    }
    @PermissionCallback
    private void locationPermsCallback(PluginCall call){
        if (getPermissionState("location") == PermissionState.GRANTED) {
            wifiSSID(call);
        } else {
            call.reject("Permission is required to fetch info");
        }
    }
}
