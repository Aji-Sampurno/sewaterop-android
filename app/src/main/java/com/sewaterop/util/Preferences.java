package com.sewaterop.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    static final String KEY_USER_TEREGISTER ="username", KEY_PASS_TEREGISTER ="password";
    static final String KEY_ID_PEMESAN ="id_pemesan";
    static final String KEY_NAMA_PEMESAN = "nama_pemesan";
    static final String KEY_ALAMAT = "alamat";
    static final String KEY_TELEPON = "telepon";

    public static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setRegisteredUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_TEREGISTER, username);
        editor.apply();
    }
    public static String getRegisteredUser(Context context){
        return getSharedPreference(context).getString(KEY_USER_TEREGISTER,"");
    }

    public static void setRegisteredPass(Context context, String password){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_PASS_TEREGISTER, password);
        editor.apply();
    }
    public static String getRegisteredPass(Context context){
        return getSharedPreference(context).getString(KEY_PASS_TEREGISTER,"");
    }

    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_USER_TEREGISTER);
        editor.remove(KEY_PASS_TEREGISTER);
        editor.remove(KEY_ID_PEMESAN);
        editor.remove(KEY_NAMA_PEMESAN);
        editor.remove(KEY_ALAMAT);
        editor.remove(KEY_TELEPON);
        editor.apply();
    }

//    Id pemesan
    public static void setKeyIdPemesan(Context context, String id_pemesan){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_ID_PEMESAN, id_pemesan);
        editor.apply();
    }
    public static String getKeyIdPemesan(Context context){
        return getSharedPreference(context).getString(KEY_ID_PEMESAN,"");
    }

//    Nama pemesan
    public static void setKeyNamaPemesan(Context context, String nama_pemesan){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NAMA_PEMESAN, nama_pemesan);
        editor.apply();
    }
    public static String getKeyNamaPemesan(Context context){
        return getSharedPreference(context).getString(KEY_NAMA_PEMESAN,"");
    }

    //    Alamat
    public static void setKeyAlamat(Context context, String alamat){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_ALAMAT, alamat);
        editor.apply();
    }
    public static String getKeyAlamat(Context context){
        return getSharedPreference(context).getString(KEY_ALAMAT,"");
    }
    //    Telepon
    public static void setKeyTelepon(Context context, String telepon){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_TELEPON, telepon);
        editor.apply();
    }
    public static String getKeyTelepon(Context context){
        return getSharedPreference(context).getString(KEY_TELEPON,"");
    }




}