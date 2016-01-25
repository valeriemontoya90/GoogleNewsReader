package com.gnr.esgi.googlenewsreader.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.Webservices.Parser;
import com.gnr.esgi.googlenewsreader.Webservices.Webservices;
import com.gnr.esgi.googlenewsreader.adapters.ListArticlesAdapter;
import com.gnr.esgi.googlenewsreader.models.Article;
import com.gnr.esgi.googlenewsreader.models.Tag;
import com.gnr.esgi.googlenewsreader.services.CounterService;
import com.gnr.esgi.googlenewsreader.utils.Config;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends ActionBarActivity {

    public static final String ACTIVATE_AUTO_REFRESH = "activate_auto_refresh";
    public static final String DESACTIVATE_AUTO_REFRESH = "desactivate_auto_refresh";

    Boolean isRefresh = false;

    ArrayList<Article> articlesArrayList = new ArrayList<>();
    ListView listviewArticles;
    ListArticlesAdapter listArticlesAdapter;
    ProgressDialog progressDialog;
    static LocalBroadcastManager broadcaster;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //refresh();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button_refresh);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*int count = refresh();

                final Snackbar snackbar = Snackbar.make(view, count + R.string.snackbar_addedNews, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.snackbar_close, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

                snackbar.show();
                */
            }
        });

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        listviewArticles = (ListView) findViewById(R.id.news_list);
        listArticlesAdapter = new ListArticlesAdapter(getApplicationContext(), articlesArrayList);
        listviewArticles.setAdapter(listArticlesAdapter);
        listviewArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendDataToDetailArticleActivity(position);
            }
        });

        callWebServices();
        initServices();
    }

    private void callWebServices() {
        ArrayList<Tag> tags = new ArrayList<>();
        // FOR TEST
        tags.add(new Tag("apple"));
        tags.add(new Tag("PSG"));
        tags.add(new Tag("Inde"));

        for (Tag tag : tags) {
            Webservices.getArticlesByTag(tag.getTagName(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.i(Config.LOG_PREFIX, "Webservice Response : " + new String(bytes));

                    List<Article> parsedArticles = Parser.parseResultPage(new String(bytes));
                    articlesArrayList.addAll(parsedArticles);
                    listArticlesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.e(Config.LOG_PREFIX, "Webservice Failed Response :" + new String(bytes));
                }
            });
        }
    }

    private void initServices() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    private void sendDataToDetailArticleActivity(int position) {
        Intent intent = new Intent(this, DetailArticleActivity.class);
        intent.putExtra(Config.ARTICLE_KEY_TITLE, articlesArrayList.get(position).getTitle());
        intent.putExtra(Config.ARTICLE_KEY_CONTENT, articlesArrayList.get(position).getContent());
        intent.putExtra(Config.ARTICLE_KEY_SOURCE_NAME, articlesArrayList.get(position).getSourceName());
        intent.putExtra(Config.ARTICLE_KEY_SOURCE_URL, articlesArrayList.get(position).getSourceUrl());
        intent.putExtra(Config.ARTICLE_KEY_PICTURE_URL, articlesArrayList.get(position).getPictureUrl());
        startActivity(intent);
    }

    private final BroadcastReceiver broadcastReceiverFromHomeActivity = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String message = intent.getAction();
            if (message.equals(CounterService.DO_A_REFRESH)) {
                Log.d(Config.LOG_PREFIX, "broadcastReceiverFromHomeActivity "+CounterService.DO_A_REFRESH);
            }
            if (message.equals(ACTIVATE_AUTO_REFRESH)) {
                Log.d(Config.LOG_PREFIX, "broadcastReceiverFromHomeActivity "+ACTIVATE_AUTO_REFRESH);
            }
            if (message.equals(DESACTIVATE_AUTO_REFRESH)) {
                Log.d(Config.LOG_PREFIX, "broadcastReceiverFromHomeActivity "+DESACTIVATE_AUTO_REFRESH);
            }
        }
    };

    private void broadcastMessageFromHomeActivity(final String message) {
        Log.d(Config.LOG_PREFIX, "broadcastMessageFromHomeActivity "+message);
        final Intent intent = new Intent(message);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilterCounterService = new IntentFilter();
        intentFilterCounterService.addAction(CounterService.DO_A_REFRESH);
        intentFilterCounterService.addAction(ACTIVATE_AUTO_REFRESH);
        intentFilterCounterService.addAction(DESACTIVATE_AUTO_REFRESH);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverFromHomeActivity, intentFilterCounterService);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiverFromHomeActivity);
    }

/*
    private int refresh() {
        GNRApplication.getUser().refreshData();
        return performSearch();
    }

    private int performSearch() {
        progressDialog = ProgressDialog.show(HomeActivity.this, "Please wait...", "Retrieving data...", true, true);

        progressDialog.setOnCancelListener(new CancelTaskOnListener(new ArticlesSearchTask().execute()));

        return GNRApplication.getUser().getData().countLatest();
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
    }*/

    public void launchCounterService() {
        Log.d(Config.LOG_PREFIX, "launchCounterService startService");
        startService(new Intent(this, CounterService.class));
    }

    public void stopCounterService() {
        Log.d(Config.LOG_PREFIX, "launchCounterService stopCounterService");
        stopService(new Intent(this, CounterService.class));
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
                    if (isRefresh) {
                        launchCounterService();
                    } else {
                        stopCounterService();
                    }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
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

    /*public void saveArticlesInDB(Tag tag) {
        for (int i=0; i<tag.getArticlesList().size(); i++) {
            GNRApplication.getGnrDBHelper().addArticle(tag.getArticlesList().get(i));
        }
    }*/
}
