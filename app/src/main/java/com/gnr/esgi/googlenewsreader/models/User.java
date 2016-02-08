package com.gnr.esgi.googlenewsreader.models;

import android.content.SharedPreferences;
import com.gnr.esgi.googlenewsreader.database.DatabaseManager;

public class User {
    private SharedPreferences settings;
    private DatabaseManager data;
    private Tag currentTag;

    public User() {
        data = new DatabaseManager();
    }

    public SharedPreferences getSettings() {
        return settings;
    }

    public void setSettings(SharedPreferences sharedPreferences) {
        settings = sharedPreferences;
    }

    public Boolean getAutoUpdate() {
        return settings.getBoolean("autoRefresh", false);
    }

    public void setAutoUpdate(Boolean autoUpdate) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("autoRefresh", autoUpdate);

        editor.commit();
    }

    public Tag getCurrentTag() {
        return new Tag(settings.getString("currentTag", ""));
    }

    public void setCurrentTag(Tag currentTag) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("currentTag", currentTag.getTagName());

        editor.commit();
    }

    public void enableAutoUpdate() {
        setAutoUpdate(true);
    }

    public void disableAutoUpdate() {
        setAutoUpdate(false);
    }

    public DatabaseManager getData() {
        return  data;
    }
}
