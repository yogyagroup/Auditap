package com.yogyagroup.auditap.ui.view;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public interface LoginView {
    void initializeView();

    void showLoading();

    void hideLoading();

    void showError();

    void hideError();
}
