package com.yogyagroup.auditap.presenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.yogyagroup.auditap.data.API;
import com.yogyagroup.auditap.data.UserCallback;
import com.yogyagroup.auditap.di.ApplicationBase;
import com.yogyagroup.auditap.ui.activity.MainActivity;
import com.yogyagroup.auditap.ui.view.LoginView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private final String TAG = LoginPresenterImpl.class.getSimpleName();

    @Inject
    ApplicationBase application;

    @Inject
    SharedPreferences sharedPreferences;

    API api;
    LoginView loginView;

    public LoginPresenterImpl(API api, LoginView loginView) {
        this.api = api;
        this.loginView = loginView;

        ApplicationBase.getComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void login(String username, String password) {
        loginView.showLoading();

        Call<UserCallback> callback = api.login(username, password);
        callback.enqueue(new Callback<UserCallback>() {
            @Override
            public void onResponse(Call<UserCallback> call, Response<UserCallback> response) {
                loginView.hideLoading();

                if (response.body().getMessage().equals("Success")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("id", response.body().getUser().getId()).apply();
                    editor.putBoolean("isLoggedIn", true).apply();
                    editor.putInt("storeId", response.body().getUser().getStore_id()).apply();

                    Intent intent = new Intent(application, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    application.startActivity(intent);
                } else {
                    Toast.makeText(application, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserCallback> call, Throwable t) {
                loginView.hideLoading();

                Toast.makeText(application, "Error when trying to login again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
