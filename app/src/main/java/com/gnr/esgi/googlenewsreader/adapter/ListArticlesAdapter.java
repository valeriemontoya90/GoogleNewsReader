package com.gnr.esgi.googlenewsreader.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.model.Article;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ListArticlesAdapter extends BaseAdapter {

    private Activity _activity;
    private List<Article> _articles;
    private static LayoutInflater _inflater = null;

    public ListArticlesAdapter(Activity activity,
                               List<Article> articles) {
        _activity = activity;
        _articles = articles;
        _inflater = (LayoutInflater)_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _articles.size();
    }

    @Override
    public Object getItem(int position) {
        return _articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView == null
                        ? _inflater.inflate(R.layout.list_row, null)
                        : convertView;

        TextView title = (TextView) view.findViewById(R.id.news_title);
        TextView date = (TextView) view.findViewById(R.id.news_date);
        TextView source = (TextView) view.findViewById(R.id.news_source);
        ImageView picture = (ImageView) view.findViewById(R.id.news_picture);

        Article article = (Article) getItem(position);

        // Settings all news in list
        title.setText(article.getTitle());
        date.setText(article.getDate().toString());
        source.setText(article.getSource().getName());

        if(article.getPicture() != null
                && article.getPicture().getUrl() != null)
            Picasso.with(GNRApplication.getAppContext()).load(article.getPicture().getUrl()).into(picture);

        return view;
    }
}
