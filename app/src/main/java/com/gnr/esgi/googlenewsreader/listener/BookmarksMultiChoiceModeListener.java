package com.gnr.esgi.googlenewsreader.listener;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.adapters.ListArticlesAdapter;
import com.gnr.esgi.googlenewsreader.models.Article;

public class BookmarksMultiChoiceModeListener implements AbsListView.MultiChoiceModeListener {

    private ListView listView;
    private ListArticlesAdapter adapter;

    public BookmarksMultiChoiceModeListener(ListView listView,
                                           ListArticlesAdapter adapter) {
        this.listView = listView;
        this.adapter = adapter;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        // Capture total checked items
        final int checkedCount = listView.getCheckedItemCount();

        String message = new StringBuilder()
                .append(checkedCount)
                .append(" ")
                .append(checkedCount > 1
                        ? " articles selected"
                        : " article selected")
                .toString();

        // Set the CAB title according to total checked items
        mode.setTitle(message);

        // Calls toggleSelection method from adapter Class
        adapter.toggleSelection(position);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_article_delete:
                // Calls getSelectedIds method from ListViewAdapter Class
                SparseBooleanArray selected = adapter.getSelectedIds();

                // Captures all selected ids with a loop
                for (int i = (selected.size() - 1); i >= 0; i--) {
                    if (selected.valueAt(i)) {
                        Article selectedItem = adapter.getItem(selected.keyAt(i));

                        // Update database, set deleted true
                        selectedItem.setDeleted(true);
                        GNRApplication.getDbHelper().updateArticle(selectedItem);

                        // Remove selected items following the ids
                        adapter.remove(selectedItem);
                    }
                }

                // Close CAB
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
    }
}
