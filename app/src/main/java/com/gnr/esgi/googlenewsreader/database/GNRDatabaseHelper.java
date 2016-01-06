package com.gnr.esgi.googlenewsreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.model.News;

/**
 * Created by valerie on 06/01/16.
 */
public class GNRDatabaseHelper extends SQLiteOpenHelper {

    private final static String TAG = GNRDatabaseHelper.class.getSimpleName();
    
    public GNRDatabaseHelper(Context context) {
        super(context, context.getString(R.string.app_settings_db_name), null, Integer.valueOf("1"));
    }

    public final class ArticleContract {
        public ArticleContract() {}

        public abstract class ArticleEntry implements BaseColumns {
            public static final String TABLE_NAME = "articles";

            public static final String COLUMN_TITLE = "new_title";
            public static final String COLUMN_DATE = "new_date";
            public static final String COLUMN_CONTENT = "new_content";
            public static final String COLUMN_SOURCE = "new_source";
        }
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String TEXT_TYPE = " TEXT";
        String REAL_TYPE = " REAL";
        String INTEGER_TYPE = " NUMERIC";
        String COMMA_SEPARATOR = ",";

        String CREATE_TABLE_NEWS =
                "CREATE TABLE " + ArticleContract.ArticleEntry.TABLE_NAME + " (" +
                        ArticleContract.ArticleEntry._ID + INTEGER_TYPE + " PRIMARY_KEY" + COMMA_SEPARATOR +
                        ArticleContract.ArticleEntry.COLUMN_TITLE + TEXT_TYPE + COMMA_SEPARATOR +
                        ArticleContract.ArticleEntry.COLUMN_DATE + TEXT_TYPE + COMMA_SEPARATOR +
                        ArticleContract.ArticleEntry.COLUMN_CONTENT + TEXT_TYPE + COMMA_SEPARATOR +
                        ArticleContract.ArticleEntry.COLUMN_SOURCE + TEXT_TYPE + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String DROP_TABLE_NEWS = "DROP TABLE IF EXISTS " + ArticleContract.ArticleEntry.TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE_NEWS);
        onCreate(sqLiteDatabase);
    }
    ///////////////
    ////NEWS//////
    /////////////

    public long addArticle(News oneNew) {
        ContentValues values = new ContentValues();
        values.put(ArticleContract.ArticleEntry.COLUMN_TITLE, oneNew.getTitle());
        values.put(ArticleContract.ArticleEntry.COLUMN_DATE, "date");
        values.put(ArticleContract.ArticleEntry.COLUMN_CONTENT, oneNew.getContent());

        long newRowId = this.getWritableDatabase().insert(
                ArticleContract.ArticleEntry.TABLE_NAME,
                null,
                values
        );

        return newRowId;
    }

    public Cursor getAllArticles() {
        return null;
    }

    public Cursor getArticles(String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String sort) {
        return this.getReadableDatabase().query(
                ArticleContract.ArticleEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                groupBy,
                having,
                sort);
    }

    public Cursor getArticleById(long rowID) {
        return getArticles(null, ArticleContract.ArticleEntry._ID + "=?", new String[]{String.valueOf(rowID)}, null, null, null);
    }
}
