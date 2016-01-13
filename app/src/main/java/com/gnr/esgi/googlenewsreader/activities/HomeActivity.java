package com.gnr.esgi.googlenewsreader.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.adapter.ListArticlesAdapter;
import com.gnr.esgi.googlenewsreader.constants.ArticleConstants;
import com.gnr.esgi.googlenewsreader.helper.ArticleHelper;
import com.gnr.esgi.googlenewsreader.listener.CancelTaskOnListener;
import com.gnr.esgi.googlenewsreader.model.Tag;
import com.gnr.esgi.googlenewsreader.parser.JsonParser;
import com.gnr.esgi.googlenewsreader.api.HttpRetriever;
import com.gnr.esgi.googlenewsreader.services.RefreshService;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends ActionBarActivity {

    Boolean isRefresh;

    ListView listview;
    ListArticlesAdapter adapter;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    RefreshService.RefreshBinder binder;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (RefreshService.RefreshBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        refresh();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button_refresh);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = refresh();

                    final Snackbar snackbar = Snackbar.make(view, count + R.string.snackbar_addedNews, Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.snackbar_close, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });

                    snackbar.show();
            }
        });
    }

    private void applyAdapter() {
        adapter = new ListArticlesAdapter(this,
                        GNRApplication
                            .getUser()
                            .getData()
                            .getAllArticles());

        listview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        listview = (ListView) findViewById(R.id.news_list);

        applyAdapter();

        // Click event on news list item
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showNewsOverview(position);
            }
        });
    }

    private int refresh() {
        GNRApplication.getUser().refreshData();

        return performSearch();
    }

    private int performSearch() {
        progressDialog = ProgressDialog.show(HomeActivity.this,
                "Please wait...", "Retrieving data...", true, true);

        progressDialog.setOnCancelListener(
                new CancelTaskOnListener(
                        new ArticlesSearchTask()
                                .execute(GNRApplication
                                        .getUser()
                                        .getData()
                                        .getTags())));

        return GNRApplication.getUser().getData().countLatest();
    }

    private void showNewsOverview(Integer id) {
        Intent intent = new Intent(this, DetailArticleActivity.class)
            //.putExtra(ArticleConstants.KEY_ARTICLE, (Parcelable) GNRApplication.getUser().getData().findArticleById(id))
                .putExtra(ArticleConstants.KEY_ID, id);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void launchRefreshService() {
        Intent intent = new Intent(this, RefreshService.class);

        startService(intent);

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void stopRefreshService() {
        Intent intent = new Intent(this, RefreshService.class);

        stopService(intent);

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void showRefreshDialog() {
        final CharSequence[] items = { "Refresh every hour" };

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
            .setTitle(R.string.refreshDialog_title)
            .setCancelable(false)
            .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    isRefresh = isChecked;
                }
            })
            .setPositiveButton(R.string.refreshDialog_accept, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(isRefresh)
                        launchRefreshService();
                    else
                        stopRefreshService();

                    dialog.dismiss();
                }
            })
            .setNegativeButton(R.string.refreshDialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // In case of user click on Refresh icon of toolbar
                // Show alert dialog with option to activate auto refresh
                // Then add a service with a 1hour refreshing thread
                showRefreshDialog();
                return true;

            case R.id.action_tags:
                // In case of user click on Tags icon of toolbar
                // Eg.
                //Intent intent = new Intent(this, TagListActivity.class);
                //startActivity(intent);
                return true;

            case R.id.action_search:
                // In case of user click on Search icon of toolbar
                // Eg.
                //Intent intent = new Intent(this, TagSettingsActivity.class);
                //startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ArticlesSearchTask extends AsyncTask<List<Tag>, Void, List<Tag>> {

        @Override
        protected List<Tag> doInBackground(List<Tag>... params) {
            for(Tag tag : params[0]) {
                InputStream source = HttpRetriever
                                        .retrieveStream(ArticleHelper
                                                .getUrl(tag
                                                        .getName()));

                Gson gson = new Gson();

                Reader reader = new InputStreamReader(source);

                Map<String, Object> response = new HashMap<>();
                response = (Map<String, Object>) gson.fromJson(reader, response.getClass());

                tag.setArticles(JsonParser.parse((ArrayList<LinkedTreeMap<String, Object>>)
                        ((LinkedTreeMap<String, Object>)
                                response
                                        .get("responseData"))
                                .get("results")));

                tag.setArticles(JsonParser.parse((ArrayList<LinkedTreeMap<String, Object>>) ((LinkedTreeMap<String, Object>) response.get("responseData")).get("results")));

                saveArticlesInDB(tag);
            }

            return params[0];
        }

        @Override
        protected void onPostExecute(final List<Tag> result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }

                    if(result != null)
                        GNRApplication.getUser().getData().setTags(result);

                    applyAdapter();
                }
            });
        }
    }

    public void saveArticlesInDB(Tag tag) {
        for (int i=0; i<tag.getArticles().size(); i++) {
            GNRApplication.getGnrDBHelper().addArticle(tag.getArticles().get(i));
        }
    }
}
