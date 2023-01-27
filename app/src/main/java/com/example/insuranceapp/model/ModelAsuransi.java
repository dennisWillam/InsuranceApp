package com.example.insuranceapp.model;

public class ModelAsuransi {

    private String nama;
    private String desciption;
    private String harga;
    private String Key;

    public ModelAsuransi(){

    }

    public ModelAsuransi(String nama, String desciption, String harga) {
        this.nama = nama;
        this.desciption = desciption;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}
