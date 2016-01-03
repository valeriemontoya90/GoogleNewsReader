package com.gnr.esgi.googlenewsreader.database;

import android.os.AsyncTask;
import com.gnr.esgi.googlenewsreader.model.News;
import com.gnr.esgi.googlenewsreader.model.Tag;
import com.gnr.esgi.googlenewsreader.parser.JsonParser;
import com.gnr.esgi.googlenewsreader.parser.NewsParser;
import com.gnr.esgi.googlenewsreader.parser.XMLParser;
import com.gnr.esgi.googlenewsreader.services.HttpRetriever;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsReader extends AsyncTask<String, String, String> {

    public static final String KEY_API_URL = "http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q="; //"http://news.google.com/news?output=rss&q=";
    public static final String KEY_NEWS = "item";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "description";
    public static final String KEY_LINK = "link";
    public static final String KEY_DATE = "pubDate";

    private List<Tag> _tags;

    public NewsReader(List<Tag> tags) {
        _tags = tags;
    }

    public List<Tag> getTags() {
        return _tags;
    }

    public void setTags(List<Tag> tags) {
        _tags = tags;
    }

    @Override
    protected String doInBackground(String... params) {
        readNews();

        return null;
    }

    private void readNews() {
        for(Tag tag : _tags) {
            InputStream source = HttpRetriever.retrieveStream(getUrl(tag.getName()));

            Gson gson = new Gson();

            Reader reader = new InputStreamReader(source);

            Map<String, Object> response = new HashMap<>();
            response = (Map<String, Object>) gson.fromJson(reader, response.getClass());

            tag.setNews(JsonParser.parse((ArrayList<LinkedTreeMap<String, Object>>) ((LinkedTreeMap<String, Object>) response.get("responseData")).get("results")));
        }
    }

    private void readNewsXML() {
        for(Tag tag : _tags) {
            XMLParser parser = new XMLParser();
            String xml = parser.getXmlFromUrl(getUrl(tag.getName()));
            Document doc = parser.getDomElement(xml);

            NodeList nl = doc.getElementsByTagName(KEY_NEWS);

            List<News> newsList = new ArrayList<>();
            for(int i=0; i<nl.getLength(); i++) {
                News news = new News();
                Element e = (Element) nl.item(i);

                news.setTitle(parser.getValue(e, KEY_TITLE));
                news.setContent(parser.getValue(e, KEY_CONTENT));

                //Parsing news before add to news list
                newsList.add(NewsParser.parse(news));
            }

            tag.setNews(newsList);
        }
    }

    private String getUrl(String tagName) {
        return KEY_API_URL + tagName;
    }
}
