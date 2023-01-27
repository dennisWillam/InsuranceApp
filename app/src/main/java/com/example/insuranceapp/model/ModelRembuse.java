package com.example.insuranceapp.model;

public class ModelRembuse {
    private String tanggal;
    private String nama;
    private String benefit;
    private String totalBayar;
    private String tempat;
    private String namaDokter;
    private String email;
    private String status;
    private String url;
    private String uid;
    private String key;

    public ModelRembuse() {
    }

    public ModelRembuse(String tanggal, String nama, String benefit, String totalBayar, String tempat, String namaDokter, String email, String status, String url) {
        this.tanggal = tanggal;
        this.nama = nama;
        this.benefit = benefit;
        this.totalBayar = totalBayar;
        this.tempat = tempat;
        this.namaDokter = namaDokter;
        this.email = email;
        this.status = status;
        this.url = url;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(String totalBayar) {
        this.totalBayar = totalBayar;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
