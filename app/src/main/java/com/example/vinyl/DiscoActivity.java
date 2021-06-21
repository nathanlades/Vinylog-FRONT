package com.example.vinyl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import POJO.Disco;
import POJO.Perfil;
import POJO.Resena;
import POJO.Tema;

public class DiscoActivity extends AppCompatActivity {

    ImageView iv_disco;
    TextView tv_disco, tv_disco_descripcion_titulo, tv_disco_descripcion, tv_disco_resena_titulo, tv_disco_fecha;
    RecyclerView rv_disco, rv_disco_resena;
    FloatingActionButton fab_disco;
    Disco disco;

    //Variables para crear el diálogo de creacción de reseña
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    EditText et_artista_resena, et_artista_resena_titulo;
    TextView tv_artista_resena_puntuacion;
    Button bt_artista_resena;
    ImageView iv_artista_resena_puntuacion1, iv_artista_resena_puntuacion2, iv_artista_resena_puntuacion3, iv_artista_resena_puntuacion4, iv_artista_resena_puntuacion5, iv_resena_titulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disco);
        iv_disco = findViewById(R.id.iv_disco);
        tv_disco = findViewById(R.id.tv_disco);
        tv_disco_descripcion = findViewById(R.id.tv_disco_descripcion);
        tv_disco_descripcion_titulo = findViewById(R.id.tv_disco_descripcion_titulo);
        tv_disco_resena_titulo = findViewById(R.id.tv_disco_resena_titulo);
        tv_disco_fecha = findViewById(R.id.tv_disco_fecha);
        rv_disco = findViewById(R.id.rv_disco);
        rv_disco_resena = findViewById(R.id.rv_disco_resena);
        fab_disco = findViewById(R.id.fab_disco);
        disco = getIntent().getParcelableExtra("Disco");
        Glide.with(this).load(disco.getPortada()).into(iv_disco);
        tv_disco_descripcion.setText(disco.getTexto());

        tv_disco.setText(disco.getNombre());
        String anoS = String.valueOf(disco.getAno());
        tv_disco_fecha.setText(anoS);

        cargarTemas("http://95.39.184.89/vinyl/cargarTemasdeDisco.php",String.valueOf(disco.getId()));

        cargarResenas("http://95.39.184.89/vinyl/cargarResenasDisco.php",String.valueOf(disco.getId()));
    }

    private void cargarTemas(String URL, String busqueda) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type temaListType = new TypeToken<ArrayList<Tema>>(){}.getType();
                    List<Tema> temaArray = gson.fromJson(response, temaListType);
                    mostrarTemas(temaArray, disco.getPortada());
                } else {
                    //No hay canciones
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DiscoActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void mostrarTemas(List<Tema> temaArray, String portada) {
        DiscoTemasAdapter discoTemasAdapter = new DiscoTemasAdapter(this,temaArray,portada);
        rv_disco.setAdapter(discoTemasAdapter);
        rv_disco.setLayoutManager(new LinearLayoutManager(this));
    }

    private void cargarResenas(String URL, String busqueda){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type resenaListType = new TypeToken<ArrayList<Resena>>(){}.getType();
                    List<Resena> resenaArray = gson.fromJson(response, resenaListType);
                    cargarResenasPerfil(busqueda, resenaArray);
                } else {
                    //No hay reseñas
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DiscoActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void cargarResenasPerfil(String busqueda, List<Resena> resenaArray) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/cargarResenasDisco_Perfil.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type perfilListType = new TypeToken<ArrayList<Perfil>>(){}.getType();
                    List<Perfil> perfilArray = gson.fromJson(response, perfilListType);
                    mostrarResenas(resenaArray, perfilArray, disco.getPortada());
                } else {
                    Toast.makeText(DiscoActivity.this, "No hay reseñas", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DiscoActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void mostrarResenas(List<Resena> resenaArray, List<Perfil> perfilArray, String portada) {
        rv_disco_resena.setVisibility(View.INVISIBLE);
        ArtistaResenaAdapter artistaResenaAdapter = new ArtistaResenaAdapter(this, resenaArray,perfilArray, portada);
        rv_disco_resena.setAdapter(artistaResenaAdapter);
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        rv_disco_resena.setLayoutManager(new LinearLayoutManager(this));
        rv_disco_resena.setVisibility(View.VISIBLE);
    }

    public void crearResenaDisco(View view){
        dialogBuilder = new AlertDialog.Builder(this);
        final View resenaPopupView = getLayoutInflater().inflate(R.layout.resena_popup, null);
        et_artista_resena_titulo = resenaPopupView.findViewById(R.id.et_artista_resena_titulo);
        et_artista_resena = resenaPopupView.findViewById(R.id.et_artista_resena);
        bt_artista_resena = resenaPopupView.findViewById(R.id.bt_artista_resena);
        iv_artista_resena_puntuacion1 = resenaPopupView.findViewById(R.id.iv_artista_resena_puntuacion1);
        iv_artista_resena_puntuacion2 = resenaPopupView.findViewById(R.id.iv_artista_resena_puntuacion2);
        iv_artista_resena_puntuacion3 = resenaPopupView.findViewById(R.id.iv_artista_resena_puntuacion3);
        iv_artista_resena_puntuacion4 = resenaPopupView.findViewById(R.id.iv_artista_resena_puntuacion4);
        iv_artista_resena_puntuacion5 = resenaPopupView.findViewById(R.id.iv_artista_resena_puntuacion5);
        tv_artista_resena_puntuacion = resenaPopupView.findViewById(R.id.tv_artista_resena_puntuacion);
        iv_resena_titulo = resenaPopupView.findViewById(R.id.iv_resena_titulo);

        dialogBuilder.setView(resenaPopupView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

/*        iv_resena_titulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });*/

/*        bt_artista_resena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = et_artista_resena_titulo.getText().toString();
                String texto = et_artista_resena.getText().toString();
                String puntuacion = tv_artista_resena_puntuacion.getText().toString();
                String id_perfil, id_disco;
                id_perfil = recuperarIdPerfil();
                id_disco = String.valueOf(disco.getId());

                if(!titulo.equals("") && !texto.equals("")){
                    guardarResena(titulo,texto,puntuacion,id_perfil,id_disco);
                } else {
                    Toast.makeText(DiscoActivity.this, "Tanto el título como el texto deben estar rellenos.", Toast.LENGTH_SHORT).show();
                }
            }

        });*/

        bt_artista_resena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }

        });

        iv_artista_resena_puntuacion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_grey);
                iv_artista_resena_puntuacion3.setImageResource(R.mipmap.feed_grey);
                iv_artista_resena_puntuacion4.setImageResource(R.mipmap.feed_grey);
                iv_artista_resena_puntuacion5.setImageResource(R.mipmap.feed_grey);
                tv_artista_resena_puntuacion.setText("1");
            }
        });
        iv_artista_resena_puntuacion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion3.setImageResource(R.mipmap.feed_grey);
                iv_artista_resena_puntuacion4.setImageResource(R.mipmap.feed_grey);
                iv_artista_resena_puntuacion5.setImageResource(R.mipmap.feed_grey);
                tv_artista_resena_puntuacion.setText("2");
            }
        });
        iv_artista_resena_puntuacion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion3.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion4.setImageResource(R.mipmap.feed_grey);
                iv_artista_resena_puntuacion5.setImageResource(R.mipmap.feed_grey);
                tv_artista_resena_puntuacion.setText("3");
            }
        });
        iv_artista_resena_puntuacion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion3.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion4.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion5.setImageResource(R.mipmap.feed_grey);
                tv_artista_resena_puntuacion.setText("4");
            }
        });
        iv_artista_resena_puntuacion5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion3.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion4.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion5.setImageResource(R.mipmap.feed_red);
                tv_artista_resena_puntuacion.setText("5");
            }
        });
    }

    private void guardarResena(String titulo, String texto, String puntuacion, String id_perfil, String id_disco) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/guardarResenaDisco.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    DiscoActivity.this.recreate();
                } else {
                    Toast.makeText(DiscoActivity.this, "Error al guardar la reseña 1", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DiscoActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("titulo",titulo);
                params.put("texto", texto);
                params.put("puntuacion", puntuacion);
                params.put("id_perfil", id_perfil);
                params.put("id_disco", id_disco);
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