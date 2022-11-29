package com.example.foody.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foody.R;
import com.example.foody.View.DangBaiActivity;

public class TaiKhoanFragment extends Fragment {

    Button btnDangBai ;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.layout_fragment_taikhoan,container,false);

        btnDangBai = view.<Button>findViewById(R.id.dangbaiviet);
        btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangBaiActivity.class);
                startActivity(intent);

//                Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
            }
        });


        return view ;
    }
}
