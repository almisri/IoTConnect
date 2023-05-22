package com.example.signuploginrealtime;

public class PesananClass {
    String name;
    String teleponprojek;
    String deskripsiprojek;
    String activity;
    String nomorDev;
    String namaDev;
    String jenisLayanan;

    public PesananClass(String name, String teleponprojek, String deskripsiprojek, String activity, String nomorDev, String namaDev, String jenisLayanan) {
        this.name = name;
        this.teleponprojek = teleponprojek;
        this.deskripsiprojek = deskripsiprojek;
        this.activity=activity;
        this.nomorDev=nomorDev;
        this.namaDev=namaDev;
        this.jenisLayanan=jenisLayanan;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getTeleponprojek(){
        return teleponprojek;
    }

    public void setTeleponprojek(String teleponprojek){
        this.teleponprojek = teleponprojek;
    }

    public String getDeskripsiprojek(){
        return deskripsiprojek;
    }

    public void setDeskripsiprojek(String deskripsiprojek){
        this.deskripsiprojek = deskripsiprojek;
    }
    public String getActivity(){
        return activity;
    }

    public void setActivity(String activity){
        this.activity = activity;
    }
    public String getNomorDev(){
        return nomorDev;
    }

    public void setNomorDev(String nomorDev){
        this.nomorDev = nomorDev;
    }
    public String getNamaDev() {
        return namaDev;
    }

    public void setNamaDev(String namaDev) {
        this.namaDev = namaDev;
    }

    public String getJenisLayanan() {
        return jenisLayanan;
    }

    public void setJenisLayanan(String jenisLayanan) {
        this.jenisLayanan = jenisLayanan;
    }
}
