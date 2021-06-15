package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import POJO.Perfil;

public class Calendar extends AppCompatActivity {

    Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        perfil  = getIntent().getParcelableExtra("perfilIntent");
    }

    public void fromCalendartoSearch(View view){
        Intent intent = new Intent(Calendar.this,Buscador.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromCalendartoFeed(View view){
        Intent intent = new Intent(Calendar.this,FeedActivity.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromCalendartoProfile(View view){
        Intent intent = new Intent(Calendar.this, ProfileActivity.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }
}