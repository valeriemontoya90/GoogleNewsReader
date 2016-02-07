package com.gnr.esgi.googlenewsreader.webservices;

import android.os.AsyncTask;

import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.api.HttpRetriever;
import com.gnr.esgi.googlenewsreader.helper.ArticleHelper;
import com.gnr.esgi.googlenewsreader.models.Article;
import com.gnr.esgi.googlenewsreader.models.Tag;
import com.gnr.esgi.googlenewsreader.factory.ArticleFactory;

public class ArticleSearchTask extends AsyncTask<Tag, Tag, Tag>{

    private Integer page;

    public ArticleSearchTask() {
        page = 0;
    }

    public ArticleSearchTask(Integer page) {
        this.page = page;
    }

    @Override
    protected Tag doInBackground(Tag... params) {
        params[0].setArticles(
            ArticleFactory.fromJson(
                    HttpRetriever.retrieveStream(
                            params.length > 0
                                    ? ArticleHelper.getUrl(params[0].getTagName(), page)
                                    : ArticleHelper.getUrl(page)
                    )));

        return params[0];
    }

    @Override
    protected void onPostExecute(Tag tag) {
        if(tag != null) {
            for (Article article : tag.getArticlesList()) {
                article.setLinkTagName(tag.getTagName());
                ArticleHelper.saveInDataBase(article, tag);
            }
        }
    }
}
