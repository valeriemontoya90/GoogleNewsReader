package com.gnr.esgi.googlenewsreader;

import android.app.Application;
import android.content.Context;
import com.gnr.esgi.googlenewsreader.database.DatabaseHelper;
import com.gnr.esgi.googlenewsreader.models.User;

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
    private static User user;

    public static User getUser() {
        if(user == null)
            user = new User();

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
