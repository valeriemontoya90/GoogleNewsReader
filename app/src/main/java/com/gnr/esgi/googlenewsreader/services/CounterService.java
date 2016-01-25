package com.gnr.esgi.googlenewsreader.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.gnr.esgi.googlenewsreader.utils.Config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CounterService extends Service {
    public static final String DO_A_REFRESH = "DO_A_REFRESH";
    static LocalBroadcastManager broadcaster;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(this.getClass().getSimpleName(), "Service started");
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onStart(Intent intent, int startid) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                try {
                    broadcastMessageFromCounterService(DO_A_REFRESH);
                    Log.d(Config.LOG_PREFIX, "refresh service RUN ");
                } catch (Exception e) {
                    Log.d("sync", "error : " + e.getMessage());
                }
            }
        }, 0, Config.GET_ARTICLES_FROM_API, TimeUnit.SECONDS);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void broadcastMessageFromCounterService(final String message) {
        Log.d(Config.LOG_PREFIX, "broadcastMessageFromCounterService DO_A_REFRESH ");
        final Intent intent = new Intent(message);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
