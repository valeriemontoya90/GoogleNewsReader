package com.gnr.esgi.googlenewsreader.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.io.FlushedInputStream;
import com.gnr.esgi.googlenewsreader.model.News;
import com.gnr.esgi.googlenewsreader.services.HttpRetriever;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private Activity _activity;
    private List<News> _news;
    private static LayoutInflater _inflater = null;
    private HttpRetriever httpRetriever = new HttpRetriever();

    public NewsAdapter(Activity activity,
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
        ImageView picture = (ImageView) view.findViewById(R.id.news_picture);

        News news = _news.get(position);

        // Settings all news in list
        title.setText(news.getTitle());
        date.setText(news.getDate().toString());
        source.setText(news.getSource().getName());

        if(news.getPicture() != null)
        {
            Bitmap bitmap = fetchBitmapFromCache(news.getPicture());

            if(bitmap == null) {
                new BitmapDownloaderTask(picture).execute(news.getPicture());
            }
            else
                picture.setImageBitmap(bitmap);
        }
        else
            picture.setImageBitmap(null);

        return view;
    }

    private LinkedHashMap<String, Bitmap> bitmapCache = new LinkedHashMap<String, Bitmap>();

    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (bitmapCache) {
                bitmapCache.put(url, bitmap);
            }
        }
    }

    private Bitmap fetchBitmapFromCache(String url){
        synchronized (bitmapCache) {
            final Bitmap bitmap = bitmapCache.get(url);
            if (bitmap != null) {
                // Bitmap found in cache
                // Move element to first position, so that it is removed last
                bitmapCache.remove(url);
                bitmapCache.put(url, bitmap);
                return bitmap;
            }
        }
        return null;
    }

    private class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

        private String url;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView){
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            url = strings[0];
            InputStream inputStream = httpRetriever.retrieveStream(url);
            if(inputStream == null)
                return null;
            return BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(isCancelled())
                bitmap = null;
            addBitmapToCache(url, bitmap);
            if(imageViewReference != null){
                ImageView imageView = imageViewReference.get();
                if(imageView != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
