package com.example.foody.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.foody.Model.SlideDetails;
import com.example.foody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class SliderAdapterDetail extends PagerAdapter {

    private StorageReference storageRef ;
    Context context ;
    List<SlideDetails> slideList ;

    public SliderAdapterDetail(Context context, List<SlideDetails> slideList) {
        this.context = context;
        this.slideList = slideList;
        storageRef = FirebaseStorage.getInstance().getReference();

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderLayout = inflater.inflate(R.layout.layout_slide_details,null);

        ImageView featured_image = sliderLayout.findViewById(R.id.my_featured_image);
//        TextView caption_title = sliderLayout.findViewById(R.id.my_caption_title);

        String s = "hinhanhfood/" + slideList.get(position).getImg();
        Log.d("kiemtraphantucuaadapter",s +  " ");
        storageRef.child(s).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context.getApplicationContext())
                        .load(uri)
                        .into(featured_image);
            }
        });


        container.addView(sliderLayout);
        return sliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return slideList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object ;
    }
}
