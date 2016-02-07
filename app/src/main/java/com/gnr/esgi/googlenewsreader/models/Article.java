package com.gnr.esgi.googlenewsreader.models;

import android.database.Cursor;
import android.util.Log;
import com.gnr.esgi.googlenewsreader.constants.ArticleConstants;
import com.gnr.esgi.googlenewsreader.constants.DatabaseConstants;
import com.gnr.esgi.googlenewsreader.utils.Config;
import org.json.JSONException;
import org.json.JSONObject;

public class Article {

    private Long articleId;
    private Boolean read;
    private Boolean deleted;

    private String title;
    private String content;
    private String createdAt;
    private Picture picture;
    private Source source;
    private String linkTagName;

    public Article(JSONObject jsonArticle){
        try {
            title = jsonArticle.getString(ArticleConstants.ARTICLE_KEY_TITLE);
            content = jsonArticle.getString(ArticleConstants.ARTICLE_KEY_CONTENT);

            createdAt = jsonArticle.getString(ArticleConstants.ARTICLE_KEY_CREATED_AT);

            source = new Source();
            source.setSourceName(jsonArticle.getString(ArticleConstants.ARTICLE_KEY_SOURCE_NAME));
            source.setSourceUrl(jsonArticle.getString(ArticleConstants.ARTICLE_KEY_SOURCE_URL));

            picture = new Picture();
            if(jsonArticle.has(ArticleConstants.ARTICLE_KEY_PICTURE))
            {
                JSONObject image = jsonArticle.getJSONObject(ArticleConstants.ARTICLE_KEY_PICTURE);
                picture.setPictureUrl(image.getString(ArticleConstants.ARTICLE_KEY_PICTURE_URL));
            }

            read = false;
            deleted = false;
        }
        catch (JSONException e) {
            if(Config.DISPLAY_LOG)
                Log.d("Article", e.getMessage());
        }

        if(Config.DISPLAY_LOG)
            Log.d(ArticleConstants.TAG, "New Article(JSONObject c):" + this.toString());
    }

    public Article(Cursor cursor) {
        articleId = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.ArticleEntry._ID));
        title = cursor.getString(cursor.getColumnIndex(DatabaseConstants.ArticleEntry.COLUMN_TITLE));
        content = cursor.getString(cursor.getColumnIndex(DatabaseConstants.ArticleEntry.COLUMN_CONTENT));
        createdAt = cursor.getString(cursor.getColumnIndex(DatabaseConstants.ArticleEntry.COLUMN_DATE));

        source = new Source();
        source.setSourceName(cursor.getString(cursor.getColumnIndex(DatabaseConstants.ArticleEntry.COLUMN_SOURCE_NAME)));
        source.setSourceUrl(cursor.getString(cursor.getColumnIndex(DatabaseConstants.ArticleEntry.COLUMN_SOURCE_URL)));

        picture = new Picture(cursor.getString(cursor.getColumnIndex(DatabaseConstants.ArticleEntry.COLUMN_PICTURE_URL)));

        read = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.ArticleEntry.COLUMN_READ)) == 1;
        deleted = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.ArticleEntry.COLUMN_DELETED)) == 1;

        linkTagName = cursor.getString(cursor.getColumnIndex(DatabaseConstants.ArticleEntry.COLUMN_TAG_NAME));
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long id) {
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

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getLinkTagName() {
        return linkTagName;
    }

    public void setLinkTagName(String tagName) {
        linkTagName = tagName;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
