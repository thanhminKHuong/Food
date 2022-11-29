package com.example.foody.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

public class NguyenLieu implements Parcelable {


    private String gia ;
    private String hinhAnh ;
    private String name ;
    private String id ;

    public NguyenLieu() {
    }

    protected NguyenLieu(Parcel in) {
        gia = in.readString();
        hinhAnh = in.readString();
        name = in.readString();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gia);
        dest.writeString(hinhAnh);
        dest.writeString(name);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NguyenLieu> CREATOR = new Creator<NguyenLieu>() {
        @Override
        public NguyenLieu createFromParcel(Parcel in) {
            return new NguyenLieu(in);
        }

        @Override
        public NguyenLieu[] newArray(int size) {
            return new NguyenLieu[size];
        }
    };

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
