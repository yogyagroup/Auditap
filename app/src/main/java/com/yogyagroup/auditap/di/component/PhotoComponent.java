package com.yogyagroup.auditap.di.component;

import com.yogyagroup.auditap.di.PerActivity;
import com.yogyagroup.auditap.di.module.PhotoModule;
import com.yogyagroup.auditap.ui.activity.PhotoActivity;

import dagger.Component;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PhotoModule.class)
public interface PhotoComponent {
    void inject(PhotoActivity photoActivity);
}
