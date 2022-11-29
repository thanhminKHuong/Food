package com.example.foody.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.DownloadImg;
import com.example.foody.Model.Monan;
import com.example.foody.R;
import com.example.foody.itf.ClickItemFood;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterFoody extends RecyclerView.Adapter<AdapterFoody.ViewHolder> {

    Context context ;

    List<Monan> monanList ;
    ClickItemFood clickItemFood;
    private StorageReference storageRef ;
    private ProgressBar process ;

    public AdapterFoody(Context context, List<Monan> monanList) {
        this.context = context;
        this.monanList = monanList;
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    public AdapterFoody(Context context, List<Monan> monanList , ClickItemFood  clickItemFood ) {
        this.context = context;
        this.monanList = monanList;
        this.clickItemFood  = clickItemFood ;

        storageRef = FirebaseStorage.getInstance().getReference();

    }

    @NonNull
    @Override
    public AdapterFoody.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_it_banh,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFoody.ViewHolder holder, int position) {

        Monan monan = monanList.get(position) ;

        holder.tvName.setText(monan.getTen());

        String s = monan.getHinhanhhslide().get(0);
        Log.d("fasgdiasfas",monan.getHinhanhhslide().get(0).toString() + "");
        DownloadImg downloadImg  = new DownloadImg();

        downloadImg.taiHinhAnh(s,context,storageRef,holder.imgHinhAnh , holder.process );

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItemFood.click(monan);
                }
            });
    }

    @Override
    public int getItemCount() {
        return monanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgHinhAnh ;
        TextView tvName , tvPrice ;
        Button btnChitiet ;
        CardView cardView ;
        ProgressBar process ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgHinhAnh =  itemView.findViewById(R.id.img_HinhAnh);
            tvName  = itemView.findViewById(R.id.tv_Name);
            tvPrice = itemView.findViewById(R.id.tv_Price);
            cardView  = itemView.findViewById(R.id.itemCake);
            process = itemView.findViewById(R.id.processBarProducts);

        }
    }
}
