package com.gnr.esgi.googlenewsreader.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.activities.HomeActivity;
import com.gnr.esgi.googlenewsreader.adapters.ListArticlesAdapter;
import com.gnr.esgi.googlenewsreader.constants.TagConstants;
import com.gnr.esgi.googlenewsreader.helper.ArticleHelper;
import com.gnr.esgi.googlenewsreader.models.Article;
import java.util.List;

public class DatabaseTask extends AsyncTask<Void, List<Article>, List<Article>>{

    private ListArticlesAdapter adapter;
    private ProgressDialog progressDialog;

    public DatabaseTask(ListArticlesAdapter adapter,
                        ProgressDialog progressDialog) {
        this.adapter = adapter;
        this.progressDialog = progressDialog;
    }

    @Override
    protected List<Article> doInBackground(Void... params) {
        List<Article> articles = ArticleHelper.getArticles();

        ArticleHelper.sortByDate(articles);

        return articles;
    }

    @Override
    protected void onPostExecute(List<Article> articles) {
        if(!articles.isEmpty()) {
            HomeActivity.articlesList = articles;
            adapter.swapItems(articles);
        }

        if(progressDialog != null) {
            progressDialog.dismiss();

            progressDialog = null;
        }
    }
}
