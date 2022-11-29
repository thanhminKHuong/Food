package com.example.foody.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.foody.Fragment.GioHangFragment;
import com.example.foody.Fragment.TaiKhoanFragment;
import com.example.foody.Fragment.TrangChuFragment;
import com.example.foody.Fragment.YeuThichFragment;
import com.example.foody.Model.Monan;
import com.example.foody.R;
import com.example.foody.itf.Food;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    public static String UID = "";
    private FirebaseAuth firebaseAuth;
    private SearchView searchView;
    private DatabaseReference myRef;
    Food itfFood;
    ImageView imgSearch;


//    BottomNavigation view android

    BottomNavigationView bottomNavigationView;


    //    end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = this.<BottomNavigationView>findViewById(R.id.bottomNavigation);
        imgSearch = findViewById(R.id.img_Search);
//        searchView.setQueryHint("Nhập tên món ăn,...");


        myRef = FirebaseDatabase.getInstance().getReference();


        initFragment(new TrangChuFragment(false));

        itfFood = new Food() {
            @Override
            public void getData(Monan monan) {

            }

            @Override
            public void updateData(Monan monan) {

            }

            @Override
            public void removeData(Monan monan) {

            }
        };

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TimkiemActivity.class);
                startActivity(intent);
            }
        });

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                ValueEventListener valueEventListener = new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                         for(DataSnapshot dataSnapshot:snapshot.getChildren())
//                         {
//                             Monan monan = dataSnapshot.getValue(Monan.class);
//                             monan.setIdMonAn(dataSnapshot.getKey());
//                             if(monan.getTen().toLowerCase().contains(query))
//                             {
//                                itfFood.getData(monan);
//                             }
//
//                         }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                };
//                myRef.child("sanpham").addValueEventListener(valueEventListener);
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
////                Log.d("kiemtrahinhanhnem,io",newText + "text change" );
////
////               initFragment( new TrangChuFragment(true));
//
//                return false;
//            }
//        });

        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_home:
                        initFragment(new TrangChuFragment(false));
                        break;

                    case R.id.page_faroute:
                        initFragment(new YeuThichFragment());
                        break;

                    case R.id.page_cartBuy:
                        initFragment(new GioHangFragment());
                        break;

                    case R.id.page_account:
                        initFragment(new TaiKhoanFragment());
                        break;
                }
            }
        });

        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("kiemtrauidnemioilami", UID + " ");

    }

    private void initFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.frameHomePage, fragment);
        fragmentTransaction.commit(); // save the changes

    }


}