package com.sewaterop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.sewaterop.util.Preferences;

public class SplashScreen extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img = findViewById(R.id.img);
        img.animate().alpha(2000).setDuration(0);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Preferences.getKeyIdPemesan(getApplicationContext()) != "") {
                    Intent dsp = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(dsp);
                } else {
                    Intent dsp = new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(dsp);
                }
                finish();
            }
        },1000);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}