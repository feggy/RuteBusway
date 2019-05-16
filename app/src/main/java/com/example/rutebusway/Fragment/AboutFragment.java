package com.example.rutebusway.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.rutebusway.Activity.Detail2Activity;
import com.example.rutebusway.BuildConfig;
import com.example.rutebusway.Constants;
import com.example.rutebusway.R;
import com.example.rutebusway.RequestHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }

    private MapView mapView;
    private GoogleMap gMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyCYCQmU7s8YUEOb7v2-yh2ywAcRMxs6OUI";
    View view;
    String strId, strNama, strAlamat, strLat, strLng, strDeskripsi, strNo_hp;
    JSONArray jsonArray;
    JSONObject data;
    Marker marker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about, container, false);

        /*Bundle bundle = null;
        if (savedInstanceState != null) {
            bundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(bundle);
        mapView.getMapAsync(this);*/

//        bacaData();

        TextView tvVersion = view.findViewById(R.id.version);

        String versionCode = BuildConfig.VERSION_NAME;

        tvVersion.setText("Version "+versionCode);

        return view;
    }

    /*
    private void bacaData() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.datadamkar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                data = jsonArray.getJSONObject(i);

                                strId = data.getString("id");
                                strNama = data.getString("nama");
                                strAlamat = data.getString("alamat");
                                strLat = data.getString("lat");
                                strLng = data.getString("lng");
                                strDeskripsi = data.getString("deskripsi");
                                strNo_hp = data.getString("no_hp");

                                Log.e("Lat = ", "Lat "+strLat);
                                Log.e("Lng = ", "Lng "+strLng);

                                createMarker(strLat, strLng, strId);

                                LatLng location = new LatLng(Double.parseDouble(strLat), Double.parseDouble(strLng));

                                marker = gMap.addMarker(new MarkerOptions().position(location).title(strId));
                                Log.e("tes", marker.toString());

                                gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(strLat), Double.parseDouble(strLng))));
                            }

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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null){
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    protected Marker createMarker(String latitude, String longtitude, String title) {
        return gMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longtitude)))
                    .title(title));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setOnMarkerClickListener(AboutFragment.this);

        gMap.setMinZoomPreference(12);

        bacaData();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e("Marker", marker.toString());
        if (marker.equals(marker)) {
            Log.e("Data Lokasi : ", marker.getTitle());

            Intent i = new Intent(view.getContext(), Detail2Activity.class);
            i.putExtra("id", marker.getTitle());
            startActivity(i);
        } else {
            Toast.makeText(view.getContext(), "Gagal", Toast.LENGTH_LONG).show();
        }
        return true;
    }
    */
}
