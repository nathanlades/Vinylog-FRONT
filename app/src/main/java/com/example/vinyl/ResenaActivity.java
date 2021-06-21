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
import android.widget.ImageButton;
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

import POJO.Comentario;
import POJO.Perfil;
import POJO.Resena;
import metodosExternos.Likes;

public class ResenaActivity extends AppCompatActivity {

    ImageView iv_resena, iv_resena_puntuacion1, iv_resena_puntuacion2, iv_resena_puntuacion3, iv_resena_puntuacion4, iv_resena_puntuacion5, iv_resena_autor, iv_resena_like,
            iv_artista_resena_puntuacion1, iv_artista_resena_puntuacion2, iv_artista_resena_puntuacion3, iv_artista_resena_puntuacion4, iv_artista_resena_puntuacion5;
    TextView tv_resena, tv_resena_texto, tv_resena_autor, tv_resena_fecha, tv_resena_comentarios_numero, tv_resena_like_numero, tv_artista_resena_puntuacion;
    Perfil perfilAutor;
    Resena resena;
    RecyclerView rv_resenaComentariosAdapter;
    FloatingActionButton fab_resena, fab_resena_editar;
    ImageButton ibt_resena_like;

    //Definimos las variables para el popup
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    EditText et_resena_comentario, et_artista_resena_titulo, et_artista_resena;
    Button bt_resena_comentario, bt_artista_resena;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resena);
        iv_resena = findViewById(R.id.iv_disco);
        tv_resena = findViewById(R.id.tv_disco);
        tv_resena_texto = findViewById(R.id.tv_resena_texto);
        tv_resena_autor = findViewById(R.id.tv_resena_autor);
        tv_resena_fecha = findViewById(R.id.tv_resena_fecha);
        iv_resena_puntuacion1 = findViewById(R.id.iv_artista_resena_puntuacion1);
        iv_resena_puntuacion2 = findViewById(R.id.iv_artista_resena_puntuacion2);
        iv_resena_puntuacion3 = findViewById(R.id.iv_artista_resena_puntuacion3);
        iv_resena_puntuacion4 = findViewById(R.id.iv_resena_puntuacion4);
        iv_resena_puntuacion5 = findViewById(R.id.iv_artista_resena_puntuacion5);
        tv_resena_comentarios_numero = findViewById(R.id.tv_resena_comentarios_numero);
        iv_resena_autor = findViewById(R.id.iv_resena_autor);
        rv_resenaComentariosAdapter = findViewById(R.id.rv_resena_comentarios);
        fab_resena = findViewById(R.id.fab_resena);
        fab_resena_editar = findViewById(R.id.fab_resena_editar);
        ibt_resena_like = findViewById(R.id.ibt_resena_like);
        tv_resena_like_numero = findViewById(R.id.tv_resena_like_numero);

        perfilAutor = getIntent().getParcelableExtra("perfilAutor");
        resena = getIntent().getParcelableExtra("resena");
        String imagen = getIntent().getStringExtra("imagen");
        Glide.with(this).load(imagen).into(iv_resena);
        tv_resena.setText(resena.getTitulo());
        tv_resena_texto.setText(resena.getTexto());
        tv_resena_autor.setText(perfilAutor.getNombre());
        String[] fecha = resena.getFecha().split(" ");
        tv_resena_fecha.setText(fecha[0]);
        setPuntuacion(resena);
        Glide.with(this).load(perfilAutor.getFoto()).into(iv_resena_autor);

        //Cargamos la consulta con los comentarios y sus autores para esta reseña
        cargarComentarios("http://95.39.184.89/vinyl/cargarComentarios.php", String.valueOf(resena.getId()));

        //Comprobamos si se le ha dado likes, y el número total de ellos
        Likes.checkLike(this, recuperarIdPerfil(), String.valueOf(resena.getId()), ibt_resena_like);
        Likes.countLike(this,String.valueOf(resena.getId()),tv_resena_like_numero);

        //Hacemos que el botón de editar reseña solo aparezca si la reseña es de tu propio perfil
        if (perfilAutor.getId() == Integer.parseInt(recuperarIdPerfil())){
            fab_resena_editar.setVisibility(View.VISIBLE);
        }
    }

    private void cargarComentarios(String URL, String busqueda) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type ComentarioListType = new TypeToken<ArrayList<Comentario>>(){}.getType();
                    List<Comentario> comentarioArray = gson.fromJson(response, ComentarioListType);
                    String s = String.valueOf(comentarioArray.size());
                    tv_resena_comentarios_numero.setText(s);
                    cargarComentariosPerfil(comentarioArray,busqueda);
                } else {}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResenaActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void cargarComentariosPerfil(List<Comentario> comentarioArray, String busqueda) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/cargarComentariosPerfil.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type PerfilListType = new TypeToken<ArrayList<Perfil>>(){}.getType();
                    List<Perfil> perfilArray = gson.fromJson(response, PerfilListType);
                    mostrarComentarios(comentarioArray,perfilArray);
                } else {}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResenaActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void mostrarComentarios(List<Comentario> comentarioArray, List<Perfil> perfilArray) {
        ComentarioAdapter comentarioAdapter = new ComentarioAdapter(this,comentarioArray, perfilArray);
        rv_resenaComentariosAdapter.setAdapter(comentarioAdapter);
        rv_resenaComentariosAdapter.setLayoutManager(new LinearLayoutManager(this));
        rv_resenaComentariosAdapter.setVisibility(View.VISIBLE);
    }

    private void setPuntuacion(Resena resena) {
        iv_resena_puntuacion1.setImageResource(R.mipmap.feed_grey);
        iv_resena_puntuacion2.setImageResource(R.mipmap.feed_grey);
        iv_resena_puntuacion3.setImageResource(R.mipmap.feed_grey);
        iv_resena_puntuacion4.setImageResource(R.mipmap.feed_grey);
        iv_resena_puntuacion5.setImageResource(R.mipmap.feed_grey);

        switch (resena.getPuntuacion()){
            case 0:
                break;
            case 1:
                iv_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                break;
            case 2:
                iv_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                break;
            case 3:
                iv_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                iv_resena_puntuacion3.setImageResource(R.mipmap.feed_red);
                break;
            case 4:
                iv_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                iv_resena_puntuacion3.setImageResource(R.mipmap.feed_red);
                iv_resena_puntuacion4.setImageResource(R.mipmap.feed_red);
                break;
            case 5:
                iv_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                iv_resena_puntuacion3.setImageResource(R.mipmap.feed_red);
                iv_resena_puntuacion4.setImageResource(R.mipmap.feed_red);
                iv_resena_puntuacion5.setImageResource(R.mipmap.feed_red);
                break;
        }
    }

    public void nuevaResena(View view){
        dialogBuilder = new AlertDialog.Builder(this);
        final View comentarioPopupView = getLayoutInflater().inflate(R.layout.comentario_popup, null);
        et_resena_comentario = comentarioPopupView.findViewById(R.id.et_resena);
        bt_resena_comentario = comentarioPopupView.findViewById(R.id.bt_resena_comentario);

        dialogBuilder.setView(comentarioPopupView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

        bt_resena_comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_resena = String.valueOf(resena.getId());
                String id_perfil = recuperarIdPerfil();
                String comentario = et_resena_comentario.getText().toString();

                if (!comentario.equals("")){
                    guardarComentario(id_resena, id_perfil, comentario);
                } else {
                    Toast.makeText(ResenaActivity.this, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void editarResena(View view) {
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

        et_artista_resena_titulo.setText(resena.getTitulo());
        et_artista_resena.setText(resena.getTexto());
        switch (resena.getPuntuacion()){
            case 0:
                break;
            case 1:
                iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                break;
            case 2:
                iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                break;
            case 3:
                iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion3.setImageResource(R.mipmap.feed_red);
                break;
            case 4:
                iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion3.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion4.setImageResource(R.mipmap.feed_red);
                break;
            case 5:
                iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion3.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion4.setImageResource(R.mipmap.feed_red);
                iv_artista_resena_puntuacion5.setImageResource(R.mipmap.feed_red);
                break;
        }

        dialogBuilder.setView(resenaPopupView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

        bt_artista_resena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_resena = String.valueOf(resena.getId());
                String puntuacion = String.valueOf(resena.getPuntuacion());
                String texto = et_artista_resena.getText().toString();
                String titulo = et_artista_resena_titulo.getText().toString();
                
                if (!tv_artista_resena_puntuacion.getText().toString().equals("")){
                    puntuacion = tv_artista_resena_puntuacion.getText().toString();
                }

                if (!texto.equals("") && !titulo.equals("")){
                    actualizarResena(id_resena, puntuacion, texto, titulo);
                } else {
                    Toast.makeText(ResenaActivity.this, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show();
                }

                alertDialog.dismiss();
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

    private void actualizarResena(String id_resena, String puntuacion, String texto, String titulo) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/actualizarResena.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("true")){
/*                    resena.setPuntuacion(Integer.parseInt(puntuacion));
                    resena.setTitulo(titulo);
                    resena.setTexto(texto);
                    ResenaActivity.this.recreate();*/
                    resena.setPuntuacion(Integer.parseInt(puntuacion));
                    resena.setTitulo(titulo);
                    resena.setTexto(texto);
                    setPuntuacion(resena);
                    tv_resena.setText(resena.getTitulo());
                    tv_resena_texto.setText(resena.getTexto());
                } else {
                    Toast.makeText(ResenaActivity.this, "Error en el volley", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResenaActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id",id_resena);
                params.put("puntuacion", puntuacion);
                params.put("texto", texto);
                params.put("titulo", titulo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void guardarComentario(String id_resena, String id_perfil, String comentario) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/guardarComentario.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    ResenaActivity.this.recreate();
                } else {
                    Toast.makeText(ResenaActivity.this, "Error al guardar el comentario", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResenaActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id_resena",id_resena);
                params.put("id_perfil", id_perfil);
                params.put("comentario", comentario);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void like(View view){
            Likes.darLike(this,recuperarIdPerfil(),String.valueOf(resena.getId()),ibt_resena_like,tv_resena_like_numero);
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