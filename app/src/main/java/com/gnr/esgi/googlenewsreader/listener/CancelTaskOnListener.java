package com.gnr.esgi.googlenewsreader.listener;

import android.content.DialogInterface;
import android.os.AsyncTask;

import com.gnr.esgi.googlenewsreader.adapters.ListArticlesAdapter;

public class CancelTaskOnListener implements DialogInterface.OnCancelListener {

    private AsyncTask<?, ?, ?> task;
    ListArticlesAdapter adapter;

    public CancelTaskOnListener(AsyncTask<?, ?, ?> task, ListArticlesAdapter adapter) {
        this.task = task;
        this.adapter = adapter;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if(task != null) {
            task.cancel(true);

            adapter.notifyDataSetChanged();
        }
    }
}
