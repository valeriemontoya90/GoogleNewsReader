package com.gnr.esgi.googlenewsreader.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.adapters.ListTagsAdapter;
import com.gnr.esgi.googlenewsreader.helper.TagHelper;
import com.gnr.esgi.googlenewsreader.listener.TagsMultiChoiceModeListener;
import com.gnr.esgi.googlenewsreader.models.Tag;
import java.util.ArrayList;
import java.util.List;

public class TagSettingsActivity extends ActionBarActivity {

    List<Tag> tagsArrayList = new ArrayList<>();
    ListView listviewTags;
    ListTagsAdapter listTagsAdapter;
    FloatingActionButton floatingActionButton;
    AppBarLayout appBar;
    Toolbar toolbar;
    EditText searchTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_settings);

        appBar = (AppBarLayout) findViewById(R.id.tag_settings_app_bar);
        toolbar = (Toolbar) findViewById(R.id.tag_setting_toolbar);
        setSupportActionBar(toolbar);

        listviewTags = (ListView) findViewById(R.id.tag_settings_list);

        listTagsAdapter = new ListTagsAdapter(getApplicationContext(), tagsArrayList);
        listviewTags.setAdapter(listTagsAdapter);
        listviewTags.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listviewTags.setMultiChoiceModeListener(
                new TagsMultiChoiceModeListener(
                        listviewTags,
                        listTagsAdapter,
                        toolbar,
                        appBar,
                        floatingActionButton
                )
        );

        floatingActionButton = (FloatingActionButton) findViewById(R.id.action_tag_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewTagDialog();
            }
        });

        searchTag = (EditText) findViewById(R.id.tag_setting_search);
        searchTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Tag> tagsList = TagHelper.getTags();

                if(s.length() > 0) {
                    tagsArrayList.clear();
                    List<Tag> tempTags = new ArrayList<Tag>();

                    for (Tag tag : tagsList) {
                        if(tag.getName().startsWith(s.toString())) {
                            tempTags.add(tag);
                        }
                    }

                    tagsArrayList = tempTags;
                }
                else {
                    tagsArrayList = tagsList;
                }

                listTagsAdapter.swapItems(tagsArrayList);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        tagsArrayList = TagHelper.getTags();

        listTagsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        listTagsAdapter.swapItems(tagsArrayList);
    }

    public void showNewTagDialog() {
        final EditText input = new EditText(TagSettingsActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        final AlertDialog.Builder builder
                = new AlertDialog
                .Builder(this)
                .setTitle(R.string.addTagDialog_title)
                .setCancelable(false)
                .setPositiveButton(R.string.addTagDialog_accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Add new tag to database
                        GNRApplication.getDbHelper().addTag(new Tag(input.getText().toString()));

                        // Update tag list with new added tag
                        listTagsAdapter.swapItems(TagHelper.getTags());

                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.addTagDialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setView(input);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
