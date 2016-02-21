package com.gnr.esgi.googlenewsreader.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tag_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

        boolean left = true;
        for (final Tag tag : TagHelper.getTags()) {
            Button button = new Button(this);
            button.setText(tag.getName());

            if(left) {
                button.setGravity(Gravity.LEFT);

                left = false;
            }
            else {
                button.setGravity(Gravity.RIGHT);

                left = true;
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectTag(tag);
                }
            });

            linearLayout.addView(button, params);
        }
    }

    public void selectTag(Tag tag) {
        GNRApplication.getUser().setCurrentTag(tag);

        finish();
    }
}
