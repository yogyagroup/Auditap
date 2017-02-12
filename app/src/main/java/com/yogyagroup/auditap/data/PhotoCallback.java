package com.yogyagroup.auditap.data;

import com.yogyagroup.auditap.model.Photo;

import java.util.ArrayList;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class PhotoCallback {
    private String message;
    private ArrayList<Photo> photos;
    private boolean success;

    public String getMessage() { return message; }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
