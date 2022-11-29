package com.example.foody;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DownloadImg {

    public DownloadImg() {
    }

    public void taiHinhAnh(String string , Context context , StorageReference storageRef , ImageView imageView , ProgressBar process )
    {


        process.setVisibility(View.VISIBLE);

            String s = "hinhanhfood/" + string;

            Log.d("hinhanhne",s.toString() +   " ");

            storageRef.child(s).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d("kiemtraaaaa" , uri + " ");
                    process.setVisibility(View.GONE);
                    Glide.with(context)
                            .load(uri)
                            .into(imageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("kiemtraaaaakotaidc" , e.getMessage() + " ");
                }
            });


    }


    public void taiHinhAnhNotProcess(String string , Context context , StorageReference storageRef , ImageView imageView  )
    {


//        process.setVisibility(View.VISIBLE);
//
        String s = "hinhanhfood/" + string;

        Log.d("hinhanhne",s.toString() +   " ");

        storageRef.child(s).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("kiemtraaaaa" , uri + " ");
//                process.setVisibility(View.GONE);
                Glide.with(context)
                        .load(uri)
                        .into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("kiemtraaaaakotaidc" , e.getMessage() + " ");
            }
        });


    }

}
