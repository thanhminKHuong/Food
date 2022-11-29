package com.example.foody.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.foody.Adapter.AdapterGioHang;
import com.example.foody.Model.GioHang;
import com.example.foody.R;
import com.example.foody.itf.Cart;
import com.example.foody.itf.itfCartItem;
import com.example.foody.itf.itfToltalPrice;
import com.example.foody.itf.removeItemCart;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GioHangFragment extends Fragment {

   private  View view ;
    private   RecyclerView rcv_gioHang ;
    private   AdapterGioHang adapterGioHang ;
    private   List<GioHang>  gioHangList ;
    private DatabaseReference myRef ;
    private com.example.foody.itf.itfCartItem itfCartItem ;
    private  Cart cart ;
    private ArrayList<GioHang> dsGioHang ;
    private AlertDialog.Builder alertDialog ;
    TextView tvTolTalPrice ;
    Button btnPayment ;
    double Price = 0 ;
    int i = 0 ;
    com.example.foody.itf.removeItemCart removeItemCart ;
    com.example.foody.itf.itfToltalPrice itfToltalPrice  ;
    DecimalFormat df ;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_giohang,container,false);

        myRef  = FirebaseDatabase.getInstance().getReference();
        tvTolTalPrice = view.findViewById(R.id.tv_ToltalPrice);

        btnPayment = view.findViewById(R.id.btnTHANHtOAN);
        rcv_gioHang = view.findViewById(R.id.rcv_fragment_giohang);
        gioHangList = new ArrayList<>();
   alertDialog = new AlertDialog.Builder(getContext());
        df  = new DecimalFormat("###,###,###") ;
        dsGioHang =  new ArrayList<>();
        ArrayList<Double> doubles = new ArrayList<>();



       itfToltalPrice = new itfToltalPrice() {
           @Override
           public void getData(Double aDouble) {
               if(aDouble == 0.0)
               {
                   tvTolTalPrice.setText("0.0");
               }else {
                   Price = aDouble ;
                   tvTolTalPrice.setText(df.format(aDouble) + "đ");
               }
           }

           @Override
           public void updateData(GioHang gioHang) {
              myRef.child("giohang").child(TrangChuFragment.uid).child(gioHang.getId()).setValue(gioHang);
           }
       };

        itfCartItem = new itfCartItem() {
            @Override
            public void addCart(GioHang gioHang) {

                Map<String,Object> stringGioHangHashMap = new HashMap<String,Object>();
                GioHang gioHang1 = new GioHang();
                gioHang1.setCheck("1");
                gioHang1.setSoLuong(gioHang.getSoLuong());
                gioHang1.setName(gioHang.getName());
                gioHang1.setId(gioHang.getId());
                gioHang1.setHinhAnh(gioHang.getHinhAnh());
                gioHang1.setSoLuong(gioHang.getSoLuong());
                gioHang1.setGia(gioHang.getGia());
                stringGioHangHashMap.put(gioHang.getId(),gioHang1);
                myRef.child("giohang").child(TrangChuFragment.uid).updateChildren(stringGioHangHashMap);

            }
            @Override
            public void removeCart(GioHang gioHang) {

                   double b = gioHang.getSoLuong() * Integer.parseInt(gioHang.getGia().trim());
                   Price -= b ;
                   tvTolTalPrice.setText(Price + " ");
                   Map<String,Object> stringGioHangHashMap = new HashMap<String,Object>();

                   GioHang gioHang1 = new GioHang();
                   gioHang1.setCheck("0");
                   gioHang1.setSoLuong(gioHang.getSoLuong());
                   gioHang1.setName(gioHang.getName());
                   gioHang1.setId(gioHang.getId());
                   gioHang1.setHinhAnh(gioHang.getHinhAnh());
                   gioHang1.setSoLuong(gioHang.getSoLuong());
                   gioHang1.setGia(gioHang.getGia());
                   stringGioHangHashMap.put(gioHang.getId(),gioHang1);
                   myRef.child("giohang").child(TrangChuFragment.uid).updateChildren(stringGioHangHashMap);

            }
        };



          removeItemCart = new removeItemCart() {
              @Override
              public void removeItemisCheck(GioHang gioHang) {
                  Log.d("kiemtragiatricuano",gioHang.getName() + "đang được check ");
                  gioHangList.remove(gioHang);
                  HashMap<String,GioHang> stringStringHashMap = new HashMap<>();
                  for(int i = 0 ; i < gioHangList.size() ; i++)
                  {
                      stringStringHashMap.put(gioHangList.get(i).getId(),gioHangList.get(i));
                  }
                  myRef.child("giohang").child(TrangChuFragment.uid).setValue(stringStringHashMap);

                  Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                  adapterGioHang.notifyDataSetChanged();
              }

              @Override
              public void removeItemNotCheck(GioHang gioHang) {

                  Log.d("kiemtragiatricuano",gioHang.getName() + "không được check");
                  gioHangList.remove(gioHang);
                  adapterGioHang.notifyDataSetChanged();
                  HashMap<String,GioHang> stringStringHashMap = new HashMap<>();
                  for(int i = 0 ; i < gioHangList.size() ; i++)
                  {
                      stringStringHashMap.put(gioHangList.get(i).getId(),gioHangList.get(i));
                  }
                  myRef.child("giohang").child(TrangChuFragment.uid).setValue(stringStringHashMap);
                  Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
              }
          };



        adapterGioHang = new AdapterGioHang(gioHangList,getContext() , itfCartItem , removeItemCart , itfToltalPrice);
        adapterGioHang.notifyDataSetChanged();
        tvTolTalPrice.setText(String.valueOf(AdapterGioHang.toltalPrice));
        Log.d("kiemtradulieucuaman" , AdapterGioHang.toltalPrice + " ");
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rcv_gioHang.setLayoutManager(gridLayoutManager);
        rcv_gioHang.setAdapter(adapterGioHang);

        cart = new Cart() {
            @Override
            public void getData(GioHang gioHang) {
               gioHangList.add(gioHang);
               adapterGioHang.notifyDataSetChanged();
               Log.d("kiemtradulieumang add",gioHangList.size() + " ");
            }

            @Override
            public void updateData(GioHang gioHang)
            {
                for(int i= 0 ; i < gioHangList.size() ; i++)
                {
                    if(gioHangList.get(i).getId().equals(gioHang.getId()))
                    {

                        gioHangList.set(i,gioHang);
                        adapterGioHang.notifyDataSetChanged();
                        Log.d("kiemtradulieumang update",gioHangList.size() + " ");
                    }
                }

            }

            @Override
            public void removeData(GioHang gioHang)
            {
              for(int i= 0 ; i < gioHangList.size() ; i++)
              {
                  if(gioHangList.get(i).getId().equals(gioHang.getId()))
                  {
                      Log.d("óidhfoasdhofahsidoifhsaoihdf","dsfjspdjfpasfhipas");
                      gioHangList.remove(i);
                      adapterGioHang.notifyDataSetChanged();
                  }
              }
                Log.d("kiemtradulieumang remove",gioHangList.size() + " ");
            }
        };

        getDataCart();


        return  view;
    }


    private void getDataCart()
    {
        ChildEventListener childEventListener = new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName)
            {
              GioHang gioHang = snapshot.getValue(GioHang.class);
              gioHang.setId(snapshot.getKey());
              cart.getData(gioHang);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName)
            {

                GioHang gioHang = snapshot.getValue(GioHang.class);
                gioHang.setId(snapshot.getKey());
                cart.updateData(gioHang);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot)
            {
                 GioHang gioHang = snapshot.getValue(GioHang.class);
                 gioHang.setId(snapshot.getKey());
                 cart.removeData(gioHang);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        };

        myRef.child("giohang").child(TrangChuFragment.uid).addChildEventListener(childEventListener);



    }
}
