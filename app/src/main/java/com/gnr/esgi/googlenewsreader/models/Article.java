package com.gnr.esgi.googlenewsreader.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.gnr.esgi.googlenewsreader.utils.Constants;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

public class Article implements Parcelable, Serializable {

    private Integer articleId;
    private Boolean hasAlreadyReadValue;

    @SerializedName(Constants.ARTICLE_KEY_TITLE)
    private String title;

    @SerializedName(Constants.ARTICLE_KEY_CONTENT)
    private String content;

    @SerializedName(Constants.ARTICLE_KEY_CREATED_AT)
    private Date createdAt;

    @SerializedName(Constants.ARTICLE_KEY_PICTURE)
    //private String picture;
    private Picture picture;

    @SerializedName(Constants.ARTICLE_KEY_SOURCE)
    private Source source;

    private int linkTagId;

    public Article() {
        articleId = 0;
        createdAt = new Date();
    }

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
        //dest.writeString(picture);
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public int getLinkTagId() {
        return linkTagId;
    }

    public void setLinkTagId(int tagId) {
        linkTagId = tagId;
    }

    public Boolean getHasAlreadyReadValue() {
        return hasAlreadyReadValue;
    }

    public void setHasAlreadyReadValue(Boolean read) {
        hasAlreadyReadValue = read;
    }
}
