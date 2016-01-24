package com.gnr.esgi.googlenewsreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.models.Article;

import java.util.List;

public class ListArticlesAdapter extends BaseAdapter {

    private Context context;
    private List<Article> arrayListArticles;

    public ListArticlesAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.arrayListArticles = articles;
    }

    @Override
    public int getCount() {
        return arrayListArticles.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.createdAt = (TextView) convertView.findViewById(R.id.news_date);
            viewHolder.source = (TextView) convertView.findViewById(R.id.news_source);
            viewHolder.picture = (ImageView) convertView.findViewById(R.id.news_picture);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Article articleSelected = arrayListArticles.get(position);
        viewHolder.title.setText(articleSelected.getTitle());
        viewHolder.createdAt.setText(articleSelected.getCreatedAt().toString());
        viewHolder.source.setText(articleSelected.getSource().getSourceName());
        //viewHolder.picture.setImageResource();
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView createdAt;
        TextView source;
        ImageView picture;
    }

    public void swapItems(List<Article> items) {
        this.arrayListArticles = items;
        notifyDataSetChanged();
    }
}
