package com.yogyagroup.auditap.di.component;

import com.yogyagroup.auditap.di.ApplicationBase;
import com.yogyagroup.auditap.di.module.ApplicationModule;
import com.yogyagroup.auditap.presenter.LoginPresenterImpl;
import com.yogyagroup.auditap.presenter.MainPresenterImpl;
import com.yogyagroup.auditap.presenter.PhotoPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(ApplicationBase applicationBase);

    void inject(MainPresenterImpl mainPresenter);

    void inject(LoginPresenterImpl loginPresenter);

    void inject(PhotoPresenterImpl photoPresenter);
}
