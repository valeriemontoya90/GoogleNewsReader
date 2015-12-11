package com.gnr.esgi.googlenewsreader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ismail on 11-12-2015.
 */
public class LazyAdapter extends BaseAdapter {

    private Activity _activity;
    private ArrayList<HashMap<String, String>> _data;
    private static LayoutInflater _inflater = null;

    public LazyAdapter(Activity activity,
                        ArrayList<HashMap<String, String>> data) {
        _activity = activity;
        _data = data;
        _inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _data.size();
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
        TextView content = (TextView) view.findViewById(R.id.news_content);
        TextView source = (TextView) view.findViewById(R.id.news_source);
        ImageView picture = (ImageView) view.findViewById(R.id.news_picture);

        HashMap<String, String> newsList = new HashMap<String, String>();
        newsList = _data.get(position);

        // Settings all news in list
        title.setText(newsList.get("title"));
        content.setText(newsList.get("content"));
        source.setText(newsList.get("source"));
        //picture.setImageURI(new URI(newsList.get("picture")));

        return view;
    }
}
