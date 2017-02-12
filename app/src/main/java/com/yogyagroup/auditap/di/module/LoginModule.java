package com.yogyagroup.auditap.di.module;

import com.yogyagroup.auditap.data.API;
import com.yogyagroup.auditap.presenter.LoginPresenter;
import com.yogyagroup.auditap.presenter.LoginPresenterImpl;
import com.yogyagroup.auditap.ui.view.LoginView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

@Module(includes = ApiModule.class)
public class LoginModule {
    private LoginView loginView;

    public LoginModule(LoginView loginView) {
        this.loginView = loginView;
    }

    @Provides
    LoginPresenter provideLoginPresenter(API api) {
        return new LoginPresenterImpl(api, loginView);
    }
}
