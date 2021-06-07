package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import POJO.Perfil;

public class FeedActivity extends AppCompatActivity {

    String mensajeBienvenida = "Bienvenido a Vinyl, ";
    ImageView ivImagenPerfil, ivPortada, ivOpcionesUsuario, ivLikes, ivComentarios, ivCompartir;
    TextView tvUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        tvUsuario = findViewById(R.id.tv_user);

        // Recogemos el objeto Perfil que nos pasa validarUsuario() desde MainActivity.java
        Perfil extras = (Perfil) getIntent().getSerializableExtra("PerfilIntent");
        // Prueba para mostrar un mensaje de bienvenida dirigido al usuario que ha iniciado sesión
        tvUsuario.setText(mensajeBienvenida.concat(extras.getUsuario()));
    }
}