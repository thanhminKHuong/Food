package com.example.foody.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;

import java.util.ArrayList;

public class AdapterSelectImg  extends RecyclerView.Adapter<AdapterSelectImg.ViewHolder>{

    private ArrayList list;

    public AdapterSelectImg(ArrayList list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterSelectImg.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.custom_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSelectImg.ViewHolder holder, int position) {
        holder.imageView.setImageURI((Uri) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
