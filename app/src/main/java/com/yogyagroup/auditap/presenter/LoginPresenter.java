package com.yogyagroup.auditap.presenter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public interface LoginPresenter {
    void onCreate(Bundle savedInstanceState);

    void onResume();

    void onPause();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();

    void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    boolean onOptionsItemSelected(MenuItem item);

    void login(String username, String password);
}
