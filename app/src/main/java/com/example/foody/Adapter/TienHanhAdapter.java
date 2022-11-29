package com.example.foody.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.foody.Model.Tienhanh;
import com.example.foody.R;

import java.util.List;

public class TienHanhAdapter extends RecyclerView.Adapter<TienHanhAdapter.ViewHolder> {

    private Context context;
    private List<Tienhanh> tienhanhList;
    private List<String> listHinhAnhTienHanh;
    private String tieude = null;
    private String tienhanh = null;
    private AdapterRCVChiTiet adapterRCVChiTiet;

    public TienHanhAdapter(Context context, List<Tienhanh> tienhanhList) {
        this.context = context;
        this.tienhanhList = tienhanhList;

    }

    @NonNull
    @Override
    public TienHanhAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_tienhanh, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TienHanhAdapter.ViewHolder holder, int position) {
        tieude = tienhanhList.get(position).getTitle().toString();
        tienhanh = "-" + tienhanhList.get(position).getContent().toString();
        holder.tieude.setText(tieude);
        holder.tienhanh.setText(tienhanh);
        holder.btnStep.setText("Bước : " + (position + 1));

    }

    @Override
    public int getItemCount() {
        return tienhanhList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView tieude, tienhanh;
        Button btnStep;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tienhanh = itemView.<TextView>findViewById(R.id.tv_tienhanh);
            tieude = itemView.findViewById(R.id.tv_tieudeTienHanh);
            btnStep = itemView.findViewById(R.id.btnBuoc);

        }
    }
}
