package com.example.foody.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Nguoidung implements Parcelable {

    private String id ;
    private String name ;
    private String nameLogin ;
    private String email ;
    private String passwords ;


    public Nguoidung(String name ,  String email) {
        this.name = name;
        this.email = email;
    }

    public Nguoidung(String name, String nameLogin, String email, String passwords) {
        this.name = name;
        this.nameLogin = nameLogin;
        this.email = email;
        this.passwords = passwords;
    }

    protected Nguoidung(Parcel in) {
        name = in.readString();
        nameLogin = in.readString();
        email = in.readString();
        passwords = in.readString();
    }

    public static final Creator<Nguoidung> CREATOR = new Creator<Nguoidung>() {
        @Override
        public Nguoidung createFromParcel(Parcel in) {
            return new Nguoidung(in);
        }

        @Override
        public Nguoidung[] newArray(int size) {
            return new Nguoidung[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameLogin() {
        return nameLogin;
    }

    public void setNameLogin(String nameLogin) {
        this.nameLogin = nameLogin;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(nameLogin);
        parcel.writeString(email);
        parcel.writeString(passwords);
    }

    @Override
    public String toString() {
        return "Nguoidung{}";
    }
}


