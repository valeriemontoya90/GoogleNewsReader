package com.gnr.esgi.googlenewsreader.listener;

import android.content.DialogInterface;
import android.os.AsyncTask;

public class CancelTaskOnListener implements DialogInterface.OnCancelListener {

    private AsyncTask<?, ?, ?> _task;

    public CancelTaskOnListener(AsyncTask<?, ?, ?> task) {
        _task = task;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if(_task != null)
            _task.cancel(true);
    }
}
