package com.example.sewaterop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static android.text.Html.fromHtml;

public class ForgetPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        TextView tvRegister = (TextView)findViewById(R.id.tvLogin);

        tvRegister.setText(fromHtml("Ingat Password ? " + "</font><font color='#3b5998'>Login</font>"));

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPassActivity.this, LoginActivity.class));
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}