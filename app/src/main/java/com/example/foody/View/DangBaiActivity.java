package com.example.foody.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foody.Adapter.AdapterRCV;
import com.example.foody.Adapter.AdapterSelectImg;
import com.example.foody.Model.CheBien;
import com.example.foody.Model.NguyenLieu;
import com.example.foody.R;
import com.example.foody.itf.vitriitemclick;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class DangBaiActivity extends AppCompatActivity {

    private ArrayList<Uri> okeee = new ArrayList<>();

    private DatabaseReference myRef ;
    EditText tv_Name , tv_theloai , tv_nguyenlieu ,  tv_mota , tv_chebien ;
    Button btnXacNhan , btnChonHinh  , btn_ChonHinhCacBuoc , btnHinhSlider ;
    String tieude = null;
    String chebien = null ;
    ArrayList<Uri> getList;

    HashMap<String, Object> stringMonanHashMap ;

    int vitrine = 1 ;
    ArrayList<NguyenLieu> nguyenLieus ;


    ArrayList<CheBien> arrayList2 ;

    RecyclerView recyclerView ,rcv_HinhAnh;
    com.example.foody.itf.vitriitemclick vitriitemclick ;
    AdapterRCV adapterRCV ;



    ArrayList<String> stringArrayList ;
    ArrayList<String> upHinhAnh  ;


    ArrayList<Uri> list = new ArrayList<>();
    ArrayList<Uri> listUriHinhAnhMon = new ArrayList<>();
    ArrayList<String> listStringHinhAnhMon = new ArrayList<>();
    ArrayList<String> stringListHinhAnh  ;
    ArrayList<String> hinhAnhSlider ;
    ArrayList<String> sourceImg  ;
    AdapterSelectImg adaptor  ;
    String colum[]={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_bai);
        upHinhAnh =  new ArrayList<>() ;
        initDangBai();
        stringMonanHashMap =  new HashMap<>();

        sourceImg = new ArrayList<>();

        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tvNguyenLieu = tv_nguyenlieu.getText().toString();

                StringTokenizer st = new StringTokenizer(tvNguyenLieu, "-");

                ArrayList<String> arrayList = new ArrayList<>();
                while (st.hasMoreTokens())
                {
                    arrayList.add(st.nextToken());
                }
                nguyenLieus = new ArrayList<>();
                for(int i = 0 ; i < arrayList.size() ; i++)
                {
                    NguyenLieu  nguyenLieu =  new NguyenLieu();

                    int indexPrice = arrayList.get(i).lastIndexOf(":");
                    String tenNguyenLieu = arrayList.get(i).substring(1,indexPrice);
                    String giaNguyenLieu = arrayList.get(i).substring((indexPrice+1) , arrayList.get(i).length() );
                    nguyenLieu.setName(tenNguyenLieu);
                    nguyenLieu.setGia(giaNguyenLieu);
                    nguyenLieus.add(nguyenLieu);
                }
               vitriitemclick = new vitriitemclick() {
                   @Override
                   public void vitri(int vitri) {
                       vitrine = vitri;
                       Intent i = new Intent();
                       i.setType("image/*");
                       i.setAction(Intent.ACTION_GET_CONTENT);
                       startActivityForResult(Intent.createChooser(i, "Select Picture"), 123);
                   }
                   @Override
                   public void danhSach(List<String> stringList) {
                     for(int i = 0 ; i < nguyenLieus.size() ; i++)
                     {
                         stringArrayList = new ArrayList<>();
                         for(int j = 0 ; j < stringList.size() ; j++)
                         {
                             int a  = stringList.get(j).lastIndexOf("e");
                             String b = stringList.get(j).substring((a+1));
                             if(b.equals(String.valueOf(i)))
                             {
                                 int d  = stringList.get(j).lastIndexOf("e");
                                 String c = stringList.get(j).substring(0 , d);
                                 nguyenLieus.get(i).setHinhAnh(c);
                                 stringArrayList.add(c);
                             }
                         }
                         upHinhAnh.addAll(stringArrayList);
                     }
                   }
               };
                adapterRCV = new AdapterRCV( arrayList.size() ,  DangBaiActivity.this , vitriitemclick ) ;
                adapterRCV.notifyDataSetChanged();
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(DangBaiActivity.this, LinearLayoutManager.VERTICAL, false);
                rcv_HinhAnh.setLayoutManager(layoutManager);
                rcv_HinhAnh.setAdapter(adapterRCV);
            }
        });
        btnHinhSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Selcet Picture"),789);
            }
        });


        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cheBien = tv_chebien.getText().toString().trim();
                List<String> strings = new ArrayList<>();
                strings = Arrays.asList(cheBien.split("xI"));
                arrayList2 = new ArrayList<>();

                for(int i = 0 ; i < strings.size() ; i++)
                {
                    CheBien  cheBien1 = new CheBien();
                    int tieude = strings.get(i).indexOf("--");
                    int length = strings.get(i).length() ;
                    String tieudene =  strings.get(i).substring(9,tieude).trim();
                    cheBien1.setTieudechebien(tieudene.trim());
                    String tienhanhne = strings.get(i).substring((tieude + 13),length);
                    cheBien1.setBatdauchebien(tienhanhne.trim());
                    arrayList2.add(cheBien1);
                }

                stringMonanHashMap.put("ten",String.valueOf(tv_Name.getText()));
                stringMonanHashMap.put("mota",String.valueOf(tv_mota.getText()));
                stringMonanHashMap.put("theloai",String.valueOf(tv_theloai.getText()));
                stringMonanHashMap.put("nguyenlieu",nguyenLieus);
                stringMonanHashMap.put("chebien",arrayList2);

                ArrayList<String>  stringArrayList = new ArrayList<>();
                for(int  i = 0 ; i < upHinhAnh.size() ; i++)
                {
                    if(!stringArrayList.contains(upHinhAnh.get(i)))
                    {
                        stringArrayList.add(upHinhAnh.get(i));
                    }
                }

//                stringMonanHashMap.put("hinhanh",stringArrayList);
                stringMonanHashMap.put("hinhanhhslide",hinhAnhSlider);

            UploadImageView();
                myRef.child("sanpham").push().setValue(stringMonanHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DangBaiActivity.this, "Đăng bài viết thành công !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void UploadImageView() {

        final StorageReference ImageFolder =  FirebaseStorage.getInstance().getReference().child("hinhanhfood/");

        for (int i = 0 ; i < okeee.size() ; i++)
        {
            Uri Image  = okeee.get(i);
            final StorageReference imagename = ImageFolder.child(Image.getLastPathSegment());
            String s = String.valueOf(Image.getLastPathSegment());
            Log.d("keiamaefiaweiefo",okeee.get(i) + " ");
            sourceImg.add(s);

            imagename.putFile(okeee.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            });
        }
    }

    private void openGalley() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selcet Picture"),123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==123 && resultCode==RESULT_OK){
                        Uri selectedImageUri = data.getData();
                            okeee.add(selectedImageUri);
                            String s = String.valueOf(selectedImageUri.getLastPathSegment() + " ");
                            sourceImg.add(s + "e" + vitrine);
                            Log.d("adiuytrewq",s);
                            vitriitemclick.danhSach(sourceImg);
             }

        if(requestCode == 789 && resultCode == RESULT_OK)
        {
            if(data.getClipData()!=null){
                int x=data.getClipData().getItemCount();
                for(int i=0;i<x;i++){
                    getList.add(data.getClipData().getItemAt(i).getUri());

                    hinhAnhSlider.add(String.valueOf(getList.get(i).getLastPathSegment()));

                    okeee.add(data.getClipData().getItemAt(i).getUri());

                }
            }else if(data.getData()!=null){
                String imgurl=data.getData().getPath();
                list.add(Uri.parse(imgurl));
            }
        }
    }

    private void initDangBai() {

        hinhAnhSlider = new ArrayList<>();
        getList = new ArrayList<>();
        btnHinhSlider =  findViewById(R.id.btn_HinhSlider);
        myRef = FirebaseDatabase.getInstance().getReference();
        rcv_HinhAnh = findViewById(R.id.rcv_HinhAnhNe);
        tv_Name = findViewById(R.id.tv_Name);
        tv_theloai = findViewById(R.id.tv_theloai);
        tv_nguyenlieu = findViewById(R.id.tv_nguyenlieu);

        tv_mota = findViewById(R.id.tv_mota);
        tv_chebien  = findViewById(R.id.tv_chebien);
        recyclerView = findViewById(R.id.rcv_dangbainemioi);
        btnXacNhan = findViewById(R.id.btn_XacNhan);

        btnChonHinh  = findViewById(R.id.btn_SelectImage);

    }
}