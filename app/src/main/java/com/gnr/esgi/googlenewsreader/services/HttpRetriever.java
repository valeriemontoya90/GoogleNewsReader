package com.gnr.esgi.googlenewsreader.services;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.gnr.esgi.googlenewsreader.io.FlushedInputStream;

public class HttpRetriever
{
    public static InputStream retrieveStream(String str) {
        try{
            URL url = new URL(str);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            return connection.getInputStream();

        } catch (IOException e) {
            Log.w("HttpRetriever",
                    "Error for URL " + str, e);
        }

        return null;
    }

    public Bitmap retrieveBitmap(String url) throws Exception {
        InputStream inputStream = null;

        try {
            //inputStream = retrieveStream(url);
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
