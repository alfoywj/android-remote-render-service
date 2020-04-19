package com.remote.render.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Surface;

public class RemoteRenderService extends Service
{
    static {
        System.loadLibrary("nativeegl");
    }

    @Override
    public void onCreate() {
        L.i("RemoteRenderService onCreate");
        super.onCreate();
        startForeground();
    }

    private void startForeground(){
        String CHANNEL_ID = "mych";
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "SnowDeer Service Channel",  NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
        L.i("createNotificationChannel");
        Notification.Builder mBuilder = new Notification.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("sdf")
                .setContentText("sdfsdf");
        startForeground(1, mBuilder.build());
        L.i("startForeground");
    }

    @Override
    public void onDestroy() {
        L.i("RemoteRenderService onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iServiceInterface.asBinder();
    }

    private IServiceInterface iServiceInterface = new IServiceInterface.Stub() {
        @Override
        public void remoteOnStart() throws RemoteException {
            L.i("RemoteRenderService remoteOnStart");
            nativeOnStart();
        }

        @Override
        public void remoteOnResume() throws RemoteException {
            L.i("RemoteRenderService remoteOnResume");
            nativeOnResume();
        }

        @Override
        public void remoteOnPause() throws RemoteException {
            L.i("RemoteRenderService remoteOnPause");
            nativeOnPause();
        }

        @Override
        public void remoteOnStop() throws RemoteException {
            L.i("RemoteRenderService remoteOnStop");
            nativeOnStop();
        }

        @Override
        public void remoteSetSurface(Surface surface) throws RemoteException {
            L.i("RemoteRenderService remoteSetSurface");
            nativeSetSurface(surface);
        }
    };

    public static native void nativeOnStart();
    public static native void nativeOnResume();
    public static native void nativeOnPause();
    public static native void nativeOnStop();
    public static native void nativeSetSurface(Surface surface);
}
