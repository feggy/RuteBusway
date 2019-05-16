package com.example.rutebusway;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mContext;

    private static final String SHARED_PREF_NAME = "mysharedpref";
    private static final String KEY_ID = "idUser";
    private static final String KEY_NAMA_USER = "namaUser";
    private static final String KEY_ALAMAT = "alamatUser";
    private static final String KEY_NOMOR_TELPON = "nomorTelepon";
    private static final String KEY_FOTO = "foto";
    private static final String KEY_HAK_AKSES = "hak_akses";

    private SharedPrefManager(Context context){
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(String id, String namaUser, String alamatUser, String nomorTelepon, String foto, String hak_akses){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_ID, id);
        editor.putString(KEY_NAMA_USER, namaUser);
        editor.putString(KEY_ALAMAT, alamatUser);
        editor.putString(KEY_NOMOR_TELPON, nomorTelepon);
        editor.putString(KEY_FOTO, foto);
        editor.putString(KEY_HAK_AKSES, hak_akses);

        editor.apply();

        return true;
    }

    public boolean isLogin(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.getString(KEY_NOMOR_TELPON, null) != null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public static String getKeyNamaUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAMA_USER, null);
    }
}
