package com.yogyagroup.auditap.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.yogyagroup.auditap.di.ApplicationBase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

@Module
public class ApplicationModule {
    private ApplicationBase application;

    public ApplicationModule(ApplicationBase application) {
        this.application = application;
    }

    @Provides
    @Singleton
    ApplicationBase provideApplicationBase() {
        return application;
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return application.getSharedPreferences("login", Context.MODE_PRIVATE);
    }
}
