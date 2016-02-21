package com.gnr.esgi.googlenewsreader.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.helper.TagHelper;
import com.gnr.esgi.googlenewsreader.models.Tag;

public class TagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        final float width = metrics.widthPixels;

        GridLayout layout = (GridLayout) findViewById(R.id.tag_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) (width/2),
                (int) (width/2),
                1.0f
        );

        for (final Tag tag : TagHelper.getTags()) {

            Button button = new Button(this);
            button.setText(tag.getName());
            button.setLayoutParams(params);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectTag(tag);
                }
            });

            layout.addView(button);
        }
    }

    public void selectTag(Tag tag) {
        GNRApplication.getUser().setCurrentTag(tag);

        finish();
    }
}
