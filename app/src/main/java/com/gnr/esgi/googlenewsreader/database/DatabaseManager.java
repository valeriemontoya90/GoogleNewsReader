package com.gnr.esgi.googlenewsreader.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.gnr.esgi.googlenewsreader.helper.ArticleHelper;
import com.gnr.esgi.googlenewsreader.io.FlushedInputStream;
import com.gnr.esgi.googlenewsreader.models.Article;
import com.gnr.esgi.googlenewsreader.models.Picture;
import com.gnr.esgi.googlenewsreader.models.Tag;
import com.gnr.esgi.googlenewsreader.services.HttpRetriever;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DatabaseManager {
    private List<Tag> _tags;
    private HttpRetriever httpRetriever = new HttpRetriever();

    public DatabaseManager() {
        _tags = new ArrayList<>();

        retrieveTags();
    }

    public List<Tag> getTags() {
        return _tags;
    }

    public void setTags(List<Tag> tags) {
        for (Tag newTag : tags)
        {
            for(Tag oldTag : _tags)
                newTag.setCounter(oldTag.getName().compareTo(newTag.getName()) == 0
                                ? ArticleHelper.countRecentNews(newTag.getArticles(), oldTag.getArticles())
                                : newTag.getArticles().size()
                );

            for(Article article : newTag.getArticles()) {
                if(article.getPicture() != null
                    && article.getPicture().getUrl() != null)
                {
                    Bitmap bitmap = fetchBitmapFromCache(article.getPicture().getUrl());

                    if(bitmap == null) {
                        new BitmapDownloaderTask(null).execute(article.getPicture().getUrl());
                    }
                    else
                        article.getPicture().setBitmap(bitmap);
                }
                else
                {
                    article.setPicture(new Picture());
                }
            }
        }

        _tags = tags;
    }

    private void retrieveTags() {
        /*
            Retrieve tags from SQLLite Database
            Add each of them to _tags
         */

        // FOR TEST
        _tags.add(new Tag("apple"));
        _tags.add(new Tag("PSG"));
        _tags.add(new Tag("Inde"));
    }

    public List<Article> findArticlesByTagId(Integer id) {
        List<Article> articleList = new ArrayList<>();

        for (Tag _tag : _tags)
            if(_tag.getId().compareTo(id) == 0)
                articleList = _tag.getArticles();

        return articleList;
    }

    public List<Article> getAllArticles() {
        List<Article> articleList = new ArrayList<>();

        for(Tag tag : _tags)
            articleList.addAll(tag.getArticles());

        return setIndex(escapeDuplicates(articleList));
    }

    public int countLatest() {
        int count = 0;

        for (Tag tag : _tags)
            count += tag.getCounter();

        return count;
    }

    public int countLatest(Tag tag) {
        return tag.getCounter();
    }

    private List<Article> setIndex(List<Article> articleList) {
        for(int i=0; i< articleList.size(); i++)
            articleList.get(i).setArticleId(i);

        return articleList;
    }

    private List<Article> escapeDuplicates(List<Article> articleList) {
        Set<Article> articleSet = new LinkedHashSet<>(articleList);

        articleList.clear();
        articleList.addAll(articleSet);

        return articleList;
    }

    public Article findArticleById(Integer id) {
        for(Article article : getAllArticles())
            if(article.getArticleId().compareTo(id) == 0)
                return article;

        return null;
    }

    private LinkedHashMap<String, Bitmap> bitmapCache = new LinkedHashMap<String, Bitmap>();

    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (bitmapCache) {
                bitmapCache.put(url, bitmap);
            }
        }
    }

    private Bitmap fetchBitmapFromCache(String url){
        synchronized (bitmapCache) {
            final Bitmap bitmap = bitmapCache.get(url);
            if (bitmap != null) {
                // Bitmap found in cache
                // Move element to first position, so that it is removed last
                bitmapCache.remove(url);
                bitmapCache.put(url, bitmap);
                return bitmap;
            }
        }
        return null;
    }

    private class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

        private String url;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView){
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            url = strings[0];
            InputStream inputStream = httpRetriever.retrieveStream(url);
            if(inputStream == null)
                return null;
            return BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(isCancelled())
                bitmap = null;
            addBitmapToCache(url, bitmap);
            if(imageViewReference != null){
                ImageView imageView = imageViewReference.get();
                if(imageView != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}