package com.sewaterop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.sewaterop.util.ServerAPI;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static android.text.Html.FROM_HTML_OPTION_USE_CSS_COLORS;
import static android.text.Html.fromHtml;

public class RegistrasiActivity extends AppCompatActivity {

    TextView tvRegister;
    TextInputEditText namapemesan, username, password, repassword, alamat, telepon;
    Button btnregister;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        tvRegister = (TextView)findViewById(R.id.tvLogin);
        namapemesan = findViewById(R.id.regnamalengkap);
        username = findViewById(R.id.regusername);
        password = findViewById(R.id.regpassword);
        repassword = findViewById(R.id.regrepassword);
        alamat = findViewById(R.id.regalamat);
        telepon = findViewById(R.id.regnotelp);
        btnregister = findViewById(R.id.btnregister);
        int[] forms = new int[]{R.id.regnamalengkap, R.id.regusername, R.id.regpassword, R.id.regrepassword, R.id.regalamat, R.id.regnotelp};
        pd = new ProgressDialog(RegistrasiActivity.this);

        tvRegister.setText(fromHtml("Sudah Punya Akun ? " + "</font><font color='#3b5998'>Login</font>", FROM_HTML_OPTION_USE_CSS_COLORS));

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrasiActivity.this, LoginActivity.class));
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrasiActivity.this);
                builder.setTitle("Konfirmasi").
                        setMessage("Pastikan data yang Anda isi sudah benar.\nLanjutkan?");
                builder.setPositiveButton("Buat akun",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                goRegister();
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

    private void goRegister() {
        pd.setMessage("Membuat Akun...");
        pd.setCancelable(false);
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest updateReq = new StringRequest(Request.Method.POST, ServerAPI.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(RegistrasiActivity.this, res.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(RegistrasiActivity.this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("nama_pemesan", namapemesan.getText().toString());
                map.put("username", username.getText().toString());
                map.put("password", password.getText().toString());
                map.put("alamat", alamat.getText().toString());
                map.put("telepon", telepon.getText().toString());
                System.out.println(map);
                return map;
            }
        };
        queue.add(updateReq);
    }
}