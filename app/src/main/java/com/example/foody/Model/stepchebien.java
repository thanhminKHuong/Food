package com.example.foody.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class stepchebien extends ArrayList<String> implements Parcelable {
     private String thuchanh ;
     private String tieude ;

     public stepchebien() {
     }

     public stepchebien(String thuchanh, String tieude) {
          this.thuchanh = thuchanh;
          this.tieude = tieude;
     }

     protected stepchebien(Parcel in) {
          thuchanh = in.readString();
          tieude = in.readString();
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(thuchanh);
          dest.writeString(tieude);
     }

     @Override
     public int describeContents() {
          return 0;
     }

     public static final Creator<stepchebien> CREATOR = new Creator<stepchebien>() {
          @Override
          public stepchebien createFromParcel(Parcel in) {
               return new stepchebien(in);
          }

          @Override
          public stepchebien[] newArray(int size) {
               return new stepchebien[size];
          }
     };

     public String getThuchanh() {
          return thuchanh;
     }

     public void setThuchanh(String thuchanh) {
          this.thuchanh = thuchanh;
     }

     public String getTieude() {
          return tieude;
     }

     public void setTieude(String tieude) {
          this.tieude = tieude;
     }

     @Override
     public String toString() {
          return "stepchebien{" +
                  "thuchanh='" + thuchanh + '\'' +
                  ", tieude='" + tieude + '\'' +
                  '}';
     }
}
