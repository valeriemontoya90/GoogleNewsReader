package com.gnr.esgi.googlenewsreader.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.adapters.ListArticlesAdapter;
import com.gnr.esgi.googlenewsreader.listener.ArticlesMultiChoiceModeListener;
import com.gnr.esgi.googlenewsreader.listener.BookmarksMultiChoiceModeListener;
import com.gnr.esgi.googlenewsreader.models.Article;
import java.util.ArrayList;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity {

    List<Article> bookmarksList = new ArrayList<>();
    ListView bookmarksListView;
    ListArticlesAdapter bookmarksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        bookmarksListView = (ListView) findViewById(R.id.news_list);

        bookmarksAdapter = new ListArticlesAdapter(getApplicationContext(), bookmarksList);
        bookmarksListView.setAdapter(bookmarksAdapter);
        bookmarksListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        bookmarksListView.setMultiChoiceModeListener(
                new BookmarksMultiChoiceModeListener(
                        bookmarksListView,
                        bookmarksAdapter
                )
        );
    }
}
