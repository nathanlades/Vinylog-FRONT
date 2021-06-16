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

import com.borjabravo.readmoretextview.ReadMoreTextView;
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
import metodosExternos.EqualSpacingItemDecoration;

public class ArtistaActivity extends AppCompatActivity {

    ImageView iv_artista;
    TextView tv_artista;
    POJO.Artista artista;
    RecyclerView recyclerView, recyclerViewResena;
    ReadMoreTextView tv_artista_bio;
    FloatingActionButton fab_artista;
    Button bt_artista_follow;

    boolean following = false;
    String data1, data2, data3;

    //Variables para crear el diálogo de creacción de reseña
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    EditText et_artista_resena, et_artista_resena_titulo;
    TextView tv_artista_resena_puntuacion;
    Button bt_artista_resena;
    ImageView iv_artista_resena_puntuacion1, iv_artista_resena_puntuacion2, iv_artista_resena_puntuacion3, iv_artista_resena_puntuacion4, iv_artista_resena_puntuacion5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artista);

        //Definimos las vistas
        tv_artista = findViewById(R.id.tv_disco);
        iv_artista = findViewById(R.id.iv_disco);
        recyclerView = findViewById(R.id.rv_disco);
        recyclerViewResena = findViewById(R.id.rv_disco_resena);
        tv_artista_bio = findViewById(R.id.tv_disco_descripcion);
        fab_artista = findViewById(R.id.fab_disco);
        bt_artista_follow = findViewById(R.id.bt_artista_follow);

        //Cargamos los datos que pasan desde el buscador
        artista = getIntent().getParcelableExtra("Artista");
        rellenarBio(tv_artista_bio, artista);
        tv_artista.setText(artista.getNombre());
        Glide.with(this).load(artista.getFoto()).into(iv_artista);

        //Comprobamos si le sigue
        isFollowing("http://95.39.184.89/vinyl/sigueArtista.php",String.valueOf(artista.getId()),"{");

        //Cargamos la discografía en un RV con consulta a la DB
        cargarDiscos("http://95.39.184.89/vinyl/cargarDiscosdeArtista.php",String.valueOf(artista.getId()));

        //Cargamos las reseñas en un RV con consulta a la DB
        cargarResenas("http://95.39.184.89/vinyl/cargarResenaArtista.php",String.valueOf(artista.getId()));

    }

    private void isFollowing(String URL, String busqueda, String checkParaRespuesta) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains(checkParaRespuesta)){
                    follows();
                } else {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ArtistaActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id_perfil",recuperarIdPerfil());
                params.put("id_artista", String.valueOf(artista.getId()));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void follows() {
        following = true;
        bt_artista_follow.setText("Following");
        bt_artista_follow.setBackgroundColor(getResources().getColor(R.color.vinyl_selected_red));
    }

    private void cargarResenas(String URL, String busqueda) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type resenaListType = new TypeToken<ArrayList<Resena>>(){}.getType();
                    List<Resena> resenaArray = gson.fromJson(response, resenaListType);
                    cargarResenasPerfil(busqueda, resenaArray);
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    Toast.makeText(ArtistaActivity.this, "No hay resenas", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ArtistaActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/cargarResenaArtista_Perfil.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type perfilListType = new TypeToken<ArrayList<Perfil>>(){}.getType();
                    List<Perfil> perfilArray = gson.fromJson(response, perfilListType);
                    mostrarResenas(resenaArray, perfilArray, artista.getFoto());
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    Toast.makeText(ArtistaActivity.this, "No hay resenas", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ArtistaActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void rellenarBio(ReadMoreTextView tv_artista_bio, POJO.Artista artista) {
        tv_artista_bio.setTrimCollapsedText("  Seguir leyendo");
        tv_artista_bio.setTrimExpandedText(" Leer menos");
        tv_artista_bio.setText(artista.getBio());
    }

    private void cargarDiscos(String URL, String busqueda) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type discoListType = new TypeToken<ArrayList<Disco>>(){}.getType();
                    List<Disco> discoArray = gson.fromJson(response, discoListType);
                    mostrarDiscos(discoArray);
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    Toast.makeText(ArtistaActivity.this, "No hay discos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ArtistaActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void mostrarDiscos(List<POJO.Disco> discoArray) {

        List<POJO.Disco> discos = discoArray;
        int i = discoArray.size();
        String[] nombre = new String[i], tipo = new String[i], portada = new String[i];

        for (Disco disco : discoArray){
            nombre[discoArray.indexOf(disco)] = disco.getNombre();
            tipo[discoArray.indexOf(disco)] = "Álbum by " + disco.getArtista();
            portada[discoArray.indexOf(disco)] = disco.getPortada();
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setVisibility(View.INVISIBLE);
        //MyAdapter myAdapter = new MyAdapter(this, nombre,tipo,portada);
        ArtistaAdapter artistaAdapter = new ArtistaAdapter(this, discoArray);
        artistaAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtistaActivity.this,DiscoActivity.class);
                intent.putExtra("Disco", discoArray.get(recyclerView.getChildAdapterPosition(v)));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(artistaAdapter);
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(30));
        recyclerView.setVisibility(View.VISIBLE);

    }

    private void mostrarResenas(List<Resena> resenaArray, List<Perfil> perfilArray, String imagen) {

        recyclerViewResena.setVisibility(View.INVISIBLE);
        ArtistaResenaAdapter artistaResenaAdapter = new ArtistaResenaAdapter(this, resenaArray,perfilArray, imagen);
        recyclerViewResena.setAdapter(artistaResenaAdapter);
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewResena.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewResena.setVisibility(View.VISIBLE);
    }

    public void crearResena(View view){
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

        dialogBuilder.setView(resenaPopupView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

        bt_artista_resena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = et_artista_resena_titulo.getText().toString();
                String texto = et_artista_resena.getText().toString();
                String puntuacion = tv_artista_resena_puntuacion.getText().toString();
                String id_perfil, id_artista;
                id_perfil = recuperarIdPerfil();
                id_artista = String.valueOf(artista.getId());

                if(!titulo.equals("") && !texto.equals("")){
                    guardarResena(titulo,texto,puntuacion,id_perfil,id_artista);
                } else {
                    Toast.makeText(ArtistaActivity.this, "Tanto el título como el texto deben estar rellenos.", Toast.LENGTH_SHORT).show();
                }
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

    private void guardarResena(String titulo, String texto, String puntuacion, String id_perfil, String id_artista) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/guardarResena.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    ArtistaActivity.this.recreate();
                } else {
                    Toast.makeText(ArtistaActivity.this, "Error al guardar la reseña 1", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ArtistaActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("titulo",titulo);
                params.put("texto", texto);
                params.put("puntuacion", puntuacion);
                params.put("id_perfil", id_perfil);
                params.put("id_artista", id_artista);
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

    public void follow(View view){
        if (!following){
            isFollowing("http://95.39.184.89/vinyl/seguirArtista.php",String.valueOf(artista.getId()),"true");
        } else {
            isFollowing("http://95.39.184.89/vinyl/noSeguirArtista.php",String.valueOf(artista.getId()),"false");
            following = false;
            bt_artista_follow.setText("Follow");
            bt_artista_follow.setBackgroundColor(getResources().getColor(R.color.vinyl_red));
        }
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