package com.gnr.esgi.googlenewsreader.helper;

import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.factory.TagFactory;
import com.gnr.esgi.googlenewsreader.models.Tag;
import java.util.List;

public class TagHelper {

    public static List<Tag> getTags() {
        return TagFactory.fromCursor(
                GNRApplication.getDbHelper().getTags()
        );
    }
}
