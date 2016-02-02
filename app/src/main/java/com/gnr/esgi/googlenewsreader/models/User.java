package com.gnr.esgi.googlenewsreader.models;

import com.gnr.esgi.googlenewsreader.database.DatabaseManager;

public class User {
    private Boolean _autoUpdate;
    private DatabaseManager _data;

    public Boolean getAutoUpdate() {
        return  _autoUpdate;
    }

    public void setAutoUpdate(Boolean autoUpdate) {
        _autoUpdate = autoUpdate;
    }

    public void enableAutoUpdate() {
        setAutoUpdate(true);
    }

    public void disableAutoUpdate(Boolean autoUpdate) {
        setAutoUpdate(false);
    }

    public DatabaseManager getData() {
        return  _data;
    }

    public void setData(DatabaseManager data) {
        _data = data;
    }

    public void refreshData() {
        _data = new DatabaseManager();
    }
}
