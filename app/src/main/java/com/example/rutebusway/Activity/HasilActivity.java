package com.example.rutebusway.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.rutebusway.Adapter.AlgoritmaAdapter;
import com.example.rutebusway.Constants;
import com.example.rutebusway.Models.Algoritma;
import com.example.rutebusway.R;
import com.example.rutebusway.RecyclerItemClickListener;
import com.example.rutebusway.RequestHandler;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HasilActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    int MY_PERMISSION_ACCESS_COURSE_LOCATION = 11;
    int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    String id, nama, lat, lng;
    JSONArray jsonArray;
    JSONObject data;
    RecyclerView recyclerView;
    String strNama, strLat, strLng, strJarak, strWaktu;
    ArrayList<Algoritma> arrayList;
    String noBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(HasilActivity.this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COURSE_LOCATION );
            ActivityCompat.requestPermissions(HasilActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(HasilActivity.this);

        recyclerView = findViewById(R.id.recyclerView);
        final ProgressBar progressBar = findViewById(R.id.vProgressBar);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    Log.e("My Current Location", "Lat " + location.getLatitude() + " Long " + location.getLongitude());
                    lat = Double.toString(location.getLatitude());
                    lng = Double.toString(location.getLongitude());

                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            Constants.fpw,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        arrayList = new ArrayList();
                                        jsonArray = jsonObject.getJSONArray("data");

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            data = jsonArray.getJSONObject(i);
                                            String id = data.getString("id");
                                            strJarak = data.getString("km");
                                            strNama = data.getString("daerah");
                                            strLat = data.getString("lat");
                                            strLng = data.getString("lng");
                                            strWaktu = data.getString("waktutempuh");

                                            /*double waktu = (Double.parseDouble(strJarak) / 50)*60;
                                            int w = (int) waktu;*/

                                            Algoritma algoritma = new Algoritma(id, strNama, strLat, strLng, strJarak, strWaktu);
                                            arrayList.add(algoritma);

//                                            Log.e("Data", "nama "+strNama+" waktu "+waktu);
                                        }

                                        Collections.sort(arrayList, new Comparator<Algoritma>() {
                                            @Override
                                            public int compare(Algoritma o1, Algoritma o2) {
                                                return o1.getJarak().compareTo(o2.getJarak());
                                            }
                                        });

                                        AlgoritmaAdapter algoritmaAdapter = new AlgoritmaAdapter(arrayList);
                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                        recyclerView.setLayoutManager(layoutManager);
                                        recyclerView.setAdapter(algoritmaAdapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();

                            params.put("latitude", lat);
                            params.put("longitude", lng);

                            return params;
                        }
                    };
                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                }

                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        Dialog dialog = new Dialog(HasilActivity.this);
                        dialog.setContentView(R.layout.dialog_hasil);

                        Algoritma get = arrayList.get(position);
                        final String id = get.getId();
                        final String gNama = get.getNama();
                        final String gLat = get.getLat();
                        final String gLng = get.getLng();

                        Log.e("get", id+" "+gNama+" "+gLat+" "+gLng);

                        final TextView vNama = dialog.findViewById(R.id.vNama);
                        final TextView vBus = dialog.findViewById(R.id.vNoBus);
                        final TextView vLat = dialog.findViewById(R.id.vLat);
                        final TextView vLng = dialog.findViewById(R.id.vLng);
                        final LinearLayout vProgressBar = dialog.findViewById(R.id.vProgressBar);
                        final LinearLayout lnyData = dialog.findViewById(R.id.lnyData);

                        Button button = dialog.findViewById(R.id.btnOke);

                        vProgressBar.setVisibility(View.VISIBLE);
                        lnyData.setVisibility(View.INVISIBLE);

                        StringRequest stringRequest = new StringRequest(
                                Request.Method.GET,
                                Constants.busway+"/"+id,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject ob = new JSONObject(response);

                                            if (!ob.getString("status").equals("fail")){
                                                JSONObject da = ob.getJSONObject("data");
                                                noBus = da.getString("nama_bus");

                                                vNama.setText(gNama);
                                                vBus.setText("Bus nomor "+noBus);
                                                vLat.setText(gLat);
                                                vLng.setText(gLng);
                                            } else {
                                                vNama.setText(gNama);
                                                vBus.setText("Bus tidak di temukan");
                                                vLat.setText(gLat);
                                                vLng.setText(gLng);
                                            }

                                            vProgressBar.setVisibility(View.GONE);
                                            lnyData.setVisibility(View.VISIBLE);

                                            Log.e("noBus", noBus);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.e("ERR", e.toString());
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_LONG).show();
                                    }
                                });

                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(HasilActivity.this, DirectionActivity.class);
                                i.putExtra("nama", gNama);
                                i.putExtra("lat", gLat);
                                i.putExtra("lng", gLng);
                                startActivity(i);
                            }
                        });

                        dialog.show();
                    }
                }));
            }
        });

    }
}
