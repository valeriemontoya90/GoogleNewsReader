package com.gnr.esgi.googlenewsreader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.utils.Config;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailArticleActivity extends AppCompatActivity {

    TextView titleDetailArticle;
    TextView contentDetailArticle;
    ImageView pictureDetailArticle;

    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        titleDetailArticle = (TextView) findViewById(R.id.detail_article_title);
        contentDetailArticle = (TextView) findViewById(R.id.detail_article_content);
        pictureDetailArticle = (ImageView) findViewById(R.id.detail_article_picture);

        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String title = intent.getStringExtra(Config.ARTICLE_KEY_TITLE);
        String content = intent.getStringExtra(Config.ARTICLE_KEY_CONTENT);
        String urlPicture = intent.getStringExtra(Config.ARTICLE_KEY_PICTURE_URL);

        showDetailArticle(title, content, urlPicture);
    }

    public void showDetailArticle(String title, String content, String urlpicture) {
        mImageLoader.displayImage(urlpicture, pictureDetailArticle);
        titleDetailArticle.setText(title);
        contentDetailArticle.setText(content);
    }
}
