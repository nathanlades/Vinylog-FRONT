package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;


import POJO.Perfil;

public class Portada extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);
        progressBar = findViewById(R.id.pb_portada);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
                boolean session = sharedPreferences.getBoolean("session", false);
                if (session){
                    Intent intent = new Intent(Portada.this, FeedActivity.class);
                    String perfilJSON = sharedPreferences.getString("perfilJSON","");
                    Perfil perfil = new Gson().fromJson(perfilJSON, Perfil.class);
                    intent.putExtra("perfilIntent", perfil);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Portada.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                            }
        }, 2000);
    }
}