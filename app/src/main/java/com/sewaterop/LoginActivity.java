package com.sewaterop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sewaterop.model.ModelData;
import com.sewaterop.util.AppController;
import com.sewaterop.util.Preferences;
import com.sewaterop.util.ServerAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.text.Html.fromHtml;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnLogin;
    ProgressDialog pd;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pd = new ProgressDialog(LoginActivity.this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnlogin);

//  untuk mengatur button login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String usernameKey = username.getText().toString();
//                String passwordKey = password.getText().toString();
                loadJson();

//                if (usernameKey.equals("admin") && passwordKey.equals("123")){
//                    //jika login berhasil
//                    Toast.makeText(getApplicationContext(), "LOGIN SUKSES", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    LoginActivity.this.startActivity(intent);
//                    finish();
//                }else {
//                    //jika login gagal
//                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                    builder.setMessage("Username atau Password Anda salah!")
//                            .setNegativeButton("Retry", null).create().show();
//                }
            }

        });

        TextView tvCreateAccount = (TextView)findViewById(R.id.tvCreateAccount);
//        TextView tvForgetPassword = (TextView)findViewById(R.id.tvforgetpass);

//  untuk mengatur butoon daftar (menuju halaman registrasi)
        tvCreateAccount.setText(fromHtml("Belum Punya Akun ? " + "</font><font color='#3b5998'>Daftar Sekarang</font>"));
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrasiActivity.class));
            }
        });

//  untuk menghilangkan action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void loadJson()
    {
        pd.setMessage("Proses Login");
        pd.setCancelable(false);
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest updateReq = new StringRequest(Request.Method.POST, ServerAPI.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.getBoolean("status_login")){
                                //jika login berhasil
                                //Toast.makeText(getApplicationContext(), "LOGIN SUKSES", Toast.LENGTH_SHORT).show();
                                Preferences.setKeyIdPemesan(getBaseContext(), res.getString("id_pemesan"));
                                Preferences.setRegisteredUser(getBaseContext(), res.getString("username"));
                                Preferences.setKeyNamaPemesan(getBaseContext(), res.getString("nama_pemesan"));
                                Preferences.setKeyAlamat(getBaseContext(), res.getString("alamat"));
                                Preferences.setKeyTelepon(getBaseContext(), res.getString("telepon"));
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            }else {
                                //jika login gagal
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Username atau Password Anda salah!")
                                        .setNegativeButton("Retry", null).create().show();
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
                        Toast.makeText(LoginActivity.this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("username",username.getText().toString());
                map.put("password",password.getText().toString());

                return map;
            }
        };

        queue.add(updateReq);
    }

//  mengatur tombol back
    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.moveTaskToBack(true);
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tekan Sekali lagi untuk Keluar", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
}