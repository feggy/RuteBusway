package com.example.rutebusway.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rutebusway.Models.Lokasi;
import com.example.rutebusway.R;

import java.util.ArrayList;

public class LokasiAdapter extends RecyclerView.Adapter<LokasiAdapter.LokasiAdapterHolder> {

    private ArrayList<Lokasi> dataList;

    public LokasiAdapter(ArrayList<Lokasi> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public LokasiAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.row_list, viewGroup, false);
        return new LokasiAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LokasiAdapterHolder lokasiAdapterHolder, int i) {
        lokasiAdapterHolder.lokasi.setText(dataList.get(i).getNama());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class LokasiAdapterHolder extends RecyclerView.ViewHolder {
        private TextView lokasi;
        public LokasiAdapterHolder(@NonNull View itemView) {
            super(itemView);
            lokasi = itemView.findViewById(R.id.vLokasi);
        }
    }
}
