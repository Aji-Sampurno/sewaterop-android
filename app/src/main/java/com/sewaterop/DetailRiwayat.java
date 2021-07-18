package com.sewaterop;

import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sewaterop.util.FormatCurrency;
import com.sewaterop.util.ServerAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailRiwayat extends AppCompatActivity {

    TextView idpesanan, namapemesan, namapaket, mulaipemesanan, akhirpemesanan, totalbiaya, totalbayar, kembalian, statusbayar;
    LinearLayout isipaketrow;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);

        /*get data from intent*/
        Intent data = getIntent();
        String intent_idpesanan = data.getStringExtra("idpesanan");
        /*end get data from intent*/

        idpesanan = (TextView) findViewById(R.id.tvidpesanan_rw);
        namapemesan = (TextView) findViewById(R.id.tvnamapemesan_rw);
        namapaket = (TextView) findViewById(R.id.tvnamapaket_rw);
        mulaipemesanan = (TextView) findViewById(R.id.tvmulaipemesanan_rw);
        akhirpemesanan = (TextView) findViewById(R.id.tvakhirpemesanan_rw);
        totalbiaya = (TextView) findViewById(R.id.tvtotalbiaya_rw);
        totalbayar = (TextView) findViewById(R.id.tvtotalbayar_rw);
        kembalian = (TextView) findViewById(R.id.tvkembalian_rw);
        statusbayar = (TextView) findViewById(R.id.tvstatus_rw);
        isipaketrow = findViewById(R.id.isi_paket_row);

        pd = new ProgressDialog(DetailRiwayat.this);
        loadJson(intent_idpesanan);


    }

    private void loadJson(String intent_idpesanan)
    {
        pd.setMessage("Memuat...");
        pd.setCancelable(false);
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        FormatCurrency currency = new FormatCurrency();
        StringRequest updateReq = new StringRequest(Request.Method.GET, ServerAPI.URL_DETAILRIWAYAT + intent_idpesanan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            idpesanan.setText(res.getString("id_pesanan"));
                            namapemesan.setText(res.getString("nama_pemesan"));
                            namapaket.setText(res.getString("nama_paket"));
                            mulaipemesanan.setText(res.getString("tgl_mulai") + " " + res.getString("waktu_mulai"));
                            akhirpemesanan.setText(res.getString("tgl_selesai") + " " + res.getString("waktu_selesai"));
                            totalbiaya.setText(currency.formatRupiah(res.getString("total_biaya")));
                            totalbayar.setText(currency.formatRupiah(res.getString("jumlah_bayar")));
                            kembalian.setText(currency.formatRupiah(res.getString("kembalian")));
                            statusbayar.setText(res.getString("status_pembayaran"));

                            String isipaketstr = res.getString("barang_sewaan");
                            JSONObject isipaketobj = new JSONObject(isipaketstr);

                            JSONArray res_jumlahbarang = isipaketobj.getJSONArray("jumlah_barang");
                            JSONArray res_namabarang = isipaketobj.getJSONArray("nama_barang");
                            JSONArray res_idbarang = isipaketobj.getJSONArray("id_barang");


                            for (int i = 0; i < res_idbarang.length(); i++) {
                                TextView tvisipaket = new TextView(getBaseContext());
                                tvisipaket.setId(View.generateViewId());
                                tvisipaket.setWidth(isipaketrow.getWidth());
//                                tvisipaket.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                                tvisipaket.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.black));
                                tvisipaket.setText(res_jumlahbarang.getString(i) + " " + res_namabarang.getString(i));
                                isipaketrow.addView(tvisipaket);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(DetailRiwayat.this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
                    }
                }){
        };

        queue.add(updateReq);
    }
}