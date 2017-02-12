package com.yogyagroup.auditap.ui.view;

import com.yogyagroup.auditap.ui.adapter.MainListAdapter;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public interface MainView {
    void initializeToolbar();

    void initializeDrawer();

    void showReceiptRefreshView();

    void hideReceiptRefreshView();

    void initializeRecyclerView();

    void initializeAdapter(MainListAdapter adapter);
}
