package com.gnr.esgi.googlenewsreader.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class RefreshService extends Service {

    RefreshBinder binder;

    public class RefreshBinder extends Binder {
        public RefreshService getService() {
            return RefreshService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if(binder == null)
            binder = new RefreshBinder();

        return binder;
    }

    @Override
    public void onCreate() {
        Log.d(this.getClass().getSimpleName(), "Service started");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(this.getClass().getSimpleName(), "Service stopped");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                     Get User instance from Extras
                     Check if autoUpdate is true
                         Obtain DatabaseManager from User
                        re instance DatabaseManager each 1hour
                      */
                try {
                    Thread.sleep(600000);
                }
                catch (InterruptedException e) {
                    Log.w(this.getClass().getSimpleName(), "Error while auto refreshing.");
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }
}