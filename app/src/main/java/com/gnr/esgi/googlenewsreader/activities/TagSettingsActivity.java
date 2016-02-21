package com.gnr.esgi.googlenewsreader.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.adapters.ListTagsAdapter;
import com.gnr.esgi.googlenewsreader.helper.TagHelper;
import com.gnr.esgi.googlenewsreader.listener.TagsMultiChoiceModeListener;
import com.gnr.esgi.googlenewsreader.models.Tag;
import com.gnr.esgi.googlenewsreader.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TagSettingsActivity extends AppCompatActivity {

    List<Tag> tagsList = new ArrayList<>();
    ListView tagsListView;
    ListTagsAdapter tagsAdapter;
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

        floatingActionButton = (FloatingActionButton) findViewById(R.id.action_tag_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewTagDialog();
            }
        });

        tagsListView = (ListView) findViewById(R.id.tag_settings_list);

        tagsAdapter = new ListTagsAdapter(getApplicationContext(), tagsList);
        tagsListView.setAdapter(tagsAdapter);
        tagsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        tagsListView.setMultiChoiceModeListener(
                new TagsMultiChoiceModeListener(
                        tagsListView,
                        tagsAdapter,
                        toolbar,
                        appBar,
                        floatingActionButton
                )
        );

        searchTag = (EditText) findViewById(R.id.tag_setting_search);
        searchTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Tag> tagsList = TagHelper.getTags();

                if(s.length() > 0) {
                    TagSettingsActivity.this.tagsList.clear();
                    List<Tag> tempTags = new ArrayList<Tag>();

                    for (Tag tag : tagsList) {
                        if(tag.getName().startsWith(s.toString())) {
                            tempTags.add(tag);
                        }
                    }

                    TagSettingsActivity.this.tagsList = tempTags;
                }
                else {
                    TagSettingsActivity.this.tagsList = tagsList;
                }

                tagsAdapter.swapItems(TagSettingsActivity.this.tagsList);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        tagsList = TagHelper.getTags();

        tagsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        tagsAdapter.swapItems(tagsList);
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
                        // Add new tag to database (capitalize first letter)
                        GNRApplication.getDbHelper().addTag(
                                new Tag(
                                        StringUtil.capitalize(
                                                input.getText().toString()
                                        )
                                )
                        );

                        // Update tag list with new added tag
                        tagsAdapter.swapItems(TagHelper.getTags());

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

    public void checkboxHandler(View view) {
        CheckBox checkBox = (CheckBox) view;

        int position = Integer.parseInt(checkBox.getTag().toString());

        //tagsList.get(position);
    }
}
