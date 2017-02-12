package com.yogyagroup.auditap.di.module;

import com.yogyagroup.auditap.data.API;
import com.yogyagroup.auditap.presenter.PhotoPresenter;
import com.yogyagroup.auditap.presenter.PhotoPresenterImpl;
import com.yogyagroup.auditap.ui.view.PhotoView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

@Module(includes = ApiModule.class)
public class PhotoModule {
    private PhotoView photoView;

    public PhotoModule(PhotoView photoView) {
        this.photoView = photoView;
    }

    @Provides
    PhotoPresenter providePhotoPresenter(API api) {
        return new PhotoPresenterImpl(api, photoView);
    }
}
