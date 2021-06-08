package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import POJO.Perfil;

public class FeedActivity extends AppCompatActivity {

    String mensajeBienvenida = "Bienvenido a Vinyl, ";
    ImageView ivImagenPerfil, ivPortada, ivOpcionesUsuario, ivLikes, ivComentarios, ivCompartir;
    TextView tvUsuario;
    Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        tvUsuario = findViewById(R.id.tv_user);

        // Recogemos el objeto Perfil que nos pasa validarUsuario() desde MainActivity.java
        perfil  = getIntent().getParcelableExtra("perfilIntent");
        // Prueba para mostrar un mensaje de bienvenida dirigido al usuario que ha iniciado sesión
        tvUsuario.setText(mensajeBienvenida.concat(perfil.getUsuario()));
    }

    public void fromFeedtoSearch(View view){
        Intent intent = new Intent(FeedActivity.this,Buscador.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromFeedtoCalendar(View view){
        Intent intent = new Intent(FeedActivity.this,Calendar.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromFeedtoProfile(View view){
        Intent intent = new Intent(FeedActivity.this,Profile.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }
}