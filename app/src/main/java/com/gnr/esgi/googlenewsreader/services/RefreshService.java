package com.gnr.esgi.googlenewsreader.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.gnr.esgi.googlenewsreader.helper.ArticleHelper;
import com.gnr.esgi.googlenewsreader.utils.Config;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RefreshService extends Service {
    public static final String NEW_ARTICLES_ARE_READY = "NEW_ARTICLES_ARE_READY";
    static LocalBroadcastManager broadcaster;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(Config.LOG_PREFIX, "launchRefreshService TEST");
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onStart(Intent intent, int startid) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                try {
                    if(Config.DISPLAY_LOG)
                        Log.d(Config.LOG_PREFIX, "refresh service RUN ");

                    // Refresh articles list
                    ArticleHelper.refreshArticles();

                    // Send broadcast to prevent refresh action is running
                    sendBroadcastMessageFromRefreshService(NEW_ARTICLES_ARE_READY);
                } catch (Exception e) {
                    if(Config.DISPLAY_LOG)
                        Log.d("sync", "error : " + e.getMessage());
                }
            }
        }, 0, Config.AUTO_REFRESH_DELAY, TimeUnit.SECONDS);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void sendBroadcastMessageFromRefreshService(final String message) {
        broadcaster.sendBroadcast(new Intent(message));
    }
}
