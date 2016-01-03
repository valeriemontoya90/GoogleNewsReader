package com.gnr.esgi.googlenewsreader.database;

import java.io.*;
import org.apache.http.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.util.Log;

public class BaseProxy
{
    public static InputStream retrieveStream(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if(statusCode != HttpStatus.SC_OK) {
                Log.w("BaseProxy",
                        "Error " + statusCode + " for URL " + url);
                return null;
            }

            return getResponse.getEntity().getContent();
        }
        catch(IOException e) {
            getRequest.abort();
            Log.w("BaseProxy",
                    "Error for URL " + url, e);
        }

        return null;
    }
}
