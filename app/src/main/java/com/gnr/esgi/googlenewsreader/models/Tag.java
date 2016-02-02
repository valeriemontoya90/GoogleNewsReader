package com.gnr.esgi.googlenewsreader.models;

import java.util.ArrayList;
import java.util.List;

public class Tag {
    private Integer tagId;
    private String tagName;
    private List<Article> articlesList;
    private int currentCounter;

    public Tag(String name) {
        tagId = 0;
        tagName = name;
        articlesList = new ArrayList<>();
        currentCounter = 0;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer id) {
        tagId = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String name) {
        tagName = name;
    }

    public List<Article> getArticlesList() {
        return articlesList;
    }

    public void setArticles(List<Article> articles) {
        articlesList = articles;
    }

    public int getCurrentCounter() {
        return currentCounter;
    }

    public void setCurrentCounter(int count) {
        currentCounter = count;
    }
}
