package com.gnr.esgi.googlenewsreader.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.gnr.esgi.googlenewsreader.constants.NewsConstants;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

public class News implements Parcelable, Serializable {

    private Integer _id;
    private Boolean _read;

    @SerializedName(NewsConstants.KEY_TITLE)
    private String _title;

    @SerializedName(NewsConstants.KEY_CONTENT)
    private String _content;

    @SerializedName(NewsConstants.KEY_DATE)
    private Date _date;

    @SerializedName(NewsConstants.KEY_PICTURE)
    private String _picture;

    @SerializedName(NewsConstants.KEY_SOURCE)
    private Source _source;

    public News() {
        _id = 0;
        _title = new String();
        _date = new Date();
        _content = new String();
        _source = new Source();
    }

    protected News(Parcel in) {
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

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
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

    public Boolean getRead() {
        return _read;
    }

    public void setRead(Boolean read) {
        _read = read;
    }
}
