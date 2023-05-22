package com.example.signuploginrealtime;

import androidx.interpolator.view.animation.FastOutLinearInInterpolator;

public class HelperClass {

    String name, email, password, notelp, Foto, String;

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String Foto) {
        this.Foto = Foto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public HelperClass(String name, String email, String password, String notelp, String Foto) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.notelp = notelp;
        this.Foto = Foto;
    }
}
