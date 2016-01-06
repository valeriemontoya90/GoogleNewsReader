package com.gnr.esgi.googlenewsreader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gnr.esgi.googlenewsreader.R;

/**
 * Created by valerie on 06/01/16.
 */
public class GNRDatabaseHelper extends SQLiteOpenHelper {

    private final static String TAG = GNRDatabaseHelper.class.getSimpleName();
    
    public GNRDatabaseHelper(Context context) {
        super(context, context.getString(R.string.app_settings_db_name), null, Integer.valueOf("1"));
    }

    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
