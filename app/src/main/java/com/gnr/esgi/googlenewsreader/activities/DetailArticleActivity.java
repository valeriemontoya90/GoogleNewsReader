package com.gnr.esgi.googlenewsreader.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.utils.Config;
import com.gnr.esgi.googlenewsreader.models.Article;

public class DetailArticleActivity extends AppCompatActivity {

    TextView title;
    TextView content;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        title = (TextView) findViewById(R.id.news_title);
        content = (TextView) findViewById(R.id.news_content);
        picture = (ImageView) findViewById(R.id.news_picture);

        // Get news from user's database by given id from main view
        Article article = GNRApplication.getUser().getData().findArticleById(getIntent().getIntExtra(Config.ARTICLE_KEY_ID, 0));

        title.setText(article.getTitle());
        content.setText(article.getContent());
        /*if (article.getPicture() != null && article.getPicture().getPictureBitmap() != null) {
            picture.setImageBitmap(article.getPicture().getPictureBitmap());
        }*/
    }
}
