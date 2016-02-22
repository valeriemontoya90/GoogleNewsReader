package com.gnr.esgi.googlenewsreader.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.constants.ArticleConstants;
import com.gnr.esgi.googlenewsreader.utils.Config;
import com.gnr.esgi.googlenewsreader.utils.DateUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

public class DetailArticleActivity extends AppCompatActivity {

    TextView titleDetailArticle;
    TextView contentDetailArticle;
    TextView createdAtDetailArticle;
    ImageView pictureDetailArticle;
    TextView sourceDetailArticle;
    TextView viewOnlineDetailArticle;
    TextView shareDetailArticle;

    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove notification bar
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        super.onCreate(savedInstanceState);
        setTheme(Config.getTheme());
        setContentView(R.layout.activity_detail_article);

        titleDetailArticle = (TextView) findViewById(R.id.detail_article_title);
        contentDetailArticle = (TextView) findViewById(R.id.detail_article_content);
        createdAtDetailArticle = (TextView) findViewById(R.id.detail_article_created_at);
        pictureDetailArticle = (ImageView) findViewById(R.id.detail_article_picture);
        sourceDetailArticle = (TextView) findViewById(R.id.detail_article_source_name);
        viewOnlineDetailArticle = (TextView) findViewById(R.id.detail_article_view_online);
        shareDetailArticle = (TextView) findViewById(R.id.detail_article_share);

        mImageLoader = ImageLoader.getInstance();

        viewOnlineDetailArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnline();
            }
        });

        shareDetailArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareArticle();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String title = intent.getStringExtra(ArticleConstants.ARTICLE_KEY_TITLE);
        String content = intent.getStringExtra(ArticleConstants.ARTICLE_KEY_CONTENT);
        String createdAt = DateUtil.getDuration(intent.getStringExtra(ArticleConstants.ARTICLE_KEY_CREATED_AT));
        String urlPicture = intent.getStringExtra(ArticleConstants.ARTICLE_KEY_PICTURE_URL);
        String sourceName = intent.getStringExtra(ArticleConstants.ARTICLE_KEY_SOURCE_NAME);
        String sourceUrl = intent.getStringExtra(ArticleConstants.ARTICLE_KEY_SOURCE_URL);

        // Don't show "View online" link if there is no url
        if(sourceUrl.isEmpty())
            viewOnlineDetailArticle.setVisibility(View.INVISIBLE);

        showDetailArticle(title, content, createdAt, urlPicture, sourceName, sourceUrl);
    }

    public void showDetailArticle(String title, String content, String createdAt, String urlpicture, String sourceName, String sourceUrl) {
        mImageLoader.displayImage(urlpicture, pictureDetailArticle);
        titleDetailArticle.setText(title);
        contentDetailArticle.setText(content);
        createdAtDetailArticle.setText(createdAt);
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

    public void shareArticle() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(ArticleConstants.ARTICLE_KEY_TITLE);

        String content = new StringBuilder()
            .append(title)
            .append("\n\n")
                .append(intent.getStringExtra(ArticleConstants.ARTICLE_KEY_CONTENT))
            .toString();

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, content);

        startActivity(Intent.createChooser(share, title));
    }
}