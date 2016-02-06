package com.gnr.esgi.googlenewsreader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.constants.ArticleConstants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

public class DetailArticleActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView titleDetailArticle;
    TextView contentDetailArticle;
    ImageView pictureDetailArticle;
    TextView sourceDetailArticle;
    TextView viewOnlineDetailArticle;

    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_article);

        titleDetailArticle = (TextView) findViewById(R.id.detail_article_title);
        contentDetailArticle = (TextView) findViewById(R.id.detail_article_content);
        pictureDetailArticle = (ImageView) findViewById(R.id.detail_article_picture);
        sourceDetailArticle = (TextView) findViewById(R.id.detail_article_source_name);
        viewOnlineDetailArticle = (TextView) findViewById(R.id.detail_article_view_online);
        toolbar = (Toolbar) findViewById(R.id.action_bar);

        mImageLoader = ImageLoader.getInstance();

        viewOnlineDetailArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnline();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String title = intent.getStringExtra(ArticleConstants.ARTICLE_KEY_TITLE);
        String content = intent.getStringExtra(ArticleConstants.ARTICLE_KEY_CONTENT);
        String urlPicture = intent.getStringExtra(ArticleConstants.ARTICLE_KEY_PICTURE_URL);
        String sourceName = intent.getStringExtra(ArticleConstants.ARTICLE_KEY_SOURCE_NAME);
        String sourceUrl = intent.getStringExtra(ArticleConstants.ARTICLE_KEY_SOURCE_URL);

        // Don't show "View online" link if there is no url
        if(sourceUrl.isEmpty())
            viewOnlineDetailArticle.setVisibility(View.INVISIBLE);

        showDetailArticle(title, content, urlPicture, sourceName, sourceUrl);
    }

    public void showDetailArticle(String title, String content, String urlpicture, String sourceName, String sourceUrl) {
        mImageLoader.displayImage(urlpicture, pictureDetailArticle);
        titleDetailArticle.setText(title);
        contentDetailArticle.setText(content);
        sourceDetailArticle.setText(sourceName);

        Picasso.with(this).load(urlpicture).into(pictureDetailArticle);
    }

    public void showOnline() {
        Intent intent = getIntent();
        String sourceUrl = intent.getStringExtra(ArticleConstants.ARTICLE_KEY_SOURCE_URL);

        Intent webViewIntent = new Intent(this, WebViewActivity.class);
        webViewIntent.putExtra(ArticleConstants.ARTICLE_KEY_SOURCE_URL, sourceUrl);
        startActivity(webViewIntent);
    }
}