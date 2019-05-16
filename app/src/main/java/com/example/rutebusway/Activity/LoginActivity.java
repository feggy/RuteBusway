package com.example.rutebusway.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.rutebusway.Constants;
import com.example.rutebusway.R;
import com.example.rutebusway.RequestHandler;
import com.example.rutebusway.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    String username, password;
    int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setStatusBarColor(getResources().getColor(R.color.green));

        if (SharedPrefManager.getInstance(this).isLogin()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etPassword = findViewById(R.id.etPassword);
        final TextView tvUsername = findViewById(R.id.tvUsername);
        final TextView tvPassword = findViewById(R.id.tvPassword);
        final Button btnLogin = findViewById(R.id.btnLogin);

        AlertDialog builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Internet")
                .setMessage("Aplikasi ini membutuhkan jaringan internet, jika device belum terhubung internet silahkan aktifkan internetnya terlebih dahulu")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        builder.show();

        checkLocation();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                Log.e("Login", "Username = "+username+" Password = "+password);

                if ( ContextCompat.checkSelfPermission( LoginActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

                    AlertDialog builder = new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Location Permission")
                            .setMessage("Aplikasi ini membutuhkan Location Permission")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(LoginActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            MY_PERMISSIONS_REQUEST_LOCATION);
                                }
                            })
                            .create();
                    builder.show();
                } else {
                    if (!username.isEmpty() && !password.isEmpty()){
                        login();
                    } else {
                        if (username.isEmpty()) {
                            tvUsername.setTextColor(getResources().getColor(R.color.colorAccent));
                            etUsername.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                        } else {
                            tvUsername.setTextColor(getResources().getColor(R.color.dark));
                            etUsername.getBackground().setColorFilter(getResources().getColor(R.color.dark), PorterDuff.Mode.SRC_ATOP);;
                        }

                        if (password.isEmpty()) {
                            tvPassword.setTextColor(getResources().getColor(R.color.colorAccent));
                            etPassword.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                        } else {
                            tvPassword.setTextColor(getResources().getColor(R.color.dark));
                            etPassword.getBackground().setColorFilter(getResources().getColor(R.color.dark), PorterDuff.Mode.SRC_ATOP);;
                        }

                        Snackbar.make(btnLogin,"Seluruh kolom harus di isi", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void login() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.optJSONObject("data");
                            if (jsonObject.getString("status").equals("success")) {
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                data.getString("idUser"),
                                                data.getString("namaUser"),
                                                data.getString("alamatUser"),
                                                data.getString("nomorTelepon"),
                                                data.getString("foto"),
                                                data.getString("hak_akses")
                                        );
                                Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_LONG).show();
                                finish();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                overridePendingTransition(0, 0);
                            } else {
                                Toast.makeText(getApplicationContext(), "Username atau password salah", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("nomor_telepon", username);
                params.put("password", password);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void checkLocation(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            AlertDialog builder = new AlertDialog.Builder(this)
                    .setTitle("Permission Location")
                    .setMessage("Aplikasi ini membutuhkan Location Permission")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    })
                    .create();
            builder.show();
        } else {

            /*if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog builder = new AlertDialog.Builder(this)
                        .setTitle("Permission Location")
                        .setMessage("Aplikasi ini membutuhkan Location Permission")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create();
                builder.show();
            }*/

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }
}
