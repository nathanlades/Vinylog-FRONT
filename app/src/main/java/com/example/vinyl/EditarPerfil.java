package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import POJO.Perfil;

public class EditarPerfil extends AppCompatActivity {

    Perfil perfil;
    ImageButton ib_ok, ib_cancel;
    ImageView iv_profile_pic;
    EditText et_profile_name, et_user_name, et_bio;
    String id_perfil, nombre_perfil, nombre_usuario, biografia;
    String URL, perfilJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        ib_ok = findViewById(R.id.ibOkEdit);
        ib_cancel = findViewById(R.id.ibCancelEdit);
        iv_profile_pic = findViewById(R.id.ivProfilePic);
        et_profile_name = findViewById(R.id.etEditProfileName);
        et_user_name = findViewById(R.id.etEditUserName);
        et_bio = findViewById(R.id.etEditBio);

        perfil = recuperarPreferencias();

        URL = getResources().getString(R.string.url) + "guardarCambiosPerfil.php";

        cargarDatos();
    }

    private void cargarDatos() {
        //Glide.with(this).load(perfil.getFoto()).into(iv_profile_pic);
        et_profile_name.setText(perfil.getNombre());
        et_user_name.setText(perfil.getUsuario());
        et_bio.setText(perfil.getBiografia());
        Toast.makeText(EditarPerfil.this, et_profile_name.getText(), Toast.LENGTH_SHORT).show();
        Toast.makeText(EditarPerfil.this, et_user_name.getText(), Toast.LENGTH_SHORT).show();
        Toast.makeText(EditarPerfil.this, et_bio.getText(), Toast.LENGTH_SHORT).show();
    }

    public void cancelarCambios(View view) {
        finish();
    }

    public void aceptarCambios(View view) {
        id_perfil = String.valueOf(perfil.getId());
        nombre_perfil = et_profile_name.getText().toString();
        nombre_usuario = et_user_name.getText().toString();
        biografia = et_bio.getText().toString();
        guardarCambios(id_perfil, nombre_perfil, nombre_usuario, biografia);
    }

    private void guardarCambios(String id, String nombre_profile, String nombre_user, String biogr) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                perfilJSON = response;
                Perfil perfil = new Gson().fromJson(response, Perfil.class);
                if (perfilJSON.contains("{")) {
                    guardarPreferencias();
                    perfil = recuperarPreferencias();
                    Toast.makeText(EditarPerfil.this, "Cambios guardados", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditarPerfil.this, ProfileActivity.class);
                    intent.putExtra("perfilIntent", perfil);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditarPerfil.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("profile_id", id);
                params.put("profile_name", nombre_profile);
                params.put("user_name", nombre_user);
                params.put("bio", biogr);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void guardarPreferencias(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasLogin",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nombre", nombre_perfil);
        editor.putString("usuario", nombre_usuario);
        editor.putString("biografia", biografia);
        editor.putBoolean("session", true);
        editor.putString("perfilJSON", perfilJSON);
        editor.commit();
    }

    private Perfil recuperarPreferencias() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
        Perfil perfil = new Gson().fromJson(sharedPreferences.getString("perfilJSON", ""), Perfil.class);
        return perfil;
    }
}