package com.example.foody.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.foody.Adapter.SliderAdapterDetail;
import com.example.foody.Fragment.CachNauFragment;
import com.example.foody.Fragment.TuongTuFragment;
import com.example.foody.Model.Monan;
import com.example.foody.Model.SlideDetails;
import com.example.foody.R;
import com.example.foody.itf.stringList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class ChitietActivity extends AppCompatActivity {


    public static Monan monan ;
    private String title ;
    private Toolbar toolbar;
    private AlertDialog.Builder alertDialog ;
    private SliderAdapterDetail adapterDetail ;
    private List<SlideDetails> slideDetailsList ;
    private ViewPager viewPager ;
    RadioGroup radioGroup ;
    RadioButton rbCachNau , rbTuongTu ;

    private DatabaseReference myRef ;
    private FirebaseAuth  mAuth ;
    private FirebaseUser mUser ;
    ImageView  yeuthichImage , imageView2   ;
    public static String uid ;
    com.example.foody.itf.stringList stringList ;
    int  i = 0 ;
    public static  ArrayList<String> stringArrayList ;

    // fragment
    FrameLayout frameLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);


        monan = getIntent().getExtras().getParcelable("monan");
        imageView2 = findViewById(R.id.imageView2);
        Log.d("dfasfoashfodaofawefawf",monan.toString() + " ");

        myRef = FirebaseDatabase.getInstance().getReference();


        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();
        uid = mUser.getUid();
      Log.d("kiemtrauser",monan.getIdMonAn()  + " ");



        viewPager = this.<ViewPager>findViewById(R.id.slider_detailsPage);
        toolbar = this.<Toolbar>findViewById(R.id.toolbarDetails);
        toolbar.setTitle(monan.getTen());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        radioGroup = this.<RadioGroup>findViewById(R.id.radioGroupDetails);
        rbCachNau = this.<RadioButton>findViewById(R.id.rb_CachNau);
        rbTuongTu = this.<RadioButton>findViewById(R.id.rb_TuongTu);
         alertDialog = new AlertDialog.Builder(this);
        yeuthichImage = this.<ImageView>findViewById(R.id.yeuthichchitiet);


        rbCachNau.setChecked(true);
        initFragment(new CachNauFragment());

         stringArrayList = new ArrayList<>();

        setSupportActionBar(toolbar);
        slideDetailsList = new ArrayList<>();
        adapterDetail =  new SliderAdapterDetail(ChitietActivity.this,slideDetailsList);

        String title = monan.getTen();

        for(int i =  0 ; i < monan.getHinhanhhslide().size() ; i++)
        {
           SlideDetails slideDetails = new SlideDetails();
           slideDetails.setImg(monan.getHinhanhhslide().get(i));
           slideDetails.setTitle(title);
           slideDetailsList.add(slideDetails);
           adapterDetail.notifyDataSetChanged();
        }
          Log.d("kiemtramanghinhanh",slideDetailsList.size() + " ");
        viewPager.setAdapter(adapterDetail);

        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new ChitietActivity.The_slide_timer(),2000,3000);
       imageView2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               if(i == R.id.rb_CachNau)
               {
                   initFragment( new CachNauFragment());
               }
               else  if( i == R.id.rb_TuongTu)
               {
                   initFragment( new TuongTuFragment());
               }
            }
        });


// Xử lý logic khi thêm , xóa bài đã lưu
        stringList = new stringList() {
            @Override
            public void getListString(ArrayList<String> stringArrayList) {

                if(stringArrayList.contains(monan.getIdMonAn()))
                {
                    yeuthichImage.setImageResource(R.drawable.ic_baseline_favorite_24);

                    yeuthichImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            alertDialog.setMessage("Bỏ lưu bài viết ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            for(int  j = 0 ; j < stringArrayList.size() ; j++)
                                            {
                                                if(stringArrayList.get(j).equals(monan.getIdMonAn()))
                                                {
                                                    stringArrayList.remove(j);
                                                }
                                            }
                                            myRef.child("yeuthichs").child(uid).setValue(stringArrayList);
                                            stringArrayList.clear();
                                        }
                                    })
                                    .setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                          alertDialog.c
                                        }
                                    });
                            alertDialog.show();
                        }
                    });
                }else if(!stringArrayList.contains(monan.getIdMonAn()))
                {
                    yeuthichImage.setImageResource(R.drawable.ic_baseline_favorite_border_24);

                    yeuthichImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            alertDialog.setMessage("Lưu bài viết ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            stringArrayList.add(monan.getIdMonAn());

                                            myRef.child("yeuthichs").child(uid).setValue(stringArrayList);
                                            stringArrayList.clear();
                                        }
                                    })
                                    .setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    });
                            alertDialog.show();

                        }
                    });
                }
            }
        };



        Log.d("kiemtrauid",uid +  " ");

// Kiểm tra xem người dùng có danh sách lưu bài viết khongo9
        if(!uid.equals(""))
        {
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(uid))
                    {
                        for(DataSnapshot dataSnapshot: snapshot.child(uid).getChildren())
                        {
                            stringArrayList.add(dataSnapshot.getValue(String.class));
                        }
                        stringList.getListString(stringArrayList);
                    }else if(!snapshot.hasChild(uid))
                    {
                        stringArrayList.clear();
                        stringList.getListString(stringArrayList);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            myRef.child("yeuthichs").addValueEventListener(valueEventListener);
        }


      }



    private void initFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_Home, fragment);
        fragmentTransaction.commit(); // save the changes
    }





    public class The_slide_timer extends TimerTask {
        @Override
        public void run() {

            ChitietActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem()< slideDetailsList.size()-1) {
                       viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    }
                    else
                        viewPager.setCurrentItem(0);
                }
            });
        }
    }


}