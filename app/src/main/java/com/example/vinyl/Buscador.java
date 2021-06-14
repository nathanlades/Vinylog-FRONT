package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import POJO.Artista;
import POJO.Disco;
import POJO.Perfil;
import POJO.Tema;

public class Buscador extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText et_buscador;
    ImageView iv_buscador;
    Button bt_destacados, bt_usuarios, bt_temas, bt_discos, bt_artistas;
    String URL = "";
    String nombre = "";
    int op = 4;
    Perfil perfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        recyclerView = findViewById(R.id.rv);
        et_buscador = findViewById(R.id.et_buscador);
        iv_buscador = findViewById(R.id.iv_perfil_perfil);
        bt_destacados = findViewById(R.id.bt_destacados);
        //Aquí hacemos que el botón de destacados no se muestre
        bt_destacados.setVisibility(View.GONE);
        bt_usuarios = findViewById(R.id.bt_usuarios);
        bt_temas = findViewById(R.id.bt_temas);
        bt_discos = findViewById(R.id.bt_discos);
        bt_artistas = findViewById(R.id.bt_artistas);

        URL = getResources().getString(R.string.url);

        perfil  = getIntent().getParcelableExtra("perfilIntent");
    }

    public void buscar(View view){
        nombre = String.valueOf(et_buscador.getText());
        
        switch (op){
            case 1:
                //cargarDestacados()
                break;
            case 2:
                cargarPerfil(URL = getResources().getString(R.string.url)+"cargarPerfil.php", nombre);
                break;
            case 3:
                cargarTemas(URL = getResources().getString(R.string.url)+"cargarTemas.php",nombre);
                break;
            case 4:
                cargarDiscos(URL = getResources().getString(R.string.url)+"cargarDiscos.php", nombre);
                break;
            case 5:
                cargarArtista(URL = getResources().getString(R.string.url)+"cargarArtistas.php",nombre);
                break;
        }
    }

    public void tipoBusqueda(View view){
        Button button = findViewById(view.getId());
        String text = button.getText().toString();

        if (text.equals(getResources().getString(R.string.bt_destacados))){
            op = 1;
        } else if (text.equals(getResources().getString(R.string.bt_usuarios))){
            op = 2;
        } else if (text.equals(getResources().getString(R.string.bt_temas))){
            op = 3;
        } else if (text.equals(getResources().getString(R.string.bt_discos))){
            op = 4;
        } else if (text.equals(getResources().getString(R.string.bt_artistas))){
            op = 5;
        }
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
                    Toast.makeText(Buscador.this, "No hay nada en la base de datos con ese nombre", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Buscador.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("nombre",nombre);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void cargarPerfil(String URL, String busqueda) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type perfilListType = new TypeToken<ArrayList<Perfil>>(){}.getType();
                    List<Perfil> perfilArray = gson.fromJson(response, perfilListType);
                    mostrarPerfil(perfilArray);
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    Toast.makeText(Buscador.this, "No hay nada en la base de datos con ese nombre", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Buscador.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("nombre",nombre);
                params.put("usuario",nombre);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void cargarArtista(String URL, String busqueda) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type artistaListType = new TypeToken<ArrayList<Artista>>(){}.getType();
                    List<Artista> artistaArray = gson.fromJson(response, artistaListType);
                    mostrarArtista(artistaArray);
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    Toast.makeText(Buscador.this, "No hay nada en la base de datos con ese nombre", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Buscador.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("nombre",nombre);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void cargarTemas(String URL, String busqueda) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type temaListType = new TypeToken<ArrayList<Tema>>(){}.getType();
                    List<Tema> temaArray = gson.fromJson(response, temaListType);
                    mostrarTema(temaArray);
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    Toast.makeText(Buscador.this, "No hay nada en la base de datos con ese nombre", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Buscador.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("nombre",nombre);
                params.put("usuario",nombre);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void mostrarDiscos(List<Disco> discoArray) {

        int i = discoArray.size();
        String[] nombre = new String[i], tipo = new String[i], portada = new String[i];

        for (Disco disco : discoArray){
            nombre[discoArray.indexOf(disco)] = disco.getNombre();
            tipo[discoArray.indexOf(disco)] = "Álbum by " + disco.getArtista();
            portada[discoArray.indexOf(disco)] = disco.getPortada();
        }

        recyclerView.setVisibility(View.INVISIBLE);
        MyAdapter myAdapter = new MyAdapter(this, nombre,tipo,portada);
        myAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Buscador.this, DiscoActivity.class);
                intent.putExtra("Disco",discoArray.get(recyclerView.getChildAdapterPosition(v)));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);

    }

    private void mostrarPerfil(List<Perfil> perfilArray) {

        int i = perfilArray.size();
        String[] nombre = new String[i], tipo = new String[i], portada = new String[i];

        for (Perfil perfil : perfilArray){
            nombre[perfilArray.indexOf(perfil)] = perfil.getUsuario();
            tipo[perfilArray.indexOf(perfil)] = "Usuario";
            portada[perfilArray.indexOf(perfil)] = perfil.getFoto();
        }

        recyclerView.setVisibility(View.INVISIBLE);
        MyAdapter myAdapter = new MyAdapter(this, nombre,tipo,portada);
        myAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Buscador.this, UsuarioActivity.class);
                intent.putExtra("Usuario", perfilArray.get(recyclerView.getChildAdapterPosition(v)));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);

    }

    private void mostrarArtista(List<Artista> artistaArray) {

        int i = artistaArray.size();
        String[] nombre = new String[i], tipo = new String[i], portada = new String[i];

        for (Artista artista : artistaArray){
            nombre[artistaArray.indexOf(artista)] = artista.getNombre();
            tipo[artistaArray.indexOf(artista)] = "Artista";
            portada[artistaArray.indexOf(artista)] = artista.getFoto();
        }

        recyclerView.setVisibility(View.INVISIBLE);
        MyAdapter myAdapter = new MyAdapter(this, nombre,tipo,portada);
        myAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Buscador.this, ArtistaActivity.class);
                intent.putExtra("Artista",artistaArray.get(recyclerView.getChildAdapterPosition(v)));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);

    }

    private void mostrarTema(List<Tema> temaArray) {

        int i = temaArray.size();
        String[] nombre = new String[i], tipo = new String[i], portada = new String[i];

        for (Tema tema : temaArray){
            nombre[temaArray.indexOf(tema)] = tema.getNombre();
            tipo[temaArray.indexOf(tema)] = "Tema";
            portada[temaArray.indexOf(tema)] = tema.getFoto();
        }

        recyclerView.setVisibility(View.INVISIBLE);
        MyAdapter myAdapter = new MyAdapter(this, nombre,tipo,portada);
        myAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDiscodelTema(temaArray.get(recyclerView.getChildAdapterPosition(v)));
            }

            private void cargarDiscodelTema(Tema tema) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/cargarDiscodeTema.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("{")){
                            Disco disco = new Gson().fromJson(response, Disco.class);
                            Intent intent = new Intent(Buscador.this, DiscoActivity.class);
                            intent.putExtra("Disco", disco);
                            startActivity(intent);
                        } else {
                            recyclerView.setVisibility(View.INVISIBLE);
                            Toast.makeText(Buscador.this, "No hay nada en la base de datos con ese nombre", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Buscador.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("id",String.valueOf(tema.getId()));
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(Buscador.this);
                requestQueue.add(stringRequest);
            }
        });
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);

    }

    public void fromSearchtoFeed(View view){
        Intent intent = new Intent(Buscador.this, FeedActivity.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }
    public void fromSearchtoCalendar(View view){
        Intent intent = new Intent(Buscador.this, Calendar.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }
    public void fromSearchtoProfile(View view){
        Intent intent = new Intent(Buscador.this, Profile.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }
}

//Glide.with(this).load("http://192.168.1.93/images/IronMaiden_NumberOfBeast.jpg").into(iv_portada);