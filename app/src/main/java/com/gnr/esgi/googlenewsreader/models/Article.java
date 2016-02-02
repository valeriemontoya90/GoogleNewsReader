package com.gnr.esgi.googlenewsreader.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.gnr.esgi.googlenewsreader.utils.Config;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Article implements Parcelable, Serializable {

    private Integer articleId;
    private Boolean hasAlreadyReadValue;

    private String title;

    @SerializedName(Config.ARTICLE_KEY_CONTENT)
    private String content;

    @SerializedName(Config.ARTICLE_KEY_CREATED_AT)
    private String createdAt;

    @SerializedName(Config.ARTICLE_KEY_PICTURE)
    private Picture picture;
    private String pictureUrl;

    @SerializedName(Config.ARTICLE_KEY_SOURCE_NAME)
    private Source source;
    private String sourceName;
    private String sourceUrl;

    private String linkTagName;

    protected Article(Parcel in) {
        articleId = in.readInt();
        title = in.readString();
        content = in.readString();
        //picture = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(articleId);
        dest.writeString(title);
        dest.writeString(content);
    }

    public Article(JSONObject jsonArticle){
        int Id = 0;
        String jTitle = "";
        String jContent = "";
        String jCreatedAt = "";
        String jSourceUrl = "";
        String jSourceName = "";
        String jImageUrl = "";

        try {
            jTitle = jsonArticle.getString(Config.ARTICLE_KEY_TITLE);
            jContent = jsonArticle.getString(Config.ARTICLE_KEY_CONTENT);
            jCreatedAt = jsonArticle.getString(Config.ARTICLE_KEY_CREATED_AT);
            jSourceName = jsonArticle.getString(Config.ARTICLE_KEY_SOURCE_NAME);
            jSourceUrl = jsonArticle.getString(Config.ARTICLE_KEY_SOURCE_URL);
            JSONObject image = jsonArticle.getJSONObject(Config.ARTICLE_KEY_PICTURE);
            jImageUrl = image.getString(Config.ARTICLE_KEY_PICTURE_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.title = jTitle;
        this.content = jContent;
        this.createdAt = jCreatedAt;
        this.sourceUrl = jSourceUrl;
        this.sourceName = jSourceName;
        this.pictureUrl = jImageUrl;

        Log.d(Config.LOG_PREFIX, "New Article(JSONObject c):" + this.toString());
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer id) {
        articleId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getLinkTagName() {
        return linkTagName;
    }

    public void setLinkTagName(String tagName) {
        linkTagName = tagName;
    }

    public Boolean getHasAlreadyReadValue() {
        return hasAlreadyReadValue;
    }

    public void setHasAlreadyReadValue(Boolean read) {
        hasAlreadyReadValue = read;
    }
}
