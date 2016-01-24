package com.gnr.esgi.googlenewsreader.models;

public class Source {
    private Integer sourceId;
    private String sourceName;
    private String sourceUrl;

    public Source() {
        sourceId = 0;
        sourceName = new String();
        sourceUrl = new String();
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer id) {
        sourceId = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String name) {
        sourceName = name;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String url) {
        sourceUrl = url;
    }
}
