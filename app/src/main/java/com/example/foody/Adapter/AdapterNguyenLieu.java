package com.example.foody.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foody.DownloadImg;
import com.example.foody.Model.Monan;
import com.example.foody.Model.NguyenLieu;
import com.example.foody.R;
import com.example.foody.itf.addCart;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterNguyenLieu extends RecyclerView.Adapter<AdapterNguyenLieu.ViewHolder> {

    private StorageReference storageRef;
    private List<NguyenLieu> nguyenLieus ;
    private Context context ;
    private com.example.foody.itf.addCart addCart ;
//   public static  int i = 1 ;


    public AdapterNguyenLieu(List<NguyenLieu> nguyenLieus, Context context , addCart addCart) {
        this.nguyenLieus = nguyenLieus;
        this.context = context;
        storageRef = FirebaseStorage.getInstance().getReference();
        this.addCart = addCart ;
    }

    @NonNull
    @Override
    public AdapterNguyenLieu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_nguyenlieu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNguyenLieu.ViewHolder holder, int position) {



        NguyenLieu nguyenLieu = nguyenLieus.get(position);
        holder.tv_price.setText(nguyenLieu.getGia());
        holder.tv_name.setText(nguyenLieu.getName());
        holder.index_Product.setText("1");



        holder.img_daucong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.i = ++holder.i ;
                holder.index_Product.setText(String.valueOf(holder.i));
            }
        });
        holder.img_dautru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(holder.i > 1)
             {
                 holder.i = --holder.i ;
                 holder.index_Product.setText(String.valueOf(holder.i));
             }
            }
        });



        Log.d("okk",nguyenLieu.getHinhAnh() + " " );

        String s = nguyenLieu.getHinhAnh().trim();
        DownloadImg downloadImg = new DownloadImg();
        downloadImg.taiHinhAnh(s,context,storageRef,holder.imageView , holder.process);


        holder.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addCart.addCart(nguyenLieu , String.valueOf(holder.index_Product.getText()));
               holder.index_Product.setText("1");
               holder.i = 1 ;
            }
        });
    }

    @Override
    public int getItemCount() {
        return nguyenLieus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private  ImageView imageView , img_dautru , img_daucong;


        private TextView tv_name , tv_price ;
        private ProgressBar process ;
        private Button btnAddCart ;
        public TextView index_Product ;
        private int i = 1 ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_HinhAnh);
            tv_name = itemView.findViewById(R.id.tv_Name);
            tv_price = itemView.findViewById(R.id.tv_Price);
            process = itemView.findViewById(R.id.processBarProducts);
            btnAddCart = itemView.findViewById(R.id.btn_AddtoCart);
            img_daucong = itemView.findViewById(R.id.img_daucong);
            img_dautru = itemView.findViewById(R.id.img_dautru);
            index_Product = itemView.findViewById(R.id.index_product);

        }
    }
}
