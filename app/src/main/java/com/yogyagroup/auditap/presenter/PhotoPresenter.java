package com.yogyagroup.auditap.presenter;

import android.net.Uri;
import android.os.Bundle;

import com.yogyagroup.auditap.model.Photo;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public interface PhotoPresenter {
    void onCreate(Bundle savedInstanceState);

    void onRefresh(int receiptId);

    void uploadPhoto(Uri fileUri);

    void deletePhoto(Photo photo, int position);

    boolean isUploadAllowed();
}
