package com.gnr.esgi.googlenewsreader;

import android.app.Application;
import android.content.Context;

import com.gnr.esgi.googlenewsreader.database.GNRDatabaseHelper;

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
