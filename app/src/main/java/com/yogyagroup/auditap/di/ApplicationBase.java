package com.yogyagroup.auditap.di;

import android.app.Application;
import android.content.Context;

import com.yogyagroup.auditap.di.component.ApplicationComponent;
import com.yogyagroup.auditap.di.component.DaggerApplicationComponent;
import com.yogyagroup.auditap.di.module.ApplicationModule;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class ApplicationBase extends Application {
    private static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

    private void initializeInjector() {
        component = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        component.inject(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    public static ApplicationBase getApplicationBase(Context context) {
        return (ApplicationBase) context.getApplicationContext();
    }
}
