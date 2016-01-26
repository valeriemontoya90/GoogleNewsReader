package com.gnr.esgi.googlenewsreader.Webservices;

import android.util.Log;

import com.gnr.esgi.googlenewsreader.utils.Config;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

public class Webservices {
    private static AsyncHttpClient client = new SyncHttpClient();

    public Webservices() {
    }

    //get requests
    public static void getArticlesByTag(String tag, AsyncHttpResponseHandler responseHandler) {
        if (Config.DISPLAY_LOG) {
            Log.i(Config.LOG_PREFIX, "request discover movies send " + Config.BASE_API_URL + "/"+tag);
        }
        client.get(Config.BASE_API_URL + "/"+tag, responseHandler);
    }
}
