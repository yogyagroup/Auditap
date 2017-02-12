package com.yogyagroup.auditap.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.yogyagroup.auditap.R;
import com.yogyagroup.auditap.data.API;
import com.yogyagroup.auditap.data.ReceiptCallback;
import com.yogyagroup.auditap.di.ApplicationBase;
import com.yogyagroup.auditap.ui.activity.LoginActivity;
import com.yogyagroup.auditap.ui.adapter.MainListAdapter;
import com.yogyagroup.auditap.ui.view.MainView;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class MainPresenterImpl implements MainPresenter {
    private final String TAG = MainPresenterImpl.class.getSimpleName();
    private MainListAdapter mainListAdapter;
    private MainView mainView;
    private int storeId;

    @Inject
    SharedPreferences sharedPreferences;

    Context context;
    API api;

    public MainPresenterImpl(API api, MainView mainView, Context context) {
        this.api = api;
        this.context = context;
        this.mainView = mainView;

        ApplicationBase.getComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        mainView.initializeToolbar();
        mainView.initializeDrawer();
        mainView.initializeRecyclerView();

        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        storeId = sharedPreferences.getInt("storeId", 0);

        if (!isLoggedIn) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            Activity activity = (Activity)context;
            activity.finish();
        } else {
            onRefreshToday();
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onRefreshToday() {
        onRefresh("");
    }

    @Override
    public void onRefresh() {
        // Use latest keyword search
        String keyword = sharedPreferences.getString("keyword", "");

        onRefresh(keyword);
    }

    @Override
    public String getLatestKeyword() {
        return sharedPreferences.getString("keyword", "");
    }

    @Override
    public void onRefresh(String keyword) {
        mainView.showReceiptRefreshView();

        // Save history search by keyword
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("keyword", keyword).apply();

        Observable<ReceiptCallback> receiptCallbackObservable;
        if (keyword.isEmpty()) {
            receiptCallbackObservable = api.receiptToday(storeId);
        } else {
            receiptCallbackObservable = api.receiptKeyword(storeId, keyword);
        }

        receiptCallbackObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ReceiptCallback>() {
                    @Override
                    public void onCompleted() {
                        mainView.hideReceiptRefreshView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error get list of receipts: " + e.getMessage());
                        mainView.hideReceiptRefreshView();
                        Toast.makeText(context, "Something went wrong. Check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ReceiptCallback receiptCallback) {
                        mainView.hideReceiptRefreshView();
                        mainListAdapter = new MainListAdapter(receiptCallback.getReceipts(), context);
                        mainView.initializeAdapter(mainListAdapter);
                        if (receiptCallback.getReceipts().size() == 0) {
                            Toast.makeText(context, "No receipts found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", 0).apply();
        editor.putBoolean("isLoggedIn", false).apply();

        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);

        Activity activity = (Activity) context;
        activity.finish();
    }
}
