package com.example.foody.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.foody.Adapter.AdapterFoody;
import com.example.foody.Adapter.PopularAdapter;
import com.example.foody.Adapter.SliderAdapter;
import com.example.foody.Model.GioHang;
import com.example.foody.Model.Monan;
import com.example.foody.Model.Popular;
import com.example.foody.Model.Slide;
import com.example.foody.R;
import com.example.foody.View.ChitietActivity;
import com.example.foody.itf.ClickItemFood;
import com.example.foody.itf.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class TrangChuFragment  extends Fragment {

//    để lấy được danh sách yêu thích bắt buộc phải đăng nhập và vô trangchufragment
    public static ArrayList<String> stringArrayList ;
    public static String uid ;
    private FirebaseAuth  mAuth ;
    private FirebaseUser mUser ;

    private DatabaseReference myRef ;

    private SliderAdapter sliderAdapter ;
    private List<Slide> listItems;
    private ViewPager pageHome;

    private RecyclerView rcvPopular ;
    private PopularAdapter popularAdapter  ;
    private List<Popular> popularList ;


    //    giao dien banh
    private AdapterFoody adapterFoody ;
    public static List<Monan> banhList ;
    private RecyclerView rcvCake ;
    private Food food;
    private ClickItemFood clickItemFood ;
    private boolean confrimSearch = false ;

    private View view ;


    private Monan monan  ;


//    Danh sách giỏ hàng nì ;
    private List<GioHang> gioHangList ;



    public TrangChuFragment(boolean confrimSearch) {
        this.confrimSearch = confrimSearch;
    }


    public TrangChuFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if(confrimSearch == false)
       {
           view = inflater.inflate(R.layout.layout_fragment_trangchu,container,false);

           Toolbar toolbar = view.findViewById(R.id.tool_BarHome);

           ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

           pageHome = view.findViewById(R.id.slider_homepage);

           rcvPopular  = view.findViewById(R.id.rcv_thinhhanh);

           myRef = FirebaseDatabase.getInstance().getReference();
           stringArrayList = new ArrayList<>();

//        myRef.child(ChitietActivity.uid);

           mAuth = FirebaseAuth.getInstance();

           mUser = mAuth.getCurrentUser();
           uid = mUser.getUid();

           if(uid != null)
           {

               ChildEventListener childEventListener = new ChildEventListener() {
                   @Override
                   public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                       String  s = snapshot.getValue(String.class);
                       stringArrayList.add(s);

                   }

                   @Override
                   public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                   }
                   @Override
                   public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                       String s = snapshot.getValue(String.class);
                       for( int i = 0 ; i < stringArrayList.size() ; i++)
                       {
                           if(stringArrayList.contains(s))
                           {
                               stringArrayList.remove(i);
                           }
                       }
                   }
                   @Override
                   public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                   }
                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               };
               myRef.child("yeuthichs").child(uid).addChildEventListener(childEventListener);

           }


           popularList = new ArrayList<>();
//           popularList.add(new Popular("Trán trộn",R.drawable.banhtrantron));
//           popularList.add(new Popular("Trà sữa",R.drawable.trasua));
//           popularList.add(new Popular("Ốc hút",R.drawable.ochut));
//           popularList.add(new Popular("Bánh canh",R.drawable.banhcanhrun));
//           popularList.add(new Popular("Trà đào",R.drawable.tradao));
//           popularList.add(new Popular("Bún đậu",R.drawable.bundau));
//           popularList.add(new Popular("Kem bơ",R.drawable.chebo));
           popularAdapter = new PopularAdapter(getActivity(),popularList);
           rcvPopular.setAdapter(popularAdapter);

           LinearLayoutManager layoutManager
                   = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

           initSlide();
           rcvPopular.setLayoutManager(layoutManager);

           banhList = new ArrayList<>();
           initRcvCake();


           monan = new Monan();


           food  = new Food() {
               @Override
               public void getData(Monan monan) {
//                banhList.clear();
                   banhList.add(monan);
                   Log.d("kiemtralisst", banhList.size() + " " );
                   adapterFoody.notifyDataSetChanged();
               }

               @Override
               public void updateData(Monan monan) {
                   for(int i = 0 ; i < banhList.size() ; i++)
                   {
                       if(monan.getIdMonAn().equals(banhList.get(i).getIdMonAn()))
                       {
                           banhList.set(i,monan);
                           adapterFoody.notifyDataSetChanged();
                       }
                   }
               }

               @Override
               public void removeData(Monan monan) {

                   for(int i = 0 ; i < banhList.size() ; i++)
                   {
                       if(monan.getIdMonAn().equals(banhList.get(i).getIdMonAn()))
                       {
                           banhList.remove(i);
                       }
                   }

               }
           };

           monan.getData(food);

       }
       else if(confrimSearch == true)
       {
           view = inflater.inflate(R.layout.content_search,container,false);
       }

        return view ;

    }


    private void initRcvCake() {

        rcvCake = view.findViewById(R.id.rcvBanh);
        clickItemFood  = new ClickItemFood() {
            @Override
            public void click(Monan food) {
                Intent intent = new Intent(getActivity(), ChitietActivity.class);
                intent.putExtra("monan",food);
                startActivity(intent);
            }
        };
        adapterFoody = new AdapterFoody(getActivity(),banhList,clickItemFood);
        adapterFoody.notifyDataSetChanged();
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcvCake.setLayoutManager(gridLayoutManager);
        rcvCake.setAdapter(adapterFoody);

    }

    private void initSlide() {
        listItems = new ArrayList<>() ;
        sliderAdapter = new SliderAdapter(getActivity(),listItems);
        listItems.add(new Slide(R.drawable.item2,"Bánh đúc","Thơm ngon nứt mũi"));
        listItems.add(new Slide(R.drawable.item3fr,"Trái cây tươi","Không chất kích thích"));
        listItems.add(new Slide(R.drawable.item4cf,"Cà phê đắng","Tựa hương vị tình yêu"));
        listItems.add(new Slide(R.drawable.itemslide1,"Bánh mỳ","Đậm vị quê hương"));

        pageHome.setAdapter(sliderAdapter);

        java.util.Timer timer = new java.util.Timer();
//        timer.scheduleAtFixedRate(new The_slide_timer(),2000,3000);
    }

    public void getDataCart()
    {
        ChildEventListener layDanhSachGioHang = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if(!snapshot.exists())
                {
                    Log.d("khoong có dữ liệu","có dữ liệu");
                }else if(snapshot.exists())
                {
                    Log.d("khoong có dữ liệu", "kocodulieu");
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        myRef.child("giohang").child(uid).addChildEventListener(layDanhSachGioHang);
    }


    public class The_slide_timer extends TimerTask {
        @Override
        public void run() {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (pageHome.getCurrentItem()< listItems.size()-1) {
                        pageHome.setCurrentItem(pageHome.getCurrentItem()+1);
                    }
                    else
                        pageHome.setCurrentItem(0);
                }
            });
        }
    }
}
