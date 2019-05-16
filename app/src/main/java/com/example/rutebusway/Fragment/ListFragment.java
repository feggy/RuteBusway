package com.example.rutebusway.Fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.rutebusway.Activity.DetailActivity;
import com.example.rutebusway.Constants;
import com.example.rutebusway.Models.Lokasi;
import com.example.rutebusway.Adapter.LokasiAdapter;
import com.example.rutebusway.R;
import com.example.rutebusway.RecyclerItemClickListener;
import com.example.rutebusway.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    public ListFragment() {
        // Required empty public constructor
    }

    View view;
    JSONArray jsonArray;
    JSONObject data;
    RecyclerView recyclerView;
    String id, nama, alamat, lat, lng, deskripsi, no_hp, gambar;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        tampilData();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view2, final int position) {
                try {
                    data = jsonArray.getJSONObject(position);
                    id = data.getString("id");
                    nama = data.getString("nama");
                    alamat = data.getString("alamat");
                    lat = data.getString("lat");
                    lng = data.getString("lng");
                    deskripsi = data.getString("deskripsi");
                    no_hp = data.getString("no_hp");
                    gambar = data.getString("gambar");

                    Intent i = new Intent(view.getContext(), DetailActivity.class);
                    i.putExtra("id", id);
                    i.putExtra("nama", nama);
                    i.putExtra("alamat", alamat);
                    i.putExtra("lat", lat);
                    i.putExtra("lng", lng);
                    i.putExtra("deskripsi", deskripsi);
                    i.putExtra("no_hp", no_hp);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }));

        return view;
    }

    private void tampilData() {
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.datadamkar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList arrayList = new ArrayList();
                            JSONObject jsonObject = new JSONObject(response);
                            jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                data = jsonArray.getJSONObject(i);

                                id = data.getString("id");
                                nama = data.getString("nama");
                                alamat = data.getString("alamat");
                                lat = data.getString("lat");
                                lng = data.getString("lng");
                                deskripsi = data.getString("deskripsi");
                                no_hp = data.getString("no_hp");
                                gambar = data.getString("gambar");

                                Lokasi lokasi = new Lokasi(id, nama, alamat, lat, lng, deskripsi, no_hp, gambar);
                                arrayList.add(lokasi);
                            }
                            LokasiAdapter lokasiAdapter = new LokasiAdapter(arrayList);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(lokasiAdapter);
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(view.getContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(), "Gagal terhubung ke server", Toast.LENGTH_LONG).show();
                    }
                });
        RequestHandler.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

}
