package com.yogyagroup.auditap.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yogyagroup.auditap.R;
import com.yogyagroup.auditap.di.component.ApplicationComponent;
import com.yogyagroup.auditap.di.component.DaggerMainComponent;
import com.yogyagroup.auditap.di.component.MainComponent;
import com.yogyagroup.auditap.di.module.MainModule;
import com.yogyagroup.auditap.presenter.MainPresenter;
import com.yogyagroup.auditap.ui.adapter.MainListAdapter;
import com.yogyagroup.auditap.ui.view.MainView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    private static final int REQUEST_CODE_PERMISSION = 2;
    private String[] mPermission = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.refreshLayoutMain)
    SwipeRefreshLayout refreshLayoutMain;
    @BindView(R.id.recyclerViewMain)
    RecyclerView recyclerViewMain;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    SearchView searchView;

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, mPermission[0]) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this, mPermission[1]) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, mPermission, REQUEST_CODE_PERMISSION);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        requestPermissions();

        mainPresenter.onCreate(savedInstanceState);

        refreshLayoutMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainPresenter.onRefresh();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (searchView != null) {
            searchView.setQuery(mainPresenter.getLatestKeyword(), false);
            searchView.clearFocus();
        }

        // TODO call latest list from database
    }

    @Override
    protected void setupComponent(ApplicationComponent appComponent) {
        MainComponent component = DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .applicationComponent(appComponent)
                .build();

        component.inject(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainPresenter.onRefresh(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                searchView.setQuery(mainPresenter.getLatestKeyword(), false);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            mainPresenter.onRefreshToday();
        } else if (id == R.id.menu_logout) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            mainPresenter.logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void initializeToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public void initializeDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void showReceiptRefreshView() {
        refreshLayoutMain.post(new Runnable() {
            @Override
            public void run() {
                refreshLayoutMain.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideReceiptRefreshView() {
        refreshLayoutMain.post(new Runnable() {
            @Override
            public void run() {
                refreshLayoutMain.setRefreshing(false);
            }
        });
    }

    @Override
    public void initializeRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewMain.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initializeAdapter(MainListAdapter adapter) {
        recyclerViewMain.setAdapter(adapter);
    }
}
