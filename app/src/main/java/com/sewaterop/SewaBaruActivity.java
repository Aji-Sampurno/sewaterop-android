package com.sewaterop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sewaterop.sewaan.DaftarBarang;
import com.sewaterop.sewaan.PaketSewa;
import com.sewaterop.util.FormatCurrency;
import com.sewaterop.util.Preferences;
import com.sewaterop.util.ServerAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SewaBaruActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btntanggal1, btnwaktu1, btntanggal2, btnwaktu2, btnpesan;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private RadioGroup radiogroup;
    private RadioButton radiopaket1, radiopaket2, radiopaket3;
    private ProgressBar loadingRadio;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa_baru);
        getSupportActionBar().setTitle("Sewa Baru");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radiogroup = (RadioGroup)findViewById(R.id.radiogrup);
        pd = new ProgressDialog(SewaBaruActivity.this);
        btntanggal1 = (Button) findViewById(R.id.btntanggalawal);
        btnwaktu1 = (Button) findViewById(R.id.btnwaktuawal);
        btntanggal2 = (Button)findViewById(R.id.btntanggalakhir);
        btnwaktu2 = (Button) findViewById(R.id.btnwaktuakhir);
        btnpesan = (Button) findViewById(R.id.btn_buatpesanan);
        loadingRadio = findViewById(R.id.progressBar1);

        loadingRadio.setIndeterminate(true);
        btntanggal1.setOnClickListener(this);
        btnwaktu1.setOnClickListener(this);
        btntanggal2.setOnClickListener(this);
        btnwaktu2.setOnClickListener(this);
        createRadioPaket();

//        btnpesan.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(SewaBaruActivity.this,
//                        "Tanggal Mulai: " + btntanggal1.getText().toString() + "\n" +
//                                "Waktu Mulai : " + btnwaktu1.getText().toString() + "\n" +
//                                "Tanggal Berakhir :"+ btntanggal2.getText().toString() + "\n" +
//                                "Waktu Berakhir :" + btnwaktu2.getText().toString() +"\n" +
//                                "Waktu Berakhir :" + btnwaktu2.getText().toString() +"\n" +
//                                "Paket sewa :" + btnwaktu2.getText().toString() +"\n" +
//                        , Toast.LENGTH_SHORT
//                ).show();
//            }
//        });


        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id=group.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton) findViewById(checkedId);
                btnpesan.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(SewaBaruActivity.this,
//                                "Tanggal Mulai: " + btntanggal1.getText().toString() + "\n" +
//                                        "Waktu Mulai : " + btnwaktu1.getText().toString() + "\n" +
//                                        "Tanggal Berakhir :"+ btntanggal2.getText().toString() + "\n" +
//                                        "Waktu Berakhir :" + btnwaktu2.getText().toString() +"\n" +
//                                        "Paket Sewa : " + rb.getContentDescription().toString(), Toast.LENGTH_SHORT
//                        ).show();
                        submitData(rb.getContentDescription().toString());
                    }
                });
            }
        });
    }

    private void createRadioPaket() {
        RequestQueue queue = Volley.newRequestQueue(this);
        FormatCurrency currency = new FormatCurrency();
        StringRequest updateReq = new StringRequest(Request.Method.GET, ServerAPI.URL_ALLPAKET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loadingRadio.setVisibility(View.GONE);
                        try {
                            JSONObject res = new JSONObject(response);
                            Iterator idKeys = res.keys();
                            RadioGroup.LayoutParams radioparams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                            radioparams.setMargins(30,30,30,30);
                            while(idKeys.hasNext()) {
                                JSONObject paket = res.getJSONObject((String) idKeys.next());
                                JSONArray jumlah_barang = paket.getJSONArray("jumlah_barang");
                                JSONArray nama_barang = paket.getJSONArray("nama_barang");
                                ArrayList isipaketarray = new ArrayList();

                                for (int i = 0; i < nama_barang.length(); i++) {
                                    isipaketarray.add(jumlah_barang.getString(i) + " " + nama_barang.getString(i));
                                }
                                String isi_paket = TextUtils.join(", ",  isipaketarray);

                                RadioButton radiopaket = new RadioButton(getBaseContext());
                                radiopaket.setId(View.generateViewId());
                                radiopaket.setWidth(radioparams.width);
                                radiopaket.setLayoutParams(radioparams);
                                radiopaket.setContentDescription(paket.getString("id_paket"));
                                radiopaket.setText(paket.getString("nama_paket") + "\n" + isi_paket + "\n" + currency.formatRupiah(paket.getString("harga")));
                                radiogroup.addView(radiopaket);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loadingRadio.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingRadio.setVisibility(View.GONE);
                        Toast.makeText(SewaBaruActivity.this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
                    }
                }){
        };
        queue.add(updateReq);
    }

    private void submitData(String idpaket) {
        pd.setMessage("Membuat Sewa...");
        pd.setCancelable(false);
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        FormatCurrency currency = new FormatCurrency();
        StringRequest updateReq = new StringRequest(Request.Method.POST, ServerAPI.URL_CREATESEWA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(SewaBaruActivity.this, res.getString("message"), Toast.LENGTH_LONG).show();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(SewaBaruActivity.this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id_penyewa", Preferences.getKeyIdPemesan(getBaseContext()));
                map.put("tgl_mulai", btntanggal1.getContentDescription().toString());
                map.put("waktu_mulai", btnwaktu1.getText().toString());
                map.put("tgl_selesai", btntanggal2.getContentDescription().toString());
                map.put("waktu_selesai", btnwaktu2.getText().toString());
                map.put("tipe_sewaan", "paket");
                map.put("id_paket", idpaket);
                System.out.println(map);

                return map;
            }
        };
        queue.add(updateReq);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btntanggalawal:

                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialogawal = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                btntanggal1.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                btntanggal1.setContentDescription(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialogawal.show();
                break;
            case R.id.btnwaktuawal:

                c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialogawal = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                btnwaktu1.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, true);
                timePickerDialogawal.show();
                break;
            case R.id.btntanggalakhir:

                Calendar c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialogakhir = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                btntanggal2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                btntanggal2.setContentDescription(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialogakhir.show();
                break;
            case R.id.btnwaktuakhir:

                c1 = Calendar.getInstance();
                mHour = c1.get(Calendar.HOUR_OF_DAY);
                mMinute = c1.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialogakhir = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                btnwaktu2.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, true);
                timePickerDialogakhir.show();
                break;
        }
    }
}