package com.gnr.esgi.googlenewsreader.services;

import java.io.*;
import org.apache.http.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.gnr.esgi.googlenewsreader.io.FlushedInputStream;

public class HttpRetriever
{
    public static InputStream retrieveStream(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if(statusCode != HttpStatus.SC_OK) {
                Log.w("HttpRetriever",
                        "Error " + statusCode + " for URL " + url);
                return null;
            }

            return getResponse.getEntity().getContent();
        }
        catch(IOException e) {
            getRequest.abort();
            Log.w("HttpRetriever",
                    "Error for URL " + url, e);
        }

        return null;
    }

    public Bitmap retrieveBitmap(String url) throws Exception {
        InputStream inputStream = null;

        try {
            inputStream = retrieveStream(url);
            final Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(inputStream));

            return bitmap;
        }
        finally {
            if(inputStream != null) {
                inputStream.close();
            }
        }
    }
}
