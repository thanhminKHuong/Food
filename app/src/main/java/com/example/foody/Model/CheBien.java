package com.example.foody.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CheBien implements  Parcelable  {
    private  String tieudechebien ;
    private  String batdauchebien ;


    public CheBien() {
    }

    public CheBien(String tieudechebien, String batdauchebien) {
        this.tieudechebien = tieudechebien;
        this.batdauchebien = batdauchebien;
    }

    public static final Creator<CheBien> CREATOR = new Creator<CheBien>() {
        @Override
        public CheBien createFromParcel(Parcel in) {
            return new CheBien(in);
        }

        @Override
        public CheBien[] newArray(int size) {
            return new CheBien[size];
        }
    };


    public CheBien(String tieudechebien, String batdauchebien, String hinhanhne) {
        this.tieudechebien = tieudechebien;
        this.batdauchebien = batdauchebien;

    }

    protected CheBien(Parcel in) {
        tieudechebien = in.readString();
        batdauchebien = in.readString();
    }


    public String getTieudechebien() {
        return tieudechebien;
    }

    public void setTieudechebien(String tieudechebien) {
        this.tieudechebien = tieudechebien;
    }

    public String getBatdauchebien() {
        return batdauchebien;
    }

    public void setBatdauchebien(String batdauchebien) {
        this.batdauchebien = batdauchebien;
    }



    @Override
    public String toString() {
        return "CheBien{" +
                "tieudechebien='" + tieudechebien + '\'' +
                ", batdauchebien='" + batdauchebien + '\'' +

                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tieudechebien);
        parcel.writeString(batdauchebien);

    }
}
