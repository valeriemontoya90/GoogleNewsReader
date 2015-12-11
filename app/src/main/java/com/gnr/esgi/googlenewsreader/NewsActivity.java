package com.gnr.esgi.googlenewsreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gnr.esgi.googlenewsreader.model.News;

public class NewsActivity extends AppCompatActivity {

    TextView title;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        title = (TextView) findViewById(R.id.news_title);
        content = (TextView) findViewById(R.id.news_content);

        News news = getIntent().getParcelableExtra("news");

        title.setText(news.title);
        content.setText(news.content);
    }
}
