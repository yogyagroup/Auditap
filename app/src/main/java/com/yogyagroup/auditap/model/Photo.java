package com.yogyagroup.auditap.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class Photo implements Parcelable {

    int id;
    Receipt receipt;
    String path;
    String caption;
    String created;

    protected Photo(Parcel in) {
        id = in.readInt();
        receipt = in.readParcelable(Receipt.class.getClassLoader());
        path = in.readString();
        caption = in.readString();
        created = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeParcelable(receipt, i);
        parcel.writeString(path);
        parcel.writeString(caption);
        parcel.writeString(created);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
