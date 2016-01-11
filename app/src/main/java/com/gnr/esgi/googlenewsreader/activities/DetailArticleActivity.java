package com.gnr.esgi.googlenewsreader.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.model.Article;

public class DetailArticleActivity extends AppCompatActivity {

    TextView title;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        title = (TextView) findViewById(R.id.news_title);
        content = (TextView) findViewById(R.id.news_content);

        Article article = getIntent().getParcelableExtra("news");

        title.setText(article.getTitle());
        content.setText(article.getContent());
    }
}
