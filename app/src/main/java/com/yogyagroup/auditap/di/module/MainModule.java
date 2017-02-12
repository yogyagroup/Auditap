package com.yogyagroup.auditap.di.module;

import android.content.Context;

import com.yogyagroup.auditap.data.API;
import com.yogyagroup.auditap.presenter.MainPresenter;
import com.yogyagroup.auditap.presenter.MainPresenterImpl;
import com.yogyagroup.auditap.ui.view.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

@Module(includes = ApiModule.class)
public class MainModule {
    private MainView mainView;
    private Context context;

    public MainModule(MainView mainView) {
        this.mainView = mainView;

        try {
            this.context = (Context) mainView;
        } catch (ClassCastException e) {
            throw new ClassCastException("mainView should be a Context as well");
        }
    }

    @Provides
    MainPresenter provideMainPresenter(API api) {
        return new MainPresenterImpl(api, mainView, context);
    }
}
