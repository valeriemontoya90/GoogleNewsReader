package com.gnr.esgi.googlenewsreader.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.model.News;
import java.util.List;

public class LazyAdapter extends BaseAdapter {

    private Activity _activity;
    private List<News> _news;
    private static LayoutInflater _inflater = null;

    public LazyAdapter(Activity activity,
                        List<News> news) {
        _activity = activity;
        _news = news;
        _inflater = (LayoutInflater)_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _news.size();
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
        View view = convertView == null ?
                        _inflater.inflate(R.layout.list_row, null)
                        : convertView;

        TextView title = (TextView) view.findViewById(R.id.news_title);
        TextView date = (TextView) view.findViewById(R.id.news_date);
        TextView source = (TextView) view.findViewById(R.id.news_source);
        //ImageView picture = (ImageView) view.findViewById(R.id.news_picture);

        News news = _news.get(position);

        // Settings all news in list
        title.setText(news.getTitle());
        date.setText(news.getDate().toString());
        source.setText(news.getSource().getName());
        //picture.setImageURI(new URI(newsList.get("picture")));

        return view;
    }
}
