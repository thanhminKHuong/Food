package com.example.foody.Fragment;

import static com.example.foody.Unit.setListViewHeightBasedOnItems;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.foody.Adapter.AdapterCachNau;
import com.example.foody.Adapter.AdapterRCVChiTiet;
import com.example.foody.Adapter.TienHanhAdapter;
import com.example.foody.Model.Monan;
import com.example.foody.Model.NguyenLieu;
import com.example.foody.Model.Tienhanh;
import com.example.foody.R;
import com.example.foody.View.ChitietActivity;

import java.util.ArrayList;

public class CachNauFragment extends Fragment {

    View view ;
    ListView listView ;
    AdapterCachNau adapterCachNau ;
    ArrayList<NguyenLieu> stringArrayList ;
    LinearLayout linearLayout ;
    TextView tv_GioiThieuMonAn ;

    RecyclerView rcvHinhAnhNguyenLieu ;
//    AdapterRCVChiTiet adapterRCVChiTiet ;
    ArrayList<String> imgNguyenLieu ;


//    tienhanh
    ArrayList<Tienhanh> tienhanhArrayList ;
    TienHanhAdapter tienHanhAdapter ;
   RecyclerView listViewTienHanh ;

    public CachNauFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.layout_fragment_cachnau,container,false);
         listView = view.findViewById(R.id.lv_CachNau);
         listViewTienHanh = view.findViewById(R.id.lvTienHanhNau);
         tv_GioiThieuMonAn  = view.findViewById(R.id.tv_gioithieumonan);
        rcvHinhAnhNguyenLieu = view.findViewById(R.id.rcv_hinhanhNguyenLieu);



         String gioiThieuMonAn = " - " + ChitietActivity.monan.getMota();
         tv_GioiThieuMonAn.setText(gioiThieuMonAn);

         stringArrayList = new ArrayList<>();
         adapterCachNau = new AdapterCachNau(getActivity(),R.layout.item_list_cachnau,stringArrayList);
         listView.setAdapter(adapterCachNau);

         setListViewHeightBasedOnItems(listView);

         TextView textView = new TextView(getContext());

//        imgNguyenLieu = new ArrayList<>();
//        adapterRCVChiTiet = new AdapterRCVChiTiet(getContext(),imgNguyenLieu);
//        StaggeredGridLayoutManager gridLayoutManager2 =
//                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        rcvHinhAnhNguyenLieu.setLayoutManager(gridLayoutManager2);
//        adapterRCVChiTiet.notifyDataSetChanged();
//        rcvHinhAnhNguyenLieu.setAdapter(adapterRCVChiTiet);

         for (int i = 0 ; i < ChitietActivity.monan.getNguyenlieu().size() ; i++)
         {
             stringArrayList.add(ChitietActivity.monan.getNguyenlieu().get(i));
//             imgNguyenLieu.add(ChitietActivity.monan.getHinhanhhslide().get(i));
//             adapterRCVChiTiet.notifyDataSetChanged();
             adapterCachNau.notifyDataSetChanged();
             setListViewHeightBasedOnItems(listView);
         }

         initTienHanh();
        return view;
    }

    private void initTienHanh() {
        tienhanhArrayList = new ArrayList<>();
//        tienHanhAdapter = new TienHanhAdapter();
        tienHanhAdapter = new TienHanhAdapter(getContext(),tienhanhArrayList);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        listViewTienHanh.setLayoutManager(gridLayoutManager);
        tienHanhAdapter.notifyDataSetChanged();
        listViewTienHanh.setAdapter(tienHanhAdapter);


        for(int i = 0 ; i < ChitietActivity.monan.getChebien().size() ; i++)
        {
            Tienhanh tienhanh = new Tienhanh();
            tienhanh.setTitle( ChitietActivity.monan.getChebien().get(i).getTieudechebien().toString());
            tienhanh.setContent(ChitietActivity.monan.getChebien().get(i).getBatdauchebien().toString());
            Log.d("liemafsdfs",tienhanh.toString() +  " ");
            tienhanhArrayList.add(tienhanh);
            tienHanhAdapter.notifyDataSetChanged();
        }
    }
}
