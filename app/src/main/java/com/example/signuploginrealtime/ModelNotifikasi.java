package com.example.signuploginrealtime;

public class ModelNotifikasi {
    private String name;
    private String activity;
    private String jenisLayanan;
    private String teleponprojek;
    private String deskripsiprojek;
    private String nomorDev;
    private String namaDev;
    private String key;

    public ModelNotifikasi(){

    }

    public ModelNotifikasi(String name, String activity, String jenisLayanan, String teleponprojek, String deskripsiprojek, String nomorDev, String namaDev) {
        this.name = name;
        this.activity = activity;
        this.jenisLayanan = jenisLayanan;
        this.teleponprojek = teleponprojek;
        this.deskripsiprojek = deskripsiprojek;
        this.nomorDev = nomorDev;
        this.namaDev = namaDev;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getJenisLayanan() {
        return jenisLayanan;
    }

    public void setJenisLayanan(String jenisLayanan) {
        this.jenisLayanan = jenisLayanan;
    }

    public String getTeleponprojek() {
        return teleponprojek;
    }

    public void setTeleponprojek(String teleponprojek) {
        this.teleponprojek = teleponprojek;
    }

    public String getDeskripsiprojek() {
        return deskripsiprojek;
    }

    public void setDeskripsiprojek(String deskripsiprojek) {
        this.deskripsiprojek = deskripsiprojek;
    }

    public String getNomorDev() {
        return nomorDev;
    }

    public void setNomorDev(String nomorDev) {
        this.nomorDev = nomorDev;
    }

    public String getNamaDev() {
        return namaDev;
    }

    public void setNamaDev(String namaDev) {
        this.namaDev = namaDev;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
