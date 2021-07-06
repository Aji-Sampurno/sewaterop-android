package com.example.sewaterop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.sewaterop.sewaan.DaftarBarang;
import com.example.sewaterop.sewaan.PaketSewa;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SewaBaruActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btntanggal1, btnwaktu1, btntanggal2, btnwaktu2, btnpesan;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private RadioGroup radiogroup;
    private RadioButton radiopaket, radiobarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa_baru);
        getSupportActionBar().setTitle("Sewa Baru");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radiogroup = (RadioGroup)findViewById(R.id.radiogrup);
        radiopaket = (RadioButton)findViewById(R.id.paketsewa);
        radiobarang = (RadioButton)findViewById(R.id.daftarbarang);

        btntanggal1 = (Button) findViewById(R.id.btntanggalawal);
        btnwaktu1 = (Button) findViewById(R.id.btnwaktuawal);
        btntanggal2 = (Button)findViewById(R.id.btntanggalakhir);
        btnwaktu2 = (Button) findViewById(R.id.btnwaktuakhir);
        btnpesan = (Button) findViewById(R.id.btn_buatpesanan);

        btntanggal1.setOnClickListener(this);
        btnwaktu1.setOnClickListener(this);
        btntanggal2.setOnClickListener(this);
        btnwaktu2.setOnClickListener(this);

        btnpesan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(SewaBaruActivity.this,
                        "Tanggal Mulai: " + btntanggal1.getText().toString() + "\n" +
                                "Waktu Mulai : " + btnwaktu1.getText().toString() + "\n" +
                                "Tanggal Berakhir :"+ btntanggal2.getText().toString() + "\n" +
                                "Waktu Berakhir :" + btnwaktu2.getText().toString()
                        , Toast.LENGTH_SHORT
                ).show();
            }
        });

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.paketsewa:
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        PaketSewa fragmentpaket = new PaketSewa();
                        transaction.replace(R.id.frameLayout, fragmentpaket);
                        transaction.addToBackStack("fragmentPertama");
                        transaction.commit();
                        break;
                    case R.id.daftarbarang:
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction transaction1 = fragmentManager1.beginTransaction();
                        DaftarBarang fragmentdaftar = new DaftarBarang();
                        transaction1.replace(R.id.frameLayout, fragmentdaftar);
                        transaction1.addToBackStack("fragmentPertama");
                        transaction1.commit();
                        break;
                }
            }
        });
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
                        }, mHour, mMinute, false);
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
                        }, mHour, mMinute, false);
                timePickerDialogakhir.show();
                break;
        }
    }
}