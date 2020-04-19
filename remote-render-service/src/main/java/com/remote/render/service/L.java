package com.remote.render.service;

import android.util.Log;

public class L {
    static final String TAG = "RemoteRenderService";

    static public void i(String text){
        Log.i(TAG, text);
    }
    static public void v(String text){
        Log.v(TAG, text);
    }
    static public void d(String text){
        Log.d(TAG, text);
    }
    static public void e(String text){
        Log.e(TAG, text);
    }
}
