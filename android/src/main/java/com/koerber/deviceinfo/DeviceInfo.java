package com.koerber.deviceinfo;

import android.util.Log;

public class DeviceInfo {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }

    // public Object get(Object object){
    //     Log.i("get", object.toString());
    //     return  object;
    // }
}
