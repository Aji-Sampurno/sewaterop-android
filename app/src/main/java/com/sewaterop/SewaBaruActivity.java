package com.sewaterop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sewaterop.sewaan.DaftarBarang;
import com.sewaterop.sewaan.PaketSewa;

import java.util.Calendar;

public class SewaBaruActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btntanggal1, btnwaktu1, btntanggal2, btnwaktu2, btnpesan;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private RadioGroup radiogroup;
    private RadioButton radiopaket1, radiopaket2, radiopaket3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa_baru);
        getSupportActionBar().setTitle("Sewa Baru");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radiogroup = (RadioGroup)findViewById(R.id.radiogrup);
        radiopaket1 = (RadioButton)findViewById(R.id.basic);
        radiopaket2 = (RadioButton)findViewById(R.id.premium);
        radiopaket3 = (RadioButton)findViewById(R.id.medium);

        btntanggal1 = (Button) findViewById(R.id.btntanggalawal);
        btnwaktu1 = (Button) findViewById(R.id.btnwaktuawal);
        btntanggal2 = (Button)findViewById(R.id.btntanggalakhir);
        btnwaktu2 = (Button) findViewById(R.id.btnwaktuakhir);
        btnpesan = (Button) findViewById(R.id.btn_buatpesanan);

        btntanggal1.setOnClickListener(this);
        btnwaktu1.setOnClickListener(this);
        btntanggal2.setOnClickListener(this);
        btnwaktu2.setOnClickListener(this);

//        btnpesan.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(SewaBaruActivity.this,
//                        "Tanggal Mulai: " + btntanggal1.getText().toString() + "\n" +
//                                "Waktu Mulai : " + btnwaktu1.getText().toString() + "\n" +
//                                "Tanggal Berakhir :"+ btntanggal2.getText().toString() + "\n" +
//                                "Waktu Berakhir :" + btnwaktu2.getText().toString() +"\n" +
//                        , Toast.LENGTH_SHORT
//                ).show();
//            }
//        });

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id=group.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton) findViewById(checkedId);

                String radioText=rb.getText().toString();
                btnpesan.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Toast.makeText(SewaBaruActivity.this,
                                "Tanggal Mulai: " + btntanggal1.getText().toString() + "\n" +
                                        "Waktu Mulai : " + btnwaktu1.getText().toString() + "\n" +
                                        "Tanggal Berakhir :"+ btntanggal2.getText().toString() + "\n" +
                                        "Waktu Berakhir :" + btnwaktu2.getText().toString() +"\n" +
                                        "Pesanan : " + rb.getText().toString(), Toast.LENGTH_SHORT
                        ).show();
                    }
                });
//                switch (checkedId){
//                    case R.id.basic:
//                        btnpesan.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View view) {
//                                Toast.makeText(SewaBaruActivity.this,
//                                        "Tanggal Mulai: " + btntanggal1.getText().toString() + "\n" +
//                                                "Waktu Mulai : " + btnwaktu1.getText().toString() + "\n" +
//                                                "Tanggal Berakhir :"+ btntanggal2.getText().toString() + "\n" +
//                                                "Waktu Berakhir :" + btnwaktu2.getText().toString() + "\n" +
//                                                radiopaket1.getText().toString(), Toast.LENGTH_SHORT
//                                ).show();
//                            }
//                        });
//                        break;
//                    case R.id.premium:
//                        btnpesan.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View view) {
//                                Toast.makeText(SewaBaruActivity.this,
//                                        "Tanggal Mulai: " + btntanggal1.getText().toString() + "\n" +
//                                                "Waktu Mulai : " + btnwaktu1.getText().toString() + "\n" +
//                                                "Tanggal Berakhir :"+ btntanggal2.getText().toString() + "\n" +
//                                                "Waktu Berakhir :" + btnwaktu2.getText().toString() + "\n" +
//                                                radiopaket2.getText().toString(), Toast.LENGTH_SHORT
//                                ).show();
//                            }
//                        });
//                        break;
//                    case R.id.medium:
//                        btnpesan.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View view) {
//                                Toast.makeText(SewaBaruActivity.this,
//                                        "Tanggal Mulai: " + btntanggal1.getText().toString() + "\n" +
//                                                "Waktu Mulai : " + btnwaktu1.getText().toString() + "\n" +
//                                                "Tanggal Berakhir :"+ btntanggal2.getText().toString() + "\n" +
//                                                "Waktu Berakhir :" + btnwaktu2.getText().toString() + "\n" +
//                                                radiopaket3.getText().toString(), Toast.LENGTH_SHORT
//                                ).show();
//                            }
//                        });
//                        break;
//                }
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