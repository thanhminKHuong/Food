package com.example.foody.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.foody.Adapter.AdapterFoody;
import com.example.foody.Model.Monan;
import com.example.foody.R;
import com.example.foody.View.ChitietActivity;
import com.example.foody.itf.Food;
import com.example.foody.itf.favourite;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class YeuThichFragment extends Fragment {

     View view ;
     RecyclerView rcvYeuthich ;

     private DatabaseReference myRef ;
     private Food food ;

     private AdapterFoody adapterFoody ;
     private ArrayList<Monan> monanArrayList ;
     private com.example.foody.itf.favourite favourite ;
     List<String> stringList  ;
     List<Monan> monanList ;


     ArrayList<String> yeuThichList ;
     ArrayList<Monan> monAnYeuThich ;

     public YeuThichFragment() {

     }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_luubai,container,false);

        myRef = FirebaseDatabase.getInstance().getReference();

        monanArrayList = new ArrayList<>();
        stringList  = new ArrayList<>();
        monanList = new ArrayList<>();

        rcvYeuthich = view.findViewById(R.id.rcv_luubai);

        adapterFoody = new AdapterFoody(getActivity(),monanList);
        adapterFoody.notifyDataSetChanged();
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcvYeuthich.setLayoutManager(gridLayoutManager);
        rcvYeuthich.setAdapter(adapterFoody);


        Monan monan =  new Monan();

        yeuThichList = new ArrayList<>();
        monAnYeuThich = new ArrayList<>();

        favourite = new favourite() {
            @Override
            public void getDataFavourite(ArrayList<Monan> monan, ArrayList<String> stringArrayList) {

                yeuThichList.addAll(stringArrayList);
                monAnYeuThich.addAll(monan);
                Log.d("dsaofdiasffsffafds",monAnYeuThich.size()  + " ");

                for(int i = 0 ; i < monAnYeuThich.size() ; i++)
                {
                  if(yeuThichList.size() > 0 )
                  {
                      for(int j = 0 ; j <  yeuThichList.size() ; j++)
                      {
                          if(monAnYeuThich.get(i).getIdMonAn().toString().equals( yeuThichList.get(j).toString()))
                          {
                              monanList.add(monAnYeuThich.get(i));
                              Log.d("koisadhfaoshfoshoiaf",monanList.size() + " ");
                              adapterFoody.notifyDataSetChanged();
                          }
                      }
                  }
                }
            }
        };
        monan.getAllDataFavour(favourite);


       return view;
    }



}
