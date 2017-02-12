package com.yogyagroup.auditap.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.yogyagroup.auditap.R;
import com.yogyagroup.auditap.di.component.ApplicationComponent;
import com.yogyagroup.auditap.di.component.DaggerLoginComponent;
import com.yogyagroup.auditap.di.component.LoginComponent;
import com.yogyagroup.auditap.di.module.LoginModule;
import com.yogyagroup.auditap.presenter.LoginPresenter;
import com.yogyagroup.auditap.ui.view.LoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @Inject
    LoginPresenter loginPresenter;

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    ProgressDialog progressDialog;

    @OnClick(R.id.btnLogin)
    void login() {
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        loginPresenter.login(username.getText().toString(), password.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter.onCreate(savedInstanceState);
    }

    @Override
    protected void setupComponent(ApplicationComponent appComponent) {
        LoginComponent loginComponent = DaggerLoginComponent
                .builder()
                .loginModule(new LoginModule(this))
                .applicationComponent(appComponent)
                .build();

        loginComponent.inject(this);
    }


    @Override
    public void initializeView() {

    }

    @Override
    public void showLoading() {
        progressDialog = ProgressDialog.show(this, "Logging in", "Please wait", true);
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void hideError() {

    }
}
