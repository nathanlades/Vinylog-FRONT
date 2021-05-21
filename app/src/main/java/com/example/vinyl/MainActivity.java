package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario, etPass;
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsuario = findViewById(R.id.et_user);
        etPass = findViewById(R.id.et_pass);
        btLogin = findViewById(R.id.bt_login);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            etUsuario.setText(extras.getString("usuarioIntent"));
        }
    }

    public void registro(View view){
        Intent i = new Intent(this, SigninActivity.class);
        startActivity(i);
    }
}