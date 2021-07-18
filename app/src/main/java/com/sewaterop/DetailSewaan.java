package com.sewaterop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailSewaan extends AppCompatActivity {

    TextView idpesanan,tglmulai,tglselesai,statuspembayaran;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sewaan);

        /*get data from intent*/
        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intent_idpesanan = data.getStringExtra("username");
        String intent_tglmulai = data.getStringExtra("grup");
        String intent_tglselesai = data.getStringExtra("nama");
        String intent_statuspembayaran = data.getStringExtra("password");
        /*end get data from intent*/

        idpesanan = (TextView) findViewById(R.id.tvidpesanan);
        tglmulai = (TextView) findViewById(R.id.tvmulaipemesanan);
        tglselesai = (TextView) findViewById(R.id.tvakhirpemesanan);
        statuspembayaran = (TextView) findViewById(R.id.tvstatus);
        pd = new ProgressDialog(DetailSewaan.this);

        /*kondisi update / insert*/
        if(update == 1) {
            idpesanan.setText(intent_idpesanan);
            idpesanan.setVisibility(View.VISIBLE);
            tglmulai.setText(intent_tglmulai);
            tglselesai.setText(intent_tglselesai);
            statuspembayaran.setText(intent_statuspembayaran);
        }
    }
}