package com.gnr.esgi.googlenewsreader.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.adapter.LazyAdapter;
import com.gnr.esgi.googlenewsreader.database.DatabaseManager;
import com.gnr.esgi.googlenewsreader.helper.NewsHelper;
import com.gnr.esgi.googlenewsreader.listener.CancelTaskOnListener;
import com.gnr.esgi.googlenewsreader.model.Tag;
import com.gnr.esgi.googlenewsreader.parser.JsonParser;
import com.gnr.esgi.googlenewsreader.services.HttpRetriever;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends Activity {

    ListView listview;
    LazyAdapter adapter;
    DatabaseManager databaseManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        refresh();

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button_refresh);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        listview = (ListView) findViewById(R.id.news_list);

        adapter = new LazyAdapter(this, databaseManager.getAllNews());
        listview.setAdapter(adapter);

        // Click event on news list item
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showNewsOverview(position);
            }
        });
    }

    private void refresh() {
        databaseManager = new DatabaseManager();
        performSearch();
    }

    private void performSearch() {
        progressDialog = ProgressDialog.show(HomeActivity.this,
                "Please wait...", "Retrieving data...", true, true);

        NewsSearchTask task = new NewsSearchTask();
        task.execute(databaseManager.getTags());
        progressDialog.setOnCancelListener(new CancelTaskOnListener(task));
    }

    private void showNewsOverview(Integer id) {
        Intent intent = new Intent(this, NewsActivity.class);

        intent.putExtra("news", (Parcelable) databaseManager.findNewsById(id));

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class NewsSearchTask extends AsyncTask<List<Tag>, Void, List<Tag>> {

        @Override
        protected List<Tag> doInBackground(List<Tag>... params) {
            for(Tag tag : params[0]) {
                InputStream source = HttpRetriever.retrieveStream(NewsHelper.getUrl(tag.getName()));

                Gson gson = new Gson();

                Reader reader = new InputStreamReader(source);

                Map<String, Object> response = new HashMap<>();
                response = (Map<String, Object>) gson.fromJson(reader, response.getClass());

                tag.setNews(JsonParser.parse((ArrayList<LinkedTreeMap<String, Object>>) ((LinkedTreeMap<String, Object>) response.get("responseData")).get("results")));
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
                        databaseManager.setTags(result);
                }
            });
        }
    }


}
