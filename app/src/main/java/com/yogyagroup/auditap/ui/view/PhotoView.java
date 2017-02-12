package com.yogyagroup.auditap.ui.view;

import com.yogyagroup.auditap.model.Photo;
import com.yogyagroup.auditap.ui.adapter.PhotoListAdapter;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public interface PhotoView {
    void initializeToolbar();

    void showRefreshView();

    void hideRefreshView();

    void showUploadProgress();

    void hideUploadProgress();

    void initializeRecyclerView();

    void initializeAdapter(PhotoListAdapter adapter);

    void showMoreOptions(Photo photo, int position);

    void showDeleteProgress();

    void hideDeleteProgress();
}
