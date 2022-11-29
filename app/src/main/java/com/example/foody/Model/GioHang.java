package com.example.foody.Model;

public class GioHang {

    private String check ;
    private String gia , hinhAnh , name ,id ;
    private int soLuong ;


    public GioHang() {
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "GioHang{" +
                "gia='" + gia + '\'' +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", soLuong=" + soLuong +
                '}';
    }
}
