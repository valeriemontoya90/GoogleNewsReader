package com.gnr.esgi.googlenewsreader.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.gnr.esgi.googlenewsreader.constants.ArticleConstants;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

public class Article implements Parcelable, Serializable {

    private Integer _id;
    private Boolean _read;

    @SerializedName(ArticleConstants.KEY_TITLE)
    private String _title;

    @SerializedName(ArticleConstants.KEY_CONTENT)
    private String _content;

    @SerializedName(ArticleConstants.KEY_DATE)
    private Date _date;

    @SerializedName(ArticleConstants.KEY_PICTURE)
    private String _picture;

    @SerializedName(ArticleConstants.KEY_SOURCE)
    private Source _source;

    private String _sourceName;
    private String _sourceUrl;
    private int _tagId;

    public Article() {
        _id = 0;
        _date = new Date();
    }

    protected Article(Parcel in) {
        _id = in.readInt();
        _title = in.readString();
        _content = in.readString();
        _picture = in.readString();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public String getContent() {
        return _content;
    }

    public void setContent(String content) {
        _content = content;
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
        dest.writeInt(_id);
        dest.writeString(_title);
        dest.writeString(_content);
        dest.writeString(_picture);
    }

    public String getPicture() {
        return _picture;
    }

    public void setPicture(String picture) {
        _picture = picture;
    }

    public Date getDate() {
        return _date;
    }

    public void setDate(Date date) {
        _date = date;
    }

    public Source getSource() {
        return _source;
    }

    public void setSource(Source source) {
        _source = source;
    }

    public String getSourceName() {
        return _sourceName;
    }

    public void setSourceName(String source) {
        _sourceName = source;
    }

    public String getSourceUrl() {
        return _sourceUrl;
    }

    public void setSourceUrl(String source) {
        _sourceUrl = source;
    }

    public int getTagId() {
        return _tagId;
    }

    public void setTagId(int tagId) {
        _tagId = tagId;
    }

    public Boolean getRead() {
        return _read;
    }

    public void setRead(Boolean read) {
        _read = read;
    }
}
