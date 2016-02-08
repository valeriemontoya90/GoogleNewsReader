package com.gnr.esgi.googlenewsreader.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.models.Tag;

public class TagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.tag_layout);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);

        for (final Tag tag : GNRApplication.getUser().getData().getTags()) {
            Button button = new Button(this);
            button.setText(tag.getTagName());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectTag(tag);
                }
            });

            relativeLayout.addView(button, lp);
        }
    }

    public void selectTag(Tag tag) {
        GNRApplication.getUser().setCurrentTag(tag);

        finish();
    }
}
