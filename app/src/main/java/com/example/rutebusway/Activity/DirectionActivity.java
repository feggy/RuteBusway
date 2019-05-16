package com.example.rutebusway.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.rutebusway.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DirectionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap gMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyCYCQmU7s8YUEOb7v2-yh2ywAcRMxs6OUI";
    Marker marker;
    String lat, lng, nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        Bundle bundle = null;
        if (savedInstanceState != null) {
            bundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(bundle);
        mapView.getMapAsync( this);
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

        Bundle getDatahasil = getIntent().getExtras();
        if (getDatahasil != null){
            nama = getDatahasil.getString("nama");
            lat = getDatahasil.getString("lat");
            lng = getDatahasil.getString("lng");
        }

//        Toast.makeText(getApplicationContext(), "Nama "+nama+" lat "+lat+" lng "+lng, Toast.LENGTH_LONG).show();
        Log.e("getDataHasil", "Nama "+nama+" lat "+lat+" lng "+lng);

        LatLng tes = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        gMap.addMarker(new MarkerOptions().position(tes).title(nama));
        gMap.setMinZoomPreference(12);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(tes));

    }
}
