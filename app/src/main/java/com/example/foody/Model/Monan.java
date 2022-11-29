package com.example.foody.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foody.itf.Food;
import com.example.foody.itf.favourite;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

    public class Monan  implements  Parcelable {


    private String  mota , ten , theloai ,idMonAn ;

    private List<String> hinhanhhslide ;

        private ArrayList<CheBien> chebien;
    private List<NguyenLieu> nguyenlieu ;

    private List<Monan> monanList;
        ArrayList<String> stringArrayList ;
        ArrayList<Monan> monanArrayList2 ;


        private   ArrayList<Monan> monanArrayList;


        private DatabaseReference myRef ;

    private Food itfFood ;


    public Monan() {
        myRef = FirebaseDatabase.getInstance().getReference();
        monanArrayList2 = new ArrayList<>();
        stringArrayList = new ArrayList<>();

    }


        protected Monan(Parcel in) {
            mota = in.readString();
            ten = in.readString();
            theloai = in.readString();
            idMonAn = in.readString();
            hinhanhhslide = in.createStringArrayList();
            chebien = in.createTypedArrayList(CheBien.CREATOR);
            nguyenlieu = in.createTypedArrayList(NguyenLieu.CREATOR);
            monanList = in.createTypedArrayList(Monan.CREATOR);
            stringArrayList = in.createStringArrayList();
            monanArrayList2 = in.createTypedArrayList(Monan.CREATOR);
            monanArrayList = in.createTypedArrayList(Monan.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mota);
            dest.writeString(ten);
            dest.writeString(theloai);
            dest.writeString(idMonAn);
            dest.writeStringList(hinhanhhslide);
            dest.writeTypedList(chebien);
            dest.writeTypedList(nguyenlieu);
            dest.writeTypedList(monanList);
            dest.writeStringList(stringArrayList);
            dest.writeTypedList(monanArrayList2);
            dest.writeTypedList(monanArrayList);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Monan> CREATOR = new Creator<Monan>() {
            @Override
            public Monan createFromParcel(Parcel in) {
                return new Monan(in);
            }

            @Override
            public Monan[] newArray(int size) {
                return new Monan[size];
            }
        };

        public String getMota() {
            return mota;
        }

        public void setMota(String mota) {
            this.mota = mota;
        }

        public String getTen() {
            return ten;
        }

        public void setTen(String ten) {
            this.ten = ten;
        }

        public String getTheloai() {
            return theloai;
        }

        public void setTheloai(String theloai) {
            this.theloai = theloai;
        }

        public List<String> getHinhanhhslide() {
            return hinhanhhslide;
        }

        public void setHinhanhhslide(List<String> hinhanhhslide) {
            this.hinhanhhslide = hinhanhhslide;
        }

        public ArrayList<CheBien> getChebien() {
            return chebien;
        }

        public void setChebien(ArrayList<CheBien> chebien) {
            this.chebien = chebien;
        }

        public List<NguyenLieu> getNguyenlieu() {
            return nguyenlieu;
        }

        public void setNguyenlieu(List<NguyenLieu> nguyenlieu) {
            this.nguyenlieu = nguyenlieu;
        }

        public List<Monan> getMonanList() {
            return monanList;
        }

        public void setMonanList(List<Monan> monanList) {
            this.monanList = monanList;
        }

        public String getIdMonAn() {
            return idMonAn;
        }

        public void setIdMonAn(String idMonAn) {
            this.idMonAn = idMonAn;
        }

        public void getData(Food itfFood)
    {
        monanArrayList  = new ArrayList<>();
        ArrayList<CheBien> cheBienArrayList = new ArrayList<>();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Monan monan = snapshot.getValue(Monan.class);

                monan.setIdMonAn(snapshot.getKey());

                Log.d("ádafhodhsfaoshoeifhaoiwesf",monan.toString()  +
                        " ");

               for(int i =  0 ; i < monan.getHinhanhhslide().size() ; i++)
               {
                   Log.d("ádafhodhsfaoshoeifhaoiwesf",monan.getHinhanhhslide().get(i) +
                           " ");
               }

                Log.d("keimtraIDmonan",monan.getIdMonAn() + " ");

                itfFood.getData(monan);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Monan monan = snapshot.getValue(Monan.class);
                monan.setIdMonAn(snapshot.getKey());
                itfFood.updateData(monan);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Monan monan = snapshot.getValue(Monan.class);
                monan.setIdMonAn(snapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        myRef.child("sanpham").addChildEventListener(childEventListener);

    }


   public void  getAllDataFavour(favourite favourite)
    {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DataSnapshot dataSnapshot = snapshot.child("sanpham");
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Monan monan = dataSnapshot1.getValue(Monan.class);
                    monan.setIdMonAn(dataSnapshot1.getKey());
                    Log.d("monafoshdosif",monan.toString() + " ");
                    monanArrayList2.add(monan);
                }
                DataSnapshot yeuthich = snapshot.child("yeuthichs");
                for(DataSnapshot dataSnapshot1 : yeuthich.getChildren())
                {
                    stringArrayList = (ArrayList<String>) dataSnapshot1.getValue();
                    Log.d("có sự thay đổi ở đây", stringArrayList.size() + " ");
                }
                Log.d("sòaoifhowhofhioaoif",monanArrayList2.size() +  " ");

                favourite.getDataFavourite(monanArrayList2,stringArrayList);
                stringArrayList.clear();
                monanArrayList2.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.addValueEventListener(valueEventListener);
    }
}
