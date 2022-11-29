package com.example.foody.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.foody.Model.Slide;
import com.example.foody.R;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    Context context;
    List<Slide> slideList;

    public SliderAdapter(Context context, List<Slide> slideList) {
        this.context = context;
        this.slideList = slideList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderLayout = inflater.inflate(R.layout.layout_item_slide, null);

        ImageView featured_image = sliderLayout.findViewById(R.id.my_featured_image);
        TextView caption_title = sliderLayout.findViewById(R.id.my_caption_title);
        TextView description_title = sliderLayout.findViewById(R.id.my_description_title);

        featured_image.setImageResource(slideList.get(position).getSourceImg());
        caption_title.setText(slideList.get(position).getTitle());
        description_title.setText(slideList.get(position).getDesc());
        container.addView(sliderLayout);
        return sliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return slideList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
