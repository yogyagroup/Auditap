package com.yogyagroup.auditap.di.component;

import com.yogyagroup.auditap.di.PerActivity;
import com.yogyagroup.auditap.di.module.LoginModule;
import com.yogyagroup.auditap.ui.activity.LoginActivity;

import dagger.Component;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
