package com.sewaterop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.sewaterop.util.FormatCurrency;
import com.sewaterop.util.Preferences;
import com.sewaterop.util.ServerAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class BayarSewaan extends AppCompatActivity {
    TextView idpesanan, namapemesan, namapaket, mulaipemesanan, akhirpemesanan, totalbiaya, kembalian, statusbayar;
    TextInputEditText inputjumlahbayar;
    Button btnbayar;
    LinearLayout isipaketrow;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_sewaan);

        Intent data = getIntent();
        String intent_id_pesanan = data.getStringExtra("id_pesanan");
        String intent_id_paket = data.getStringExtra("id_paket");
        String intent_nama_pemesan = data.getStringExtra("nama_pemesan");
        String intent_nama_paket = data.getStringExtra("nama_paket");
        String intent_mulai_pemesanan = data.getStringExtra("mulai_pemesanan");
        String intent_akhir_pemesanan = data.getStringExtra("akhir_pemesanan");
        String intent_total_biaya = data.getStringExtra("total_biaya");
        String intent_total_biaya_num = data.getStringExtra("total_biaya_num");
        btnbayar = findViewById(R.id.btnbayar);
        idpesanan = findViewById(R.id.tvidpesananbayar);
        namapemesan = findViewById(R.id.tvnamapemesanbayar);
        namapaket = findViewById(R.id.tvnamapaketbayar);
        mulaipemesanan = findViewById(R.id.tvmulaipemesananbayar);
        akhirpemesanan = findViewById(R.id.tvakhirpemesananbayar);
        totalbiaya = findViewById(R.id.tvtotalbiayabayar);
        inputjumlahbayar = findViewById(R.id.jumlahbayar);
        kembalian = findViewById(R.id.tvkembalian_rwbayar);
        statusbayar = findViewById(R.id.tvstatus_rwbayar);
        pd = new ProgressDialog(BayarSewaan.this);

        idpesanan.setText(intent_id_pesanan);
        namapemesan.setText(intent_nama_pemesan);
        namapaket.setText(intent_nama_paket);
        mulaipemesanan.setText(intent_mulai_pemesanan);
        akhirpemesanan.setText(intent_akhir_pemesanan);
        totalbiaya.setText(intent_total_biaya);
        totalbiaya.setContentDescription(intent_total_biaya_num);
        kembalian.setText("0");
        FormatCurrency currency = new FormatCurrency();

        inputjumlahbayar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    try {
                        int jumlahbayarnum = Integer.parseInt(inputjumlahbayar.getText().toString());
                        int kembaliannum = jumlahbayarnum - Integer.parseInt(intent_total_biaya_num);
                        if (kembaliannum < 0) {
                            statusbayar.setText("Kurang " + currency.formatRupiah(String.valueOf(kembaliannum * -1)));
                        } else {
                            statusbayar.setText("Lunas");
                        }
                        kembalian.setText(currency.formatRupiah(String.valueOf(kembaliannum)));
                        kembalian.setContentDescription(String.valueOf(kembaliannum));
                    } catch (NumberFormatException e) {
                        Toast.makeText(BayarSewaan.this, "Maksimal angka tercapai", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    kembalian.setText("0");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnbayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BayarSewaan.this);
                builder.setTitle("Konfirmasi Pembayaran").
                        setMessage("Pembayaran akan diproses dan sewa akan dipindahkan ke Riwayat.\nLanjutkan?");
                builder.setPositiveButton("Bayar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                submitData(intent_id_paket);
                            }
                        });
                builder.setNegativeButton("Batal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });
    }

    private void submitData(String idpaket) {
        pd.setMessage("Menyelesaikan Sewa...");
        pd.setCancelable(false);
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest updateReq = new StringRequest(Request.Method.POST, ServerAPI.URL_FINISHSEWA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(BayarSewaan.this, res.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(BayarSewaan.this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id_sewaan", idpesanan.getText().toString());
                map.put("id_paket", idpaket);
                map.put("total_biaya", totalbiaya.getContentDescription().toString());
                map.put("kembalian", kembalian.getContentDescription().toString());
                map.put("status_pembayaran", statusbayar.getText().toString());
                map.put("jumlah_bayar", inputjumlahbayar.getText().toString());
                System.out.println(map);

                return map;
            }
        };
        queue.add(updateReq);
    }

}