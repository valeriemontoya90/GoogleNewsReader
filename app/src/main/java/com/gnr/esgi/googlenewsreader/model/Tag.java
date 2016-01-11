package com.gnr.esgi.googlenewsreader.model;

import java.util.ArrayList;
import java.util.List;

public class Tag {
    private Integer _id;
    private String _name;
    private List<Article> _articles;
    private int _recentCounter;

    public Tag(String name) {
        _id = 0;
        _name = name;
        _articles = new ArrayList<>();
        _recentCounter = 0;
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

    public List<Article> getArticles() {
        return _articles;
    }

    public void setArticles(List<Article> articles) {
        _articles = articles;
    }

    public int getCounter() {
        return _recentCounter;
    }

    public void setCounter(int count) {
        _recentCounter = count;
    }
}
