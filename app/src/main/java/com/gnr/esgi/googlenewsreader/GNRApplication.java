package com.gnr.esgi.googlenewsreader;

import android.app.Application;
import android.content.Context;
import com.gnr.esgi.googlenewsreader.database.DatabaseHelper;
import com.gnr.esgi.googlenewsreader.models.SessionManager;

public class GNRApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    public static Context getAppContext(){
        return context;
    }

    ///////////////
    ///USER///
    ///////////////
    private static SessionManager user;

    public static SessionManager getUser() {
        if(user == null)
            user = new SessionManager();

        return user;
    }

    ///////////////
    ///DB HELPER///
    ///////////////
    private static DatabaseHelper dbHelper;

    public static DatabaseHelper getDbHelper() {
        if(dbHelper == null)
            dbHelper = new DatabaseHelper(getAppContext());

        return dbHelper;
    }
}
