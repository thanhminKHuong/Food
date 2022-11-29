package com.example.foody.itf;

import com.example.foody.Model.GioHang;

import java.util.List;

public interface getDataCart {

    void getDataCart(List<String> tenNguyenLieus ,  List<GioHang> gioHangList);
    void updateDataCart(GioHang gioHang);
    void deleteDataCart(GioHang gioHang);

}
