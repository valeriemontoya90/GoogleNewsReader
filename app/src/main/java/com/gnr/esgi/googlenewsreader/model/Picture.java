package com.gnr.esgi.googlenewsreader.model;

import android.graphics.Bitmap;

public class Picture {
    private String _url;
    private Bitmap _bitmap;

    public Picture() {
    }

    public Picture(String url) {
        _url = url;
    }

    public String getUrl() {
        return _url;
    }

    public void setUrl(String url) {
        _url = url;
    }

    public Bitmap getBitmap() {
        return _bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        _bitmap = bitmap;
    }
}
