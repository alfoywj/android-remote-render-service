package com.remote.render.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Surface;
import android.widget.Toast;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;

import com.remote.render.service.IServiceInterface;


public class RemoteClient extends Activity implements SurfaceHolder.Callback
{
    Surface surface = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i( "onCreate()");
        
        setContentView(R.layout.main);
        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surfaceview);
        surfaceView.getHolder().addCallback(this);

        Intent intent = new Intent();
        intent.setClassName("com.remote.render.service", "com.remote.render.service.RemoteRenderService");
        startForegroundService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }
    IServiceInterface iServiceInterface = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iServiceInterface =  IServiceInterface.Stub.asInterface(service);
            try {
                iServiceInterface.remoteOnStart();
                iServiceInterface.remoteOnResume();

                if(surface != null)
                    iServiceInterface.remoteSetSurface(surface);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        L.i("onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.i( "onResume()");
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        L.i( "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.i("onStop()");
        try {
            if(iServiceInterface != null){
                iServiceInterface.remoteOnPause();
                iServiceInterface.remoteOnStop();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setClassName("com.remote.render.service", "com.remote.render.service.RemoteRenderService");
        stopService(intent);
        unbindService(serviceConnection);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        surface = holder.getSurface();
        try {
            if(iServiceInterface != null){
                iServiceInterface.remoteSetSurface(surface);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
       // remoteSetSurface(null);
    }
}
