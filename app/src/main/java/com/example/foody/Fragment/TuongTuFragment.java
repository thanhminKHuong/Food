package com.example.foody.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.foody.Adapter.AdapterFoody;
import com.example.foody.Adapter.AdapterNguyenLieu;
import com.example.foody.Model.GioHang;
import com.example.foody.Model.Monan;
import com.example.foody.Model.NguyenLieu;
import com.example.foody.R;
import com.example.foody.View.ChitietActivity;
import com.example.foody.itf.addCart;
import com.example.foody.itf.getDataCart;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TuongTuFragment extends Fragment {

    RecyclerView rcv_NguyenLieu ;
    TextView tv_totalCard ;
    List<NguyenLieu> nguyenLieus ;
    AdapterNguyenLieu adapterNguyenLieu ;
    Monan monan ;
    com.example.foody.itf.addCart addCart ;
    List<GioHang> gioHangList ;
    DatabaseReference myRef ;
    com.example.foody.itf.getDataCart getDataCart ;
    List<String> getGioHangList ;
    View view ;

    public TuongTuFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_order,container,false);

        myRef = FirebaseDatabase.getInstance().getReference();

         monan = ChitietActivity.monan ;


         getGioHangList = new ArrayList<>();
        gioHangList = new ArrayList<>();
        List<GioHang>  gioHangList = new ArrayList<>();

      getDataCart = new getDataCart() {
          @Override
          public void getDataCart(List<String> tenNguyenLieus ,  List<GioHang> gioHangList) {

              getGioHangList.addAll(tenNguyenLieus);

              Log.d("iemtrafiohasodfasdfsdf" , getGioHangList.size() + " ");

              for(String string : getGioHangList)
              {
                  Log.d("iemtrafiohasodfasdfsdf1234565789" , string + " ");
              }

              gioHangList.addAll(gioHangList);

          }

          @Override
          public void updateDataCart(GioHang gioHang) {

//              Log.d("update","co su thay doi");
              for(int i = 0 ; i < gioHangList.size() ; i++)
              {
                  if(gioHangList.get(i).getId().equals(gioHang.getId()))
                  {
                      gioHangList.set(i,gioHang);
                      Log.d("update",getGioHangList.get(i).toString() + " " + getGioHangList.size());
                  }
              }
          }

          @Override
          public void deleteDataCart(GioHang gioHang) {

//              for(int i = 0 ; i < getGioHangList.size() ; i++)
//              {
//                  if(getGioHangList.get(i).getId().equals(gioHang.getId()))
//                  {
//                      getGioHangList.remove(i);
//                  }
//              }

          }
      };

        ArrayList<String> tenNguyenLieu = new ArrayList<>();
        ArrayList<GioHang> hangs = new ArrayList<>();
        if(TrangChuFragment.uid != null)
        {

            ChildEventListener getDataCart1 = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    tenNguyenLieu.clear();
                    GioHang gioHang = snapshot.getValue(GioHang.class);
                        gioHang.setId(snapshot.getKey());
                    tenNguyenLieu.add(gioHang.getName().trim());
                    Log.d("fsfasfsdf",tenNguyenLieu.size() + " ");
                    hangs.add(gioHang);



                   getDataCart.getDataCart(tenNguyenLieu,hangs);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    GioHang gioHang = snapshot.getValue(GioHang.class);
                    Log.d("kiemtradulieunemioi",gioHang.getName() + " ");
                    gioHang.setId(snapshot.getKey());
                    getDataCart.updateDataCart(gioHang);
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    GioHang gioHang = snapshot.getValue(GioHang.class);
                    gioHang.setId(snapshot.getKey());
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            myRef.child("giohang").child(TrangChuFragment.uid).addChildEventListener(getDataCart1);
        }else
        {
            Log.d("khongcodulieu","Ban chua dang nhap");
        }

        addCart = new addCart() {
            @Override
            public void addCart(NguyenLieu nguyenLieu, String s) {

                if(TrangChuFragment.uid != null)
                {

                    Toast.makeText(getContext(), " " + getGioHangList.size()   , Toast.LENGTH_SHORT).show();
                    if(getGioHangList.contains(nguyenLieu.getName().trim()))
                    {

                        for(int i = 0 ; i < hangs.size() ; i++)
                        {
                            if(hangs.get(i).getName().equals(nguyenLieu.getName()))
                            {
                                GioHang gioHang = new GioHang();
                                gioHang.setGia(nguyenLieu.getGia());
                                gioHang.setHinhAnh(nguyenLieu.getHinhAnh());
                                gioHang.setName(nguyenLieu.getName());
                                gioHang.setCheck("0");
                                gioHang.setId(hangs.get(i).getId());
                                int productBanDau = hangs.get(i).getSoLuong()  ;
                                int productHienTai = Integer.parseInt(s);

                                gioHang.setSoLuong(productHienTai+productBanDau);

                                Log.d("kiemtradulieumang22",s + " ");

                                myRef.child("giohang").child(TrangChuFragment.uid).child(gioHang.getId()).setValue(gioHang);

                            }
                        }
                    }
                    else if(!getGioHangList.contains(nguyenLieu.getName().trim()))
                    {
                        Log.d("khongcodulieu", nguyenLieu.getName() + "ko co du lieu ");
                        Log.d("kiemtradulieumang",getGioHangList.size() + " ");
                        GioHang gioHang = new GioHang();
                        gioHang.setGia(nguyenLieu.getGia());
                        gioHang.setCheck("0");
                        gioHang.setHinhAnh(nguyenLieu.getHinhAnh());
                        gioHang.setName(nguyenLieu.getName());
                        gioHang.setSoLuong(Integer.valueOf(s));


                        String key = myRef.child(TrangChuFragment.uid).push().getKey();
                        gioHang.setId(key);

                        Log.d("kiemtradulieucuakeynemioi",key.toString() + " ");

                        myRef.child("giohang").child(TrangChuFragment.uid).child(key).setValue(gioHang);
                    }

                }else if(TrangChuFragment.uid == null)
                {
                    Toast.makeText(getContext(), "Vui lòng đăng nhập để xử dụng tính năng", Toast.LENGTH_SHORT).show();
                }
            }
        };

         rcv_NguyenLieu = view.<RecyclerView>findViewById(R.id.rcv_nguyenlieu);
         tv_totalCard = view.<TextView>findViewById(R.id.tv_totalCard);
          nguyenLieus = new ArrayList<>();
         adapterNguyenLieu = new AdapterNguyenLieu(nguyenLieus,getContext(),addCart);
        adapterNguyenLieu.notifyDataSetChanged();
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcv_NguyenLieu.setLayoutManager(gridLayoutManager);
        rcv_NguyenLieu.setAdapter(adapterNguyenLieu);

        for(int i = 0 ; i < monan.getNguyenlieu().size() ; i++)
        {
            nguyenLieus.add(monan.getNguyenlieu().get(i));
//            Log.d("fsofhoasidfhoisahdfo",monan.getNguyenlieu().get(i) + " ");
            adapterNguyenLieu.notifyDataSetChanged();
        }


         return view;
    }
}
