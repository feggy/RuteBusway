package com.example.rutebusway.Models;

public class Algoritma {

    public String id, nama, lat, lng, jarak, waktu;
//    double jarak;

    public Algoritma(String id, String nama, String lat, String lng, String jarak, String waktu) {
        this.id = id;
        this.nama = nama;
        this.lat = lat;
        this.lng = lng;
        this.jarak = jarak;
        this.waktu = waktu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
