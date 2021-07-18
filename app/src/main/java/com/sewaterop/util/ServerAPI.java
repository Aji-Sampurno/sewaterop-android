package com.sewaterop.util;

public class ServerAPI {
    private static final String BASE_URL = "https://sewateropapi.000webhostapp.com/api";
    public static final String URL_DATA = BASE_URL + "/getlistsewaforuser?id_penyewa=";
    public static final String URL_RIWAYAT = BASE_URL + "/getlistsewahistory?id_penyewa=";
    public static final String URL_LOGIN = BASE_URL + "/login";
    public static final String URL_REGISTER= BASE_URL + "/signup";
    public static final String URL_DETAILSEWA = BASE_URL + "/getdetailsewa?id_sewaan=";
    public static final String URL_DETAILRIWAYAT = BASE_URL + "/detailhistory?id_sewaan=";
    public static final String URL_ALLPAKET = BASE_URL + "/getlistpaket";
    public static final String URL_CREATESEWA = BASE_URL + "/addsewa";
    public static final String URL_FINISHSEWA = BASE_URL + "/finishsewa";
}
