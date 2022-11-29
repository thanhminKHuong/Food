package com.example.foody.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foody.Adapter.AdapterFoody;
import com.example.foody.Model.Monan;
import com.example.foody.R;
import com.example.foody.itf.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;

public class TimkiemActivity extends AppCompatActivity {

    SearchView searchView;
    ImageView imgBack;
    private DatabaseReference myRef;
    Food food;
    ArrayList<Monan> monanArrayList;
    AdapterFoody adapterFoody;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        recyclerView = this.<RecyclerView>findViewById(R.id.rcv_Search);
        myRef = FirebaseDatabase.getInstance().getReference();
        monanArrayList = new ArrayList<>();
        imgBack = findViewById(R.id.img_BackSearch);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        searchView = this.<SearchView>findViewById(R.id.searchView);
        searchView.setQueryHint("Nhập tên món ăn ,...");

        adapterFoody = new AdapterFoody(TimkiemActivity.this, monanArrayList);
        adapterFoody.notifyDataSetChanged();

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterFoody);


        food = new Food() {
            @Override
            public void getData(Monan monan) {
                monanArrayList.add(monan);
                adapterFoody.notifyDataSetChanged();
            }

            @Override
            public void updateData(Monan monan) {

            }

            @Override
            public void removeData(Monan monan) {

            }
        };

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                monanArrayList.clear();
                adapterFoody.notifyDataSetChanged();

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Monan monan = dataSnapshot.getValue(Monan.class);
                            monan.setIdMonAn(dataSnapshot.getKey());
                            if (query.length() == 0) {
                                monanArrayList.clear();
                                adapterFoody.notifyDataSetChanged();
                            } else {
                                if (monan.getTen().toLowerCase().trim().contains(query)) {
                                    food.getData(monan);
                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };

                myRef.child("sanpham").addValueEventListener(valueEventListener);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                monanArrayList.clear();
                adapterFoody.notifyDataSetChanged();

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Monan monan = dataSnapshot.getValue(Monan.class);
                            monan.setIdMonAn(dataSnapshot.getKey());
                         if(newText.length() == 0 )
                         {
                             monanArrayList.clear();
                             adapterFoody.notifyDataSetChanged();
                         }else
                         {
                             if (monan.getTen().toLowerCase().trim().contains(newText)) {
                                 food.getData(monan);
                                 Log.d("eiaoifie222a", monan.getTen() + " ");
                             }
                         }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };

                myRef.child("sanpham").addValueEventListener(valueEventListener);

                return false;
            }
        });

    }
}