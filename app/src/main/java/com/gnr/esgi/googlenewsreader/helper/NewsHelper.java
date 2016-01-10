package com.gnr.esgi.googlenewsreader.helper;

import com.gnr.esgi.googlenewsreader.constants.NewsConstants;
import com.gnr.esgi.googlenewsreader.model.News;
import com.gnr.esgi.googlenewsreader.model.Tag;

import java.util.List;

public class NewsHelper {

    public static String getUrl(String tagName) {
        return NewsConstants.KEY_API_URL + tagName;
    }

    public static int countRecentNews(List<News> recent, List<News> old) {
        int count = recent.size();

        for (News recentNews : recent)
            for (News oldNews : old)
                if(recentNews == oldNews)
                    count--;

        return count;
    }

}
