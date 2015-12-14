package com.gnr.esgi.googlenewsreader.model;

import com.gnr.esgi.googlenewsreader.database.DatabaseManager;

/**
 * Created by Ismail on 13-12-2015.
 */
public class User {
    private Boolean _autoUpdate;
    private DatabaseManager _data;

    public Boolean getAutoUpdate() {
        return  _autoUpdate;
    }

    public void setAutoUpdate(Boolean autoUpdate) {
        _autoUpdate = autoUpdate;
    }

    public DatabaseManager getData() {
        return  _data;
    }

    public void setData(DatabaseManager data) {
        _data = data;
    }
}