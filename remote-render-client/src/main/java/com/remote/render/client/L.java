package com.remote.render.client;

import android.util.Log;

public class L {
    static final String TAG = "RemoteRenderClient";

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
