package com.sewaterop.model;

public class ModelDataRiwayat {String id_pesanan, nama_pemesan, tgl_mulai, waktu_mulai, tgl_selesai, waktu_selesai, status_pembayaran;
    public ModelDataRiwayat(){}

    public ModelDataRiwayat(String id_pesanan, String nama_pemesan, String tgl_mulai, String waktu_mulai, String tgl_selesai, String waktu_selesai, String status_pembayaran) {
        this.id_pesanan = id_pesanan;
        this.nama_pemesan = nama_pemesan;
        this.tgl_mulai = tgl_mulai;
        this.waktu_mulai = waktu_mulai;
        this.tgl_selesai = tgl_selesai;
        this.waktu_selesai = waktu_selesai;
        this.status_pembayaran = status_pembayaran;
    }

    public String getIdPesanan() {
        return id_pesanan;
    }

    public void setIdPesanan(String id_pesanan) {
        this.id_pesanan = id_pesanan;
    }

    public String getNamaPemesan() {
        return nama_pemesan;
    }

    public void setNamaPemesan(String nama_pemesan) {
        this.nama_pemesan = nama_pemesan;
    }

    public String getTglMulai() {
        return tgl_mulai;
    }

    public void setTglMulai(String tgl_mulai) {
        this.tgl_mulai = tgl_mulai;
    }

    public String getWaktuMulai() {
        return waktu_mulai;
    }

    public void setWaktuMulai(String waktu_mulai) {
        this.waktu_mulai = waktu_mulai;
    }

    public String getTglSelesai() {
        return tgl_selesai;
    }

    public void setTglSelesai(String tgl_selesai) {
        this.tgl_selesai = tgl_selesai;
    }

    public String getWaktuSelesai() {
        return waktu_selesai;
    }

    public void setWaktuSelesai(String waktu_selesai) {
        this.waktu_selesai = waktu_selesai;
    }

    public String getStatusPembayaran() { return status_pembayaran; }

    public void setStatusPembayaran(String status_pembayaran) { this.status_pembayaran = status_pembayaran; }


}