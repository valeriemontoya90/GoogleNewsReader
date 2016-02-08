package com.gnr.esgi.googlenewsreader.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gnr.esgi.googlenewsreader.models.Tag;
import java.util.List;

public class ListTagsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Tag> mListTags;
    private SparseBooleanArray mSelectedItemsIds;
    private int layout;
    private int tagName;

    public ListTagsAdapter(Context context,
                           List<Tag> tags,
                           int layout,
                           int tagName) {
        this.mContext = context;
        this.mListTags = tags;
        this.mSelectedItemsIds = new SparseBooleanArray();
        this.layout = layout;
        this.tagName = tagName;
    }

    @Override
    public int getCount() {
        return mListTags.size();
    }

    @Override
    public Tag getItem(int position) {
        return mListTags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(Tag tag) {
        mListTags.remove(tag);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mListTags.remove(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(tagName);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Tag tagSelected = mListTags.get(position);
        viewHolder.name.setText(tagSelected.getTagName());

        return convertView;
    }

    private class ViewHolder {
        TextView name;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if(value) {
            mSelectedItemsIds.put(position, value);
        }
        else {
            mSelectedItemsIds.delete(position);
        }

        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}
