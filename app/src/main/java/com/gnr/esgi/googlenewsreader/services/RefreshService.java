package com.gnr.esgi.googlenewsreader.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.Webservices.Parser;
import com.gnr.esgi.googlenewsreader.Webservices.Webservices;
import com.gnr.esgi.googlenewsreader.models.Article;
import com.gnr.esgi.googlenewsreader.models.Tag;
import com.gnr.esgi.googlenewsreader.utils.Config;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
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
                    Log.d(Config.LOG_PREFIX, "refresh service RUN ");
                    refreshData();
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

    private void refreshData() {
        ArrayList<Tag> tags = new ArrayList<>();
        //get all real tags from database
        tags.add(new Tag("apple"));
        tags.add(new Tag("PSG"));
        tags.add(new Tag("Inde"));

        for (final Tag tag : tags) {
            Webservices.getArticlesByTag(tag.getTagName(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    try {
                        Log.i(Config.LOG_PREFIX, "Webservice Response : " + new String(bytes));

                        List<Article> parsedArticles = Parser.parseResultPage(new String(bytes));
                        saveArticleInDataBase(parsedArticles, tag);
                        sendBroadcastMessageFromRefreshService(NEW_ARTICLES_ARE_READY);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.e(Config.LOG_PREFIX, "Webservice Failed Response :" + new String(bytes));
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void sendBroadcastMessageFromRefreshService(final String message) {
        final Intent intent = new Intent(message);
        broadcaster.sendBroadcast(intent);
    }

    public void saveArticleInDataBase(List<Article> parsedArticles, Tag tag) {
        Log.d(Config.LOG_PREFIX, "saveArticleInDataBase");
        for (int i=0; i<parsedArticles.size(); i++) {
            Article article = parsedArticles.get(i);
            article.setLinkTagName(tag.getTagName());
            article.setHasAlreadyReadValue(false);
            GNRApplication.getGnrDBHelper().addArticle(article);
        }
    }
}
