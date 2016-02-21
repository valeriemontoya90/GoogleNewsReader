package com.gnr.esgi.googlenewsreader.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.adapters.ListArticlesAdapter;
import com.gnr.esgi.googlenewsreader.helper.ArticleHelper;
import com.gnr.esgi.googlenewsreader.models.Article;
import java.util.List;

public class DatabaseTask extends AsyncTask<Void, List<Article>, List<Article>>{

    private ListArticlesAdapter adapter;
    private ProgressDialog progressDialog;

    public DatabaseTask(ListArticlesAdapter adapter, ProgressDialog progressDialog) {
        this.adapter = adapter;
        this.progressDialog = progressDialog;
    }

    @Override
    protected List<Article> doInBackground(Void... params) {
        // If user selected a tag show listArticles of tag, else show all listArticles
        List<Article> articles =
                GNRApplication.getUser().getCurrentTag().getName().isEmpty()
                        ? ArticleHelper.getArticles()
                        : ArticleHelper.getArticles(GNRApplication.getUser().getCurrentTag());

        ArticleHelper.sortByDate(articles);

        return articles;
    }

    @Override
    protected void onPostExecute(List<Article> articles) {
        if(!articles.isEmpty()) {
            adapter.swapItems(articles);
        }

        if(progressDialog != null) {
            progressDialog.dismiss();

            progressDialog = null;
        }
    }
}
