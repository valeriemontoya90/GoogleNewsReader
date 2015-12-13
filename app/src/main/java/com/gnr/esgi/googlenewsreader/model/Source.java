package com.gnr.esgi.googlenewsreader.model;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ismail on 13-12-2015.
 */
public class Source {
    private Integer _id;
    private String _name;
    private String _url;

    public Source() {
        _id = 2;
        _name = "ESGI";
        _url = "http://esgi.fr";
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getUrl() {
        return _url;
    }

    public void setUrl(String url) {
        _url = url;
    }
}
