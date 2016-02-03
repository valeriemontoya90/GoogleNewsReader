package com.gnr.esgi.googlenewsreader.webservices;

import android.os.AsyncTask;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.api.HttpRetriever;
import com.gnr.esgi.googlenewsreader.helper.ArticleHelper;
import com.gnr.esgi.googlenewsreader.models.Tag;
import com.gnr.esgi.googlenewsreader.factory.ArticleFactory;

public class ArticleSearchTask extends AsyncTask<Tag, Tag, Tag>{

    @Override
    protected Tag doInBackground(Tag... params) {
        params[0].setArticles(
            ArticleFactory
                .createArticlesList(
                    HttpRetriever
                        .retrieveStream(
                            ArticleHelper
                                .getUrl(
                                    params[0]
                                        .getTagName()))));
        return params[0];
    }

    @Override
    protected void onPostExecute(Tag tag) {
        GNRApplication.getUser().getData().getTags().add(tag);
        ArticleHelper.saveArticleInDataBase(tag);
    }
}
