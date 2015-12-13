package com.gnr.esgi.googlenewsreader.model;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ismail on 13-12-2015.
 */
public class Source {
    private Integer _id;
    private String _name;
    private URL _url;

    public Source() throws MalformedURLException {
        _id = 2;
        _name = "ESGI";
        _url = new URL("http://esgi.fr");
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

    public URL getUrl() {
        return _url;
    }

    public void setUrl(URL url) {
        _url = url;
    }
}
