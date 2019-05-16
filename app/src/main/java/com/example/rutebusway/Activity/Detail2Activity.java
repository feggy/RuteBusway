package com.example.rutebusway.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.rutebusway.Constants;
import com.example.rutebusway.R;
import com.example.rutebusway.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Detail2Activity extends AppCompatActivity {

    String strId, strNama, strAlamat, strLat, strLng, strDesk, strNohp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            strId = bundle.getString("id");
        }

        final TextView id = findViewById(R.id.vId);
        final TextView nama = findViewById(R.id.vNama);
        final TextView alamat = findViewById(R.id.vAlamat);
        final TextView lat = findViewById(R.id.vLat);
        final TextView lng = findViewById(R.id.vLng);
        final TextView desk = findViewById(R.id.vDeskripsi);
        final TextView no_hp = findViewById(R.id.vNohp);

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.datadamkardetail+"/"+strId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            JSONObject data = jsonArray.getJSONObject(0);

                            strNama = data.getString("nama");
                            strAlamat = data.getString("alamat");
                            strLat = data.getString("lat");
                            strLng = data.getString("lng");
                            strDesk = data.getString("deskripsi");
                            strNohp = data.getString("no_hp");

                            id.setText(strId);
                            nama.setText(strNama);
                            alamat.setText(strAlamat);
                            lat.setText(strLat);
                            lng.setText(strLng);
                            desk.setText(strDesk);
                            no_hp.setText(strDesk);

                            Log.e("Data", strNama+" "+strAlamat+" "+strLat+" "+strLng+" "+strDesk+" "+strDesk+" "+strNohp);
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
                });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
