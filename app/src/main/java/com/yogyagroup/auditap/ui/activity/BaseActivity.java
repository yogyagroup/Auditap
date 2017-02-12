package com.yogyagroup.auditap.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yogyagroup.auditap.di.ApplicationBase;
import com.yogyagroup.auditap.di.component.ApplicationComponent;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupComponent(ApplicationBase.getApplicationBase(this).getComponent());
    }

    protected abstract void setupComponent(ApplicationComponent appComponent);
}
