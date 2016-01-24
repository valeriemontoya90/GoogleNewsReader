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
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ListArticlesAdapter extends BaseAdapter {

    private Context mContext;
    private List<Article> mListArticles;
    private ImageLoader mImageLoader;

    public ListArticlesAdapter(Context context, List<Article> articles) {
        this.mContext = context;
        this.mListArticles = articles;
        //this.mImageLoader = ;
    }

    @Override
    public int getCount() {
        return mListArticles.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.createdAt = (TextView) convertView.findViewById(R.id.news_date);
            viewHolder.source = (TextView) convertView.findViewById(R.id.news_source);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Article articleSelected = mListArticles.get(position);
        viewHolder.title.setText(articleSelected.getTitle());
        viewHolder.createdAt.setText(articleSelected.getCreatedAt().toString());
        viewHolder.source.setText(articleSelected.getSource().getSourceName());

        ImageView picture = (ImageView) convertView.findViewById(R.id.news_picture);
        ImageLoader.getInstance().displayImage(articleSelected.getPicture().getPictureUrl(), picture);

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
        this.mListArticles = items;
        notifyDataSetChanged();
    }
}
