package com.example.rutebusway.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.rutebusway.Activity.HasilActivity;
import com.example.rutebusway.Adapter.AlgoritmaAdapter;
import com.example.rutebusway.Constants;
import com.example.rutebusway.Models.Algoritma;
import com.example.rutebusway.R;
import com.example.rutebusway.RequestHandler;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * A simple {@link Fragment} subclass.
 */
public class TesAlgoritmaFragment extends Fragment implements OnMapReadyCallback {


    public TesAlgoritmaFragment() {
        // Required empty public constructor
    }

    protected FragmentActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (FragmentActivity) context;
        }
    }

    private FusedLocationProviderClient fusedLocationProviderClient;
    View view;
    int MY_PERMISSION_ACCESS_COURSE_LOCATION = 11;
    int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    String lat, lng;
    private MapView mapView;
    private GoogleMap gMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyCYCQmU7s8YUEOb7v2-yh2ywAcRMxs6OUI";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tes_algoritma, container, false);

        if (ContextCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermission();
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view.getContext());

        final TextView vLat = view.findViewById(R.id.vLatitude);
        final TextView vLng = view.findViewById(R.id.vLongitude);
        final Button btnCari = view.findViewById(R.id.btnCari);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.e("My Current Location", "Lat " + location.getLatitude() + " Long " + location.getLongitude());
                    lat = Double.toString(location.getLatitude());
                    lng = Double.toString(location.getLongitude());

                    vLat.setText("Latitude : " + lat);
                    vLng.setText("Longitude : " + lng);

                    btnCari.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(view.getContext(), HasilActivity.class));
                        }
                    });
                }
            }
        });

        Bundle bundle = null;
        if (savedInstanceState != null) {
            bundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(bundle);
        mapView.getMapAsync(this);

        return view;
    }

    private void locationPermission() {
        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSION_ACCESS_COURSE_LOCATION);
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
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
    public void onMapReady(final GoogleMap googleMap) {

        FusedLocationProviderClient flpc = LocationServices.getFusedLocationProviderClient(view.getContext());

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermission();
            return;
        }
        flpc.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Double a = location.getLatitude();
                Double b = location.getLongitude();

                gMap = googleMap;
                gMap.setMinZoomPreference(14);
                createMarker(Double.toString(a), Double.toString(b), "Lokasi saya");
                LatLng user = new LatLng(a, b);
                gMap.moveCamera(CameraUpdateFactory.newLatLng(user));
            }
        });
    }
}