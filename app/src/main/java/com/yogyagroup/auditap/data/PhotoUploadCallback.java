package com.yogyagroup.auditap.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class PhotoUploadCallback {
    private String message;
    private boolean success;
    @SerializedName("photo_url")
    private String PhotoUrl;

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
