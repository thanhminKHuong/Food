package com.example.foody.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;
import com.example.foody.itf.vitriitemclick;

import java.util.List;

public class AdapterRCV extends RecyclerView.Adapter<AdapterRCV.ViewHolder> {

    private  int stringList ;
    private Context context ;
    private com.example.foody.itf.vitriitemclick vitriitemclick ;

    public AdapterRCV(int stringList, Context context , vitriitemclick  vitriitemclick) {
        this.stringList = stringList;
        this.context = context;
        this.vitriitemclick = vitriitemclick ;
    }

    @NonNull
    @Override
    public AdapterRCV.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_products,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRCV.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

//        String popular = stringList.get(position);
        holder.textView.setText("Chọn hình ảnh cho bước thứ :  " + position);
        holder.itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vitriitemclick.vitri(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringList ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view ;
        TextView textView ;
        ImageView itemImg ;
        Button btnBoQua ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.tv_tieude);
            itemImg = itemView.findViewById(R.id.imageView);

        }
    }

}
