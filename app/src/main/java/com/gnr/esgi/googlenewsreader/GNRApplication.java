package com.gnr.esgi.googlenewsreader;

import android.app.Application;
import android.content.Context;

import com.gnr.esgi.googlenewsreader.database.GNRDatabaseHelper;
import com.gnr.esgi.googlenewsreader.model.User;

/**
 * Created by valerie on 10/01/16.
 */
public class GNRApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        GNRApplication.context = getApplicationContext();
    }

    public static Context getAppContext(){
        return GNRApplication.context;
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
    private static GNRDatabaseHelper gnrDBHelper;
    public static GNRDatabaseHelper getGnrDBHelper() {
        if(gnrDBHelper == null){
            gnrDBHelper = new GNRDatabaseHelper(getAppContext());
        }
        return gnrDBHelper;
    }
}
