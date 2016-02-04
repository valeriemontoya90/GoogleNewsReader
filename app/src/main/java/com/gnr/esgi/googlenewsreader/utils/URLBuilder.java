package com.gnr.esgi.googlenewsreader.utils;

import com.gnr.esgi.googlenewsreader.parser.HtmlParser;

public class URLBuilder {
    private String url;

    public URLBuilder(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void addParameter(String key, String value) {
        StringBuilder builder = new StringBuilder();

        builder.append(key);
        builder.append("=");
        builder.append(value);
        builder.append("&");

        url = builder.toString();
        clean();
    }

    private void clean() {
        url = HtmlParser.escapeSpace(url);

        if(url.endsWith("&"))
            url = url.substring(0, url.lastIndexOf("&"));
    }
}
