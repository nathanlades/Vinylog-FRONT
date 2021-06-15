package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import POJO.Perfil;

public class Profile extends AppCompatActivity {

    Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        perfil  = getIntent().getParcelableExtra("perfilIntent");
    }

    public void fromProfiletoSearch(View view){
        Intent intent = new Intent(Profile.this,Buscador.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromProfiletoFeed(View view){
        Intent intent = new Intent(Profile.this,FeedActivity.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromProfiletoCalendar(View view){
        Intent intent = new Intent(Profile.this,Calendar.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }
}