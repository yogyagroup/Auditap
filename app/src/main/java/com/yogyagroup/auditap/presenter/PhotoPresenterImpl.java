package com.yogyagroup.auditap.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yogyagroup.auditap.data.API;
import com.yogyagroup.auditap.data.MessageCallback;
import com.yogyagroup.auditap.data.PhotoCallback;
import com.yogyagroup.auditap.data.PhotoUploadCallback;
import com.yogyagroup.auditap.di.ApplicationBase;
import com.yogyagroup.auditap.model.Photo;
import com.yogyagroup.auditap.ui.adapter.PhotoListAdapter;
import com.yogyagroup.auditap.ui.view.PhotoView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class PhotoPresenterImpl implements PhotoPresenter {
    private final String TAG = PhotoPresenterImpl.class.getSimpleName();
    private PhotoListAdapter photoListAdapter;
    private final API api;
    private final PhotoView photoView;
    private final Context context;
    private int receiptId;
    private int userId;
    private ArrayList<Photo> photos;

    @Inject
    SharedPreferences sharedPreferences;

    public PhotoPresenterImpl(API api, PhotoView photoView) {
        this.api = api;
        this.photoView = photoView;

        try {
            this.context = (Context)photoView;
        } catch (ClassCastException e) {
            throw new ClassCastException("photoView must be a Context");
        }

        ApplicationBase.getComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        photoView.initializeToolbar();
        photoView.initializeRecyclerView();

        userId = sharedPreferences.getInt("id", 0);
    }

    @Override
    public void onRefresh(int receiptId) {
        this.receiptId = receiptId;

        photoView.showRefreshView();

        Observable<PhotoCallback> photoCallbackObservable = api.photoByReceiptId(receiptId);
        photoCallbackObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<PhotoCallback>() {
                    @Override
                    public void onCompleted() {
                        photoView.hideRefreshView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error get list of photos: " + e.getMessage());
                        photoView.hideRefreshView();

                        Toast.makeText(context, "Something went wrong. Check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(PhotoCallback photoCallback) {
                        if (photoCallback.getPhotos().size() > 0) {
                            photos = photoCallback.getPhotos();
                            photoListAdapter = new PhotoListAdapter(photos, context);
                            photoView.initializeAdapter(photoListAdapter);
                        } else {
                            photoView.hideRefreshView();
                            Toast.makeText(context, "No memo found", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void uploadPhoto(Uri fileUri) {
        photoView.showUploadProgress();

        File file = new File(fileUri.getPath());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestBody);

        RequestBody bodyUserId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
        RequestBody bodyReceiptId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(receiptId));

        Call<PhotoUploadCallback> call = api.uploadPhoto(bodyUserId, bodyReceiptId, body);
        call.enqueue(new Callback<PhotoUploadCallback>() {
            @Override
            public void onResponse(Call<PhotoUploadCallback> call, Response<PhotoUploadCallback> response) {
                if (response.body() != null) {
                    photoView.hideUploadProgress();

                    onRefresh(receiptId);
                } else {
                    photoView.hideUploadProgress();
                }
            }

            @Override
            public void onFailure(Call<PhotoUploadCallback> call, Throwable t) {
                Log.d("Image error: ", t.toString());
                photoView.hideUploadProgress();

                Toast.makeText(context, "Something went wrong. Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void deletePhoto(Photo photo, final int position) {
        photoView.showDeleteProgress();

        Call<MessageCallback> deleteMessageCallback = api.deletePhoto(photo.getId());
        deleteMessageCallback.enqueue(new Callback<MessageCallback>() {
            @Override
            public void onResponse(Call<MessageCallback> call, Response<MessageCallback> response) {
                if (response.body().getMessage().equals("Success")) {
                    photos.remove(position);
                    photoListAdapter.notifyItemRemoved(position);
                    photoListAdapter.notifyItemRangeChanged(position, photos.size());

                    Toast.makeText(context, "Memo is deleted", Toast.LENGTH_SHORT).show();
                }
                photoView.hideDeleteProgress();
            }

            @Override
            public void onFailure(Call<MessageCallback> call, Throwable t) {
                photoView.hideDeleteProgress();

                Toast.makeText(context, "Something went wrong. Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean isUploadAllowed() {
        if (photos != null) {
            return photos.size() < 3;
        }

        return true;
    }
}
