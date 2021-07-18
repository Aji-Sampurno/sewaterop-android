package com.sewaterop.util;


import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import androidx.core.net.ParseException;

public class FormatCurrency {

  public String formatRupiah(String harga_param) {

    double harga = Double.parseDouble(harga_param);

    DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

    formatRp.setCurrencySymbol("Rp.");
    formatRp.setMonetaryDecimalSeparator(',');
    formatRp.setGroupingSeparator('.');

    kursIndonesia.setDecimalFormatSymbols(formatRp);
    String x = kursIndonesia.format(harga);
    return x;
  }
}
