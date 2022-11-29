package com.example.foody.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.DownloadImg;
import com.example.foody.Model.GioHang;
import com.example.foody.R;
import com.example.foody.itf.itfCartItem;
import com.example.foody.itf.itfToltalPrice;
import com.example.foody.itf.removeItemCart;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterGioHang extends RecyclerView.Adapter<AdapterGioHang.ViewHolder> {


   private  List<GioHang> gioHangList;
  private  Context  context ;
    public static  double toltalPrice =0 ;
    public static double itemPrice = 0 ;
    private StorageReference storageRef;
    private com.example.foody.itf.itfCartItem itfCartItem ;
    com.example.foody.itf.removeItemCart removeItemCart ;
    com.example.foody.itf.itfToltalPrice itfToltalPrice ;

    DecimalFormat df ;



    public AdapterGioHang(List<GioHang> gioHangList, Context context , com.example.foody.itf.itfCartItem itfCartItem ,com.example.foody.itf.removeItemCart removeItemCart , com.example.foody.itf.itfToltalPrice itfToltalPrice ) {
        this.gioHangList = gioHangList;
        storageRef = FirebaseStorage.getInstance().getReference();
        this.context = context;
        this.itfCartItem = itfCartItem ;
        this.removeItemCart = removeItemCart;
        this.itfToltalPrice = itfToltalPrice ;
        itemPrice = 0 ;
         toltalPrice = 0 ;
        Log.d("kiemtranoschayrasao","dsfaoihsdf -- constructor");
        df  = new DecimalFormat("###,###,###") ;

    }


    @NonNull
    @Override
    public AdapterGioHang.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_giohang2, parent, false);
        Log.d("kiemtranoschayrasao","dsfaoihsdf -- CreateViewHolder");
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGioHang.ViewHolder holder, int position) {

        Log.d("kiemtranoschayrasao","dsfaoihsdf -- Onbinh");
        GioHang gioHang = gioHangList.get(position);
        holder.tv_Name.setText(gioHang.getName());
        holder.tv_Gia.setText(df.format(Double.parseDouble(gioHang.getGia())) + "đ");
        holder.indexProduct.setText(String.valueOf(gioHang.getSoLuong()));
        DownloadImg downloadImg  = new DownloadImg();

        String s = gioHang.getHinhAnh().trim();

        downloadImg.taiHinhAnhNotProcess(s,context,storageRef,holder.img_HinhAnh);

        String indexBanDau = holder.indexProduct.getText().toString() ;

        if(gioHang.getCheck().equals("0"))
        {
            Log.d("kiemtradulieucaumangene,màopdojifs",gioHang.getName() + " ");
            holder.cb_select.setChecked(false);

        }
        else if(gioHang.getCheck().equals("1"))
        {

            itemPrice = Double.parseDouble(gioHang.getGia()) * Double.parseDouble(String.valueOf(gioHang.getSoLuong()));
            toltalPrice += itemPrice ;
            itfToltalPrice.getData(toltalPrice);
            holder.cb_select.setChecked(true);

            Log.d("dsfsadf", toltalPrice + " ");

        }



        holder.cb_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CompoundButton) v).isChecked()){
                    itemPrice = 0 ;
                    toltalPrice = 0 ;
                    int s = Integer.parseInt(holder.indexProduct.getText().toString()) ;
                    gioHang.setSoLuong(s);
                    Log.d("kliemoiahfoisdhfas", gioHang.getName() + " ");
                    itfCartItem.addCart(gioHang);
                } else {

                    itemPrice = Double.parseDouble(gioHang.getGia()) * Double.parseDouble(holder.indexProduct.getText().toString());
                    toltalPrice = toltalPrice -  itemPrice ;
                    Log.d("kjiemtrad",toltalPrice + " " + itemPrice);
                    itfToltalPrice.getData(toltalPrice);
                    itemPrice = 0 ;
                    toltalPrice = 0 ;
                    int s = Integer.parseInt(holder.indexProduct.getText().toString());
                    gioHang.setSoLuong(s);
                    itfCartItem.removeCart(gioHang);
                }
            }
        });
       holder.img_RemoveItemCart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(holder.cb_select.isChecked())
               {
                   itemPrice = Double.parseDouble(gioHang.getGia()) * Double.parseDouble(holder.indexProduct.getText().toString());

                   toltalPrice = toltalPrice -  itemPrice ;
                   Log.d("kjiemtrad",toltalPrice + " " + itemPrice);
                   itfToltalPrice.getData(toltalPrice);
                   itemPrice = 0 ;
                   toltalPrice = 0 ;

                   removeItemCart.removeItemisCheck(gioHang);

               }else {
                   itemPrice = 0 ;
                   toltalPrice = 0 ;
                   removeItemCart.removeItemNotCheck(gioHang);
               }
           }
       });

       holder.indexProduct.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {

               if(!hasFocus)
               {

                  Log.d("kiemtradulieucuaindexproducts", holder.indexProduct.getText() + " ");
                  if(!holder.indexProduct.getText().equals(indexBanDau))
                  {
                      double itemPrice22 = Double.parseDouble(gioHang.getGia()) * Double.parseDouble(String.valueOf(indexBanDau));
                      toltalPrice -= itemPrice22 ;
                      itemPrice = Double.parseDouble(gioHang.getGia()) * Double.parseDouble(String.valueOf(holder.indexProduct.getText()));
                      toltalPrice += itemPrice ;
                      itfToltalPrice.getData(toltalPrice);
                      gioHang.setSoLuong(Integer.parseInt(holder.indexProduct.getText().toString()));
                      itfToltalPrice.updateData(gioHang);
                      toltalPrice = 0 ;
                      itemPrice = 0 ;
                  }else
                  {

                  }
               }
           }
       });



    }

    @Override
    public int getItemCount() {
        Log.d("kiemtranoschayrasao","dsfaoihsdf -- getList");
        return gioHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Name , tv_Gia  ;
        EditText indexProduct ;
        ImageView img_HinhAnh , img_DauTru , img_DauCong  , img_RemoveItemCart;
        CheckBox cb_select ;
        String s ;
//        ProgressBar process ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("kiemtranoschayrasao","dsfaoihsdf -- ViewHolder");
            tv_Name = itemView.findViewById(R.id.tv_name);
            tv_Gia = itemView.findViewById(R.id.tv_price);
            img_HinhAnh = itemView.findViewById(R.id.img_hinhanh);
            indexProduct = itemView.findViewById(R.id.index_product);
            img_DauCong = itemView.findViewById(R.id.img_daucong);
            img_DauTru  = itemView.findViewById(R.id.img_dautru);
            cb_select = itemView.findViewById(R.id.checkBox_GioHang);
            img_RemoveItemCart = itemView.findViewById(R.id.img_xoasanpham);
//            process = itemView.findViewById(R.id.process);

        }
    }
}
