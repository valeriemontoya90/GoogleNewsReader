package com.gnr.esgi.googlenewsreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gnr.esgi.googlenewsreader.constants.DatabaseConstants;
import com.gnr.esgi.googlenewsreader.models.Article;
import com.gnr.esgi.googlenewsreader.models.Tag;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final static String TAG = DatabaseHelper.class.getSimpleName();

    public DatabaseHelper(Context context) {
        super(context, DatabaseConstants.DB_NAME, null, DatabaseConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE_ARTICLES =
                "CREATE TABLE " + DatabaseConstants.ArticleEntry.TABLE_NAME + " (" +
                        DatabaseConstants.ArticleEntry._ID + DatabaseConstants.INTEGER_TYPE + " PRIMARY_KEY" + DatabaseConstants.COMMA_SEPARATOR +
                        DatabaseConstants.ArticleEntry.COLUMN_TITLE + DatabaseConstants.TEXT_TYPE + DatabaseConstants.COMMA_SEPARATOR +
                        DatabaseConstants.ArticleEntry.COLUMN_DATE + DatabaseConstants.TEXT_TYPE + DatabaseConstants.COMMA_SEPARATOR +
                        DatabaseConstants.ArticleEntry.COLUMN_CONTENT + DatabaseConstants.TEXT_TYPE + DatabaseConstants.COMMA_SEPARATOR +
                        DatabaseConstants.ArticleEntry.COLUMN_SOURCE_NAME + DatabaseConstants.TEXT_TYPE + DatabaseConstants.COMMA_SEPARATOR +
                        DatabaseConstants.ArticleEntry.COLUMN_SOURCE_URL + DatabaseConstants.TEXT_TYPE + DatabaseConstants.COMMA_SEPARATOR +
                        DatabaseConstants.ArticleEntry.COLUMN_TAG_NAME + DatabaseConstants.TEXT_TYPE + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_ARTICLES);

        String CREATE_TABLE_TAGS =
                "CREATE TABLE " + DatabaseConstants.TagEntry.TABLE_NAME + " (" +
                        DatabaseConstants.TagEntry._ID + DatabaseConstants.INTEGER_TYPE + " PRIMARY_KEY" + DatabaseConstants.COMMA_SEPARATOR +
                        DatabaseConstants.TagEntry.COLUMN_NAME + DatabaseConstants.TEXT_TYPE + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_TAGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String DROP_TABLE_ARTICLES = "DROP TABLE IF EXISTS " + DatabaseConstants.ArticleEntry.TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE_ARTICLES);

        String DROP_TABLE_TAGS = "DROP TABLE IF EXISTS " + DatabaseConstants.TagEntry.TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE_TAGS);

        onCreate(sqLiteDatabase);
    }

    //region Articles

    public long addArticle(Article article) {
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.ArticleEntry.COLUMN_TITLE, article.getTitle());
        values.put(DatabaseConstants.ArticleEntry.COLUMN_DATE, article.getCreatedAt());
        values.put(DatabaseConstants.ArticleEntry.COLUMN_CONTENT, article.getContent());
        values.put(DatabaseConstants.ArticleEntry.COLUMN_SOURCE_NAME, article.getSource().getSourceName());
        values.put(DatabaseConstants.ArticleEntry.COLUMN_SOURCE_URL, article.getSource().getSourceUrl());
        values.put(DatabaseConstants.ArticleEntry.COLUMN_TAG_NAME, article.getLinkTagName());

        Log.d("DB COLUMN_TITLE", values.get(DatabaseConstants.ArticleEntry.COLUMN_TITLE).toString());
        Log.d("DB COLUMN_DATE", values.get(DatabaseConstants.ArticleEntry.COLUMN_DATE).toString());
        Log.d("DB COLUMN_CONTENT", values.get(DatabaseConstants.ArticleEntry.COLUMN_CONTENT).toString());
        Log.d("DB COLUMN_SOURCE_NAME", values.get(DatabaseConstants.ArticleEntry.COLUMN_SOURCE_NAME).toString());
        Log.d("DB COLUMN_SOURCE_URL", values.get(DatabaseConstants.ArticleEntry.COLUMN_SOURCE_URL).toString());
        Log.d("DB COLUMN_TAG_NAME", values.get(DatabaseConstants.ArticleEntry.COLUMN_TAG_NAME).toString());

        return getWritableDatabase()
                .insert(
                        DatabaseConstants.ArticleEntry.TABLE_NAME,
                        null,
                        values
                );
    }

    public Cursor getAllArticles() {
        return getReadableDatabase()
                .query(
                        DatabaseConstants.ArticleEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
    }

    public Cursor getArticles(String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String sort) {
        return getReadableDatabase()
                .query(
                        DatabaseConstants.ArticleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        groupBy,
                        having,
                        sort
                );
    }

    public Cursor getArticle(long rowID) {
        return getArticles(null, DatabaseConstants.ArticleEntry._ID + "=?", new String[]{String.valueOf(rowID)}, null, null, null);
    }

    public Cursor getArticles(String tagName) {
        return getArticles(
                        null,
                        DatabaseConstants.ArticleEntry.COLUMN_TAG_NAME + "=?",
                        new String[]{tagName},
                        null,
                        null,
                        null
                );
    }

    public Cursor getReadArticles(String tagName) {
        return getArticles(
                        null,
                        DatabaseConstants.ArticleEntry.COLUMN_TAG_NAME + "=?",
                        new String[]{tagName},
                        null,
                        null,
                        null
                );
    }

    public int deleteArticles() {
        return deleteArticles(
                null,
                null
            );
    }

    public int deleteArticles(String selection, String[] selectionArgs) {
        return getWritableDatabase()
                .delete(
                        DatabaseConstants.ArticleEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
    }

    public int deleteArticles(String[] rowIDs) {
        return deleteArticles(
                DatabaseConstants.ArticleEntry._ID + " IN (" + makePlaceholders(rowIDs.length) + ")",
                rowIDs
            );
    }

    //endregion

    //region Tags

    public long addTag(Tag tag) {
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.TagEntry.COLUMN_NAME, tag.getTagName());

        Log.d("DB COLUMN_NAME", values.get(DatabaseConstants.TagEntry.COLUMN_NAME).toString());

        return getWritableDatabase()
                .insert(
                        DatabaseConstants.TagEntry.TABLE_NAME,
                        null,
                        values
                );
    }

    public Cursor getTags() {
        return getReadableDatabase()
                .query(
                        DatabaseConstants.ArticleEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
    }

    public Cursor getTags(String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String sort) {
        return getReadableDatabase()
                .query(
                        DatabaseConstants.TagEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        groupBy,
                        having,
                        sort
                );
    }

    public Cursor getTag(long rowID) {
        return getTags(
                    null,
                    DatabaseConstants.TagEntry._ID + "=?",
                    new String[]{String.valueOf(rowID)},
                    null,
                    null,
                    null
                );
    }

    public Cursor getTag(String name) {
        return getTags(
                    null,
                    DatabaseConstants.TagEntry.COLUMN_NAME + "=?",
                    new String[]{name},
                    null,
                    null,
                    null
            );
    }

    public int deleteTags() {
        return deleteTags(
                        null,
                        null
                );
    }

    public int deleteTags(String selection, String[] selectionArgs) {
        return getWritableDatabase()
                .delete(
                        DatabaseConstants.TagEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
    }

    public int deleteTags(String[] rowIDs) {
        return deleteTags(
                        DatabaseConstants.TagEntry._ID + " IN (" + makePlaceholders(rowIDs.length) + ")",
                        rowIDs
                );
    }

    //endregion

    private String makePlaceholders(int len) {
        if (len > 0) {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");

            for (int i = 1; i < len; i++)
                sb.append(", ?");

            return sb.toString();
        }

        throw new RuntimeException("No placeholders");
    }
}
