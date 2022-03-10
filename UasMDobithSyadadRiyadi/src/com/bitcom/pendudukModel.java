package com.bitcom;

import java.util.Date;

public class pendudukModel {

    private String nik;
    private String nama;
    private String jk;
    private Date tgl;
    private String agama;

    public pendudukModel(String nik, String nama, String jk, Date tgl, String agama) {
        this.nik = nik;
        this.nama = nama;
        this.jk = jk;
        this.tgl = tgl;
        this.agama = agama;
    }

    public String getNik() {
        return nik;
    }

    public String getNama() {
        return nama;
    }

    public String getJk() {
        return jk;
    }

    public Date getTgl() {
        return tgl;
    }

    public String getAgama() {
        return agama;
    }


}
