package com.yogyagroup.auditap.di.component;

import com.yogyagroup.auditap.di.PerActivity;
import com.yogyagroup.auditap.di.module.MainModule;
import com.yogyagroup.auditap.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
