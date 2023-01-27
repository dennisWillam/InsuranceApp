package com.example.insuranceapp.model;

public class ModelAsuransiUser {
    private String nama;
    private String desc;
    private String harga;
    private String email;
    private String status;
    private String Key;

    public ModelAsuransiUser() {
    }

    public ModelAsuransiUser(String email, String nama, String desc, String harga, String status) {
        this.email = email;
        this.nama = nama;
        this.desc = desc;
        this.harga = harga;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
