package com.example.rutebusway.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rutebusway.Models.Algoritma;
import com.example.rutebusway.R;

import java.util.ArrayList;

public class AlgoritmaAdapter extends RecyclerView.Adapter<AlgoritmaAdapter.AlgoritmaAdapterHolder> {

    private ArrayList<Algoritma> dataList;

    public AlgoritmaAdapter(ArrayList<Algoritma> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public AlgoritmaAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater  = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.row_algoritma, viewGroup, false);
        return new AlgoritmaAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlgoritmaAdapterHolder algoritmaAdapterHolder, int i) {
        algoritmaAdapterHolder.vNama.setText(dataList.get(i).getNama());
        algoritmaAdapterHolder.vLat.setText("Latitude "+dataList.get(i).getLat());
        algoritmaAdapterHolder.vLng.setText("Longitude "+dataList.get(i).getLng());
        algoritmaAdapterHolder.vJarak.setText("Jarak "+dataList.get(i).getJarak()+" km");
        algoritmaAdapterHolder.vWaktu.setText("Perkiraan waktu tempuh "+dataList.get(i).getWaktu());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class AlgoritmaAdapterHolder extends RecyclerView.ViewHolder {
        private TextView vNama, vLat, vLng, vJarak, vWaktu;
        public AlgoritmaAdapterHolder(@NonNull View itemView) {
            super(itemView);

            vNama = itemView.findViewById(R.id.vNama);
            vLat = itemView.findViewById(R.id.vLat);
            vLng = itemView.findViewById(R.id.vLng);
            vJarak = itemView.findViewById(R.id.vJarak);
            vWaktu = itemView.findViewById(R.id.tvWaktu);
        }
    }
}
