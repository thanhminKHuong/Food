package com.example.foody.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foody.DownloadImg;
import com.example.foody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;





public class AdapterRCVChiTiet extends RecyclerView.Adapter<AdapterRCVChiTiet.ViewHolder> {

    private StorageReference storageRef;
    private Context context;
    private List<String> stringList;
    private DownloadImg downloadImg;

    public AdapterRCVChiTiet(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    @NonNull
    @Override
    public AdapterRCVChiTiet.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rcv_chitietmonan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRCVChiTiet.ViewHolder holder, int position) {


        String s = "hinhanhfood/" + stringList.get(position);

        storageRef.child(s).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context.getApplicationContext())
                        .load(uri)
                        .into(holder.imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_rcvchitiet);

        }
    }
}
