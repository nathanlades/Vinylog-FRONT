package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import POJO.Perfil;
import POJO.Resena;

public class UsuarioActivity extends AppCompatActivity {

    TextView tv_usuario_nombre, tv_usuario_usuario, tv_usuario_resenas_numero, tv_usuario_biografia;
    ImageView iv_usuario;
    Button bt_usuario_follow, bt_usuario_mensaje;
    RecyclerView rv_usuario_resena;
    Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        tv_usuario_biografia = findViewById(R.id.tv_usuario_biografia);
        tv_usuario_nombre = findViewById(R.id.tv_usuario_nombre);
        tv_usuario_resenas_numero = findViewById(R.id.tv_usuario_resenas_numero);
        tv_usuario_usuario = findViewById(R.id.tv_usuario_usuario);
        iv_usuario = findViewById(R.id.iv_usuario);
        bt_usuario_follow = findViewById(R.id.bt_usuario_follow);
        bt_usuario_mensaje = findViewById(R.id.bt_usuario_mensaje);
        rv_usuario_resena = findViewById(R.id.rv_usuario_resena);
        perfil = getIntent().getParcelableExtra("Usuario");

        tv_usuario_nombre.setText(perfil.getNombre());
        tv_usuario_usuario.setText(perfil.getUsuario());
        tv_usuario_biografia.setText(perfil.getBiografia());
        Glide.with(this).load(perfil.getFoto()).into(iv_usuario);
        tv_usuario_biografia.setText(perfil.getBiografia());

        cargarResenas("http://95.39.184.89/vinyl/cargarResenasUsuario.php", String.valueOf(perfil.getId()));

        estaSiguiendo(recuperarIdPerfil(),perfil.getId(), "http://95.39.184.89/vinyl/sigueUsuario.php");
    }

    private void estaSiguiendo(String id1, int id2, String URL) {
        //id1 e id2 son para la id de la persona logeada y para el perfil consultado respectivamente
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Siguiendo(id1,id2);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UsuarioActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id1",id1);
                params.put("id2",String.valueOf(id2));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void Siguiendo(String id1, int id2) {
        bt_usuario_follow.setBackgroundColor(getResources().getColor(R.color.vinyl_selected_red));
        bt_usuario_follow.setText("Following");
    }

    private void cargarResenas(String URL, String busqueda) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type resenaListType = new TypeToken<ArrayList<Resena>>(){}.getType();
                    List<Resena> resenaArray = gson.fromJson(response, resenaListType);
                    cargarResenas2("http://95.39.184.89/vinyl/CargarResenasUsuaio2.php", busqueda, resenaArray);
                } else {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UsuarioActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id",busqueda);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void cargarResenas2(String URL, String busqueda, List<Resena> resenaArray) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type resenaListType = new TypeToken<ArrayList<Resena>>(){}.getType();
                    List<Resena> resenaArray2 = gson.fromJson(response, resenaListType);
                    resenaArray.addAll(resenaArray2);
                    tv_usuario_resenas_numero.setText(String.valueOf(resenaArray.size()));
                    mostrarResenas(resenaArray);
                } else {
                    tv_usuario_resenas_numero.setText(String.valueOf(resenaArray.size()));
                    mostrarResenas(resenaArray);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UsuarioActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id",busqueda);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void mostrarResenas(List<Resena> resenaArray) {
        UsuarioResenaAdapter usuarioResenaAdapter = new UsuarioResenaAdapter(this,resenaArray,perfil);
        rv_usuario_resena.setAdapter(usuarioResenaAdapter);
        rv_usuario_resena.setLayoutManager(new LinearLayoutManager(this));
    }

    public void seguir(View view){
        String s = bt_usuario_follow.getText().toString();
        if (s.equals("Following")){
            dejarDeSeguir(recuperarIdPerfil(),perfil.getId(),"http://95.39.184.89/vinyl/noSeguirUsuario.php");
        } else {
            empezarASeguir(recuperarIdPerfil(),perfil.getId(),"http://95.39.184.89/vinyl/seguirUsuario.php");
        }
    }

    private void empezarASeguir(String id1, int id2, String URL) {
        //id1 e id2 son para la id de la persona logeada y para el perfil consultado respectivamente
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("true")){
                    Siguiendo(id1,id2);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UsuarioActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id1",id1);
                params.put("id2",String.valueOf(id2));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void dejarDeSeguir(String id1, int id2, String URL) {
        //id1 e id2 son para la id de la persona logeada y para el perfil consultado respectivamente
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("true")){
                    bt_usuario_follow.setBackgroundColor(getResources().getColor(R.color.vinyl_red));
                    bt_usuario_follow.setText("Follow");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UsuarioActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id1",id1);
                params.put("id2",String.valueOf(id2));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private String recuperarIdPerfil() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
        String perfilJSON = sharedPreferences.getString("perfilJSON", "");
        Perfil perfil = new Gson().fromJson(perfilJSON,Perfil.class);
        String id_perfil = String.valueOf(perfil.getId());
        return id_perfil;
    }

    public void fromHeretoSearch(View view) {
        Intent intent = new Intent(this, Buscador.class);
        startActivity(intent);
    }

    public void fromHeretoCalendar(View view) {
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }

    public void fromHeretoProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void fromHeretoFeed(View view) {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
    }
}