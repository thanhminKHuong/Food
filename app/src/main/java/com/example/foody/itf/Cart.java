package com.example.foody.itf;

import com.example.foody.Model.GioHang;

import java.util.List;

public interface Cart {

    void getData(GioHang gioHang);
    void updateData(GioHang gioHang);
    void removeData(GioHang gioHang);

}
