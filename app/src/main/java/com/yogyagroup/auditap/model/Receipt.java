package com.yogyagroup.auditap.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class Receipt implements Parcelable {

    int id;
    String name;
    String address;
    String brand;
    String color;
    String leasing;
    String date;
    String dp_po;
    int memo;

    protected Receipt(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        brand = in.readString();
        color = in.readString();
        memo = in.readInt();
        leasing = in.readString();
        date = in.readString();
        dp_po = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(brand);
        dest.writeString(color);
        dest.writeInt(memo);
        dest.writeString(leasing);
        dest.writeString(date);
        dest.writeString(dp_po);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Receipt> CREATOR = new Creator<Receipt>() {
        @Override
        public Receipt createFromParcel(Parcel in) {
            return new Receipt(in);
        }

        @Override
        public Receipt[] newArray(int size) {
            return new Receipt[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) { this.color = color; }

    public int getMemo() {
        return memo;
    }

    public void setMemo(int memo) {
        this.memo = memo;
    }

    public String getLeasing() {
        return leasing;
    }

    public void setLeasing(String leasing) {
        this.leasing = leasing;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDp_po() {
        return dp_po;
    }

    public void setDp_po(String dp_po) {
        this.dp_po = dp_po;
    }
}
