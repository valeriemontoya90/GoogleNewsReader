package com.gnr.esgi.googlenewsreader.activities;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.adapters.ListArticlesAdapter;
import com.gnr.esgi.googlenewsreader.constants.ArticleConstants;
import com.gnr.esgi.googlenewsreader.helper.ArticleHelper;
import com.gnr.esgi.googlenewsreader.listener.ArticlesMultiChoiceModeListener;
import com.gnr.esgi.googlenewsreader.models.Article;
import com.gnr.esgi.googlenewsreader.services.RefreshService;
import com.gnr.esgi.googlenewsreader.tasks.DatabaseTask;
import com.gnr.esgi.googlenewsreader.utils.Config;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends ActionBarActivity {

    boolean isRefresh = GNRApplication.getUser().getAutoUpdate();

    RelativeLayout relativeLayout;
    List<Article> articlesArrayList = new ArrayList<>();
    ListView listviewArticles;
    ListArticlesAdapter listArticlesAdapter;
    static LocalBroadcastManager broadcaster;
    AppBarLayout appBar;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.button_refresh);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySnackbar(
                        refreshListArticles()
                );
            }
        });

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        listviewArticles = (ListView) findViewById(R.id.news_list);

        listArticlesAdapter = new ListArticlesAdapter(getApplicationContext(), articlesArrayList);
        listviewArticles.setAdapter(listArticlesAdapter);
        listviewArticles.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listviewArticles.setMultiChoiceModeListener(
                new ArticlesMultiChoiceModeListener(
                        listviewArticles,
                        listArticlesAdapter,
                        toolbar,
                        appBar,
                        floatingActionButton
                )
        );

        listviewArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Update database, set read true
                ArticleHelper.setRead(articlesArrayList.get(position));

                listArticlesAdapter.notifyDataSetChanged();

                openArticleDetail(position);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        initServices();

        // At application start, if database contains no articles, load from internet
        if(ArticleHelper.getArticles().isEmpty()) {
            displaySnackbar(
                    refreshListArticles()
            );
        }

        refreshListView();
    }

    @Override
    public void onResume() {
        super.onResume();

        listArticlesAdapter.swapItems(articlesArrayList);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //broadcaster.unregisterReceiver(broadcastReceiverFromHomeActivity);
    }

    // Erase entire articles list and update it with fresh news
    public Integer refreshListArticles() {
        // Save old news to compare with latest
        List<Article> oldArticles = articlesArrayList;

        // First refresh articles in database getting new content from internet
        ArticleHelper.refreshArticles();

        // Then refresh view
        refreshListView();

        return ArticleHelper.countRecentNews(
                articlesArrayList,
                oldArticles
        );
    }

    // Keep current articles, load new articles from internet and add them to previous articles list
    public Integer loadMoreArticles() {
        // Save old news to compare with latest
        List<Article> oldArticles = articlesArrayList;

        // First refresh articles in database getting new content from internet
        ArticleHelper.moreArticles();

        // Then refresh view
        refreshListView();

        return ArticleHelper.countRecentNews(
                articlesArrayList,
                oldArticles
        );
    }

    private void refreshListView() {
        //Clean listArticles list before refresh
        articlesArrayList.clear();

        new
            DatabaseTask(
                articlesArrayList,
                listArticlesAdapter
            )
            .execute();
    }

    private void initServices() {
        if(GNRApplication.getUser().getAutoUpdate()) {
            if (!isRefreshServiceRunning(RefreshService.class)) {
                broadcaster = LocalBroadcastManager.getInstance(this);

                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(RefreshService.NEW_ARTICLES_ARE_READY);

                broadcaster.registerReceiver(
                        broadcastReceiverFromHomeActivity,
                        intentFilter
                );

                launchRefreshService();
            }
        }
        else {
            if (isRefreshServiceRunning(RefreshService.class)) {
                stopRefreshService();
            }
        }
    }

    private void openArticleDetail(int position) {
        Intent intent = new Intent(this, DetailArticleActivity.class);

        intent.putExtra(
                ArticleConstants.ARTICLE_KEY_TITLE,
                articlesArrayList.get(position).getTitle()
        );

        intent.putExtra(
                ArticleConstants.ARTICLE_KEY_CONTENT,
                articlesArrayList.get(position).getContent()
        );

        intent.putExtra(
                ArticleConstants.ARTICLE_KEY_CREATED_AT,
                articlesArrayList.get(position).getCreatedAt()
        );

        intent.putExtra(
                ArticleConstants.ARTICLE_KEY_SOURCE_NAME,
                articlesArrayList.get(position).getSource().getName()
        );

        intent.putExtra(
                ArticleConstants.ARTICLE_KEY_SOURCE_URL,
                articlesArrayList.get(position).getSource().getUrl()
        );

        intent.putExtra(
                ArticleConstants.ARTICLE_KEY_PICTURE_URL,
                articlesArrayList.get(position).getPicture().getPictureUrl()
        );

        startActivity(intent);
    }

    private final BroadcastReceiver broadcastReceiverFromHomeActivity = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String message = intent.getAction();

            if(message.equals(RefreshService.NEW_ARTICLES_ARE_READY)) {
                if(Config.DISPLAY_LOG)
                    Log.d(
                            RefreshService.TAG,
                            "receiveBroadcastMessageFromHomeActivity " + RefreshService.NEW_ARTICLES_ARE_READY
                    );

                refreshListView();
            }
        }
    };

    private void displaySnackbar(Integer count) {
        String message =
                new StringBuilder()
                    .append(count)
                    .append(" ")
                    .append(getString(R.string.snackbar_addedNews))
                    .toString();

        final Snackbar snackbar = Snackbar.make(relativeLayout, message, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snackbar_close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
    }

    public void showTagSettings() {
        Intent intent = new Intent(this, TagSettingsActivity.class);
        startActivity(intent);
    }

    public void showTags() {
        Intent intent = new Intent(this, TagActivity.class);
        startActivity(intent);
    }

    public void launchRefreshService() {
        if(Config.DISPLAY_LOG)
            Log.d(
                    RefreshService.TAG,
                    "launchRefreshService startService"
            );

       // If refresh service isn't already running, start it
        if (!isRefreshServiceRunning(RefreshService.class))
            startService(
                    new Intent(
                            this,
                            RefreshService.class
                    )
            );
    }

    public void stopRefreshService() {
        if (Config.DISPLAY_LOG)
            Log.d(
                    RefreshService.TAG,
                    "launchRefreshService stopRefreshService"
            );

        // If refresh service is already running, stop it
        if (isRefreshServiceRunning(RefreshService.class))
            stopService(
                    new Intent(
                            this,
                            RefreshService.class
                    )
            );
    }

    public boolean isRefreshServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }

        return false;
    }

    public void showRefreshDialog() {
        final CharSequence[] items = { getString(R.string.refreshDialog_message) };
        boolean[] checkedItems = new boolean[] { isRefresh };

        final AlertDialog.Builder builder
            = new AlertDialog
                    .Builder(this)
                    .setTitle(R.string.refreshDialog_title)
                    .setCancelable(false)
                    .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            isRefresh = isChecked;
                        }
                    })
                    .setPositiveButton(R.string.refreshDialog_accept, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GNRApplication.getUser().setAutoUpdate(isRefresh);

                            initServices();

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
        getMenuInflater().inflate(
                R.menu.menu_home,
                menu
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                showRefreshDialog();
                return true;

            case R.id.action_tags:

                showTags();
                return true;

            case R.id.action_search:
                showTagSettings();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
