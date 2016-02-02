package com.gnr.esgi.googlenewsreader.model;

public class Source {
    private Integer _id;
    private String _name;
    private String _url;

    public Source() {
        _id = 0;
        _name = new String();
        _url = new String();
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
