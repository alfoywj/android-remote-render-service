package com.remote.render.service;

import android.view.Surface;
interface IServiceInterface {
    void remoteOnStart();
    void remoteOnResume();
    void remoteOnPause();
    void remoteOnStop();
    void remoteSetSurface(in Surface surface);
}
