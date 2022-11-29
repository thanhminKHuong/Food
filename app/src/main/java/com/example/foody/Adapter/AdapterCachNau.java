package com.example.foody.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foody.Model.NguyenLieu;
import com.example.foody.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterCachNau extends ArrayAdapter<NguyenLieu> {

    private Activity context1 ;
    private ArrayList<NguyenLieu> arrayList  ;


    public AdapterCachNau(@NonNull Activity context, int resource, @NonNull ArrayList<NguyenLieu> arrayList) {
        super(context, resource, arrayList);
        this.context1 = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context1.getLayoutInflater();

        View rowView=inflater.inflate(R.layout.item_list_cachnau, null,true);

        String s = "+) " + arrayList.get(position).getName();

        TextView textView = rowView.findViewById(R.id.tv_Name);

        textView.setText(s);

        return rowView;
    }
}
