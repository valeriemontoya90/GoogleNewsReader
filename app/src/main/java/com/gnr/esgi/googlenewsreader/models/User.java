package com.gnr.esgi.googlenewsreader.models;

import android.content.SharedPreferences;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.constants.TagConstants;
import com.gnr.esgi.googlenewsreader.utils.Config;

public class User {

    private SharedPreferences settings;

    public User() {
        settings = GNRApplication
                    .getAppContext()
                    .getSharedPreferences(
                        Config.PREFS_NAME,
                        0
                    );
    }

    public Boolean getAutoUpdate() {
        return settings.getBoolean(
                "autoRefresh",
                false);
    }

    public void setAutoUpdate(Boolean autoUpdate) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("autoRefresh", autoUpdate);

        editor.commit();
    }

    public Tag getCurrentTag() {
        return new Tag(
                settings.getString(
                        "currentTag",
                        TagConstants.ALL
                )
        );
    }

    public void setCurrentTag(Tag currentTag) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(
                "currentTag",
                currentTag.getName()
        );

        editor.commit();
    }
}
