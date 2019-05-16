package com.example.rutebusway.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rutebusway.R;

public class DetailActivity extends AppCompatActivity {

    String strId, strNama, strAlamat, strLat, strLng, strDeskripsi, strNo_hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            strId = bundle.getString("id");
            strNama = bundle.getString("nama");
            strAlamat = bundle.getString("alamat");
            strLat = bundle.getString("lat");
            strLng = bundle.getString("lng");
            strDeskripsi = bundle.getString("deskripsi");
            strNo_hp = bundle.getString("no_hp");
        }

        TextView id = findViewById(R.id.vId);
        TextView nama = findViewById(R.id.vNama);
        TextView alamat = findViewById(R.id.vAlamat);
        TextView lat = findViewById(R.id.vlat);
        TextView lng = findViewById(R.id.vLong);
        TextView deskripsi = findViewById(R.id.vDeskripsi);
        TextView no_hp = findViewById(R.id.vNohp);

        id.setText(strId);
        nama.setText(strNama);
        alamat.setText(strAlamat);
        lat.setText(strLat);
        lng.setText(strLng);
        deskripsi.setText(strDeskripsi);
        no_hp.setText(strNo_hp);
    }
}
