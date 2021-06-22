package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import POJO.Perfil;
import POJO.Resena;

public class ProfileActivity extends AppCompatActivity {

    RecyclerView rv_grid;
    Perfil perfil;
    String URL = "", URL2 = "";
    ImageButton ib_edit_profile, ib_log_out;
    ImageView iv_profile_pic;
    TextView tv_num_reviews, tv_num_followers, tv_num_followed, tv_name, tv_bio, tv_no_resenas;
    Button btn_song, btn_album, btn_artist;
    int option = 1;
    String userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ib_edit_profile = findViewById(R.id.ibEditProfile);
        ib_log_out = findViewById(R.id.ibLogOut);
        iv_profile_pic = findViewById(R.id.ivProfilePic);
        tv_num_reviews = findViewById(R.id.tvNumReviews);
        tv_num_followers = findViewById(R.id.tvNumFollowers);
        tv_num_followed = findViewById(R.id.tvNumFollowed);
        tv_name = findViewById(R.id.tvNameProfile);
        tv_bio = findViewById(R.id.tvBioProfile);
        btn_song = findViewById(R.id.btnSongProfile);
        btn_album = findViewById(R.id.btnAlbumProfile);
        btn_artist = findViewById(R.id.btnArtistProfile);
        tv_no_resenas = findViewById(R.id.tvNoResenas);
        rv_grid = findViewById(R.id.rvGrid);

        perfil = recuperarPreferencias();

        URL = getResources().getString(R.string.url) + "cargarGridDiscos.php";
        URL2 = getResources().getString(R.string.url) + "cargarNumsPerfil.php";
        userLoggedIn = perfil.getUsuario();

        // Con esta línea supuestamente se carga la ruta de la imagen del servidor y se muestra en
        // el ImageView, pero no soy capaz de que funcione.
        //Picasso.get().load(perfil.getFoto()).into(iv_profile_pic);

        mostrarPerfil();

        cargarGrid(URL, userLoggedIn);
    }

    public void filtrar() {
        switch (option) {
            case 0:
                cargarGrid(URL = getResources().getString(R.string.url) + "cargarGridTemas.php", userLoggedIn);
                break;
            case 1:
                cargarGrid(URL = getResources().getString(R.string.url) + "cargarGridDiscos.php", userLoggedIn);
                break;
            case 2:
                cargarGrid(URL = getResources().getString(R.string.url) + "cargarGridArtistas.php", userLoggedIn);
                break;
        }
    }

    public void filterSelected(View view) {
        Button btn = findViewById(view.getId());
        String text = btn.getText().toString();
        clearButtons();
        if (text.equals(getResources().getString(R.string.btn_song))) {
            btn_song.setBackgroundColor(getResources().getColor(R.color.vinyl_selected_red));
            option = 0;
        } else if (text.equals(getResources().getString(R.string.btn_album))) {
            btn_album.setBackgroundColor(getResources().getColor(R.color.vinyl_selected_red));
            option = 1;
        } else if (text.equals(getResources().getString(R.string.btn_artist))) {
            btn_artist.setBackgroundColor(getResources().getColor(R.color.vinyl_selected_red));
            option = 2;
        }
        filtrar();
    }

    private void clearButtons() {
        btn_song.setBackgroundColor(getResources().getColor(R.color.vinyl_red));
        btn_album.setBackgroundColor(getResources().getColor(R.color.vinyl_red));
        btn_artist.setBackgroundColor(getResources().getColor(R.color.vinyl_red));
    }

    private void mostrarPerfil() {
        //Glide.with(this).load(perfil.getFoto()).into(iv_profile_pic);
        //tv_num_reviews = findViewById(R.id.tvNumReviews);
        //tv_num_followers = findViewById(R.id.tvNumFollowers);
        //tv_num_followed = findViewById(R.id.tvNumFollowed);
        tv_name.setText(perfil.getNombre());
        tv_bio.setText(perfil.getBiografia());
    }

    private void cargarGrid(String URL, String userLoggedIn) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("{")) {
                    Gson gson = new Gson();
                    Type resenaListType = new TypeToken<ArrayList<Resena>>() {
                    }.getType();
                    List<Resena> resenaArray = gson.fromJson(response, resenaListType);
                    tv_no_resenas.setVisibility(View.INVISIBLE);
                    mostrarGrid(resenaArray);
                } else {
                    rv_grid.setVisibility(View.INVISIBLE);
                    tv_no_resenas.setVisibility(View.VISIBLE);
                    Toast.makeText(ProfileActivity.this, "No se encuentran publicaciones", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                rv_grid.setVisibility(View.INVISIBLE);
                tv_no_resenas.setVisibility(View.VISIBLE);
                Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usuario", userLoggedIn);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void mostrarGrid(List<Resena> resenaArray) {
        int i = resenaArray.size();
        String[] cover = new String[i];

        for (Resena resena : resenaArray) {
            cover[resenaArray.indexOf(resena)] = resena.getImagen();
        }

        rv_grid.setVisibility(View.INVISIBLE);
        GridAdapter grid = new GridAdapter(this, cover);
        rv_grid.setAdapter(grid);
        rv_grid.setLayoutManager(new GridLayoutManager(this, 3));
        rv_grid.setVisibility(View.VISIBLE);
    }

    /*
     * @param URL2
     * @param userLoggedIn private void cargarNumeros(String URL2, String userLoggedIn) {
     *                     StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
     * @Override public void onResponse(String response) {
     * if (response.contains("{")) {
     * Gson gson = new Gson();
     * Type perfilListType = new TypeToken<ArrayList<Perfil>>() {
     * }.getType();
     * List<Perfil> perfilArray = gson.fromJson(response, perfilListType);
     * mostrarNumeros(perfilArray);
     * } else {
     * Toast.makeText(ProfileActivity.this, "No se pueden cargar los datos", Toast.LENGTH_SHORT).show();
     * }
     * }
     * }, new Response.ErrorListener() {
     * @Override public void onErrorResponse(VolleyError error) {
     * Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
     * }
     * }) {
     * @Override protected Map<String, String> getParams() throws AuthFailureError {
     * Map<String, String> params = new HashMap<String, String>();
     * params.put("usuario", userLoggedIn);
     * return params;
     * }
     * };
     * RequestQueue requestQueue = Volley.newRequestQueue(this);
     * requestQueue.add(stringRequest);
     * }
     */

    public void cerrarSesion(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        Toast.makeText(ProfileActivity.this, "Has cerrado sesión", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void editarPerfil(View view) {
        Intent intent = new Intent(ProfileActivity.this, EditarPerfil.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromProfiletoSearch(View view) {
        Intent intent = new Intent(ProfileActivity.this, Buscador.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromProfiletoFeed(View view) {
        Intent intent = new Intent(ProfileActivity.this, FeedActivity.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromProfiletoCalendar(View view) {
        Intent intent = new Intent(ProfileActivity.this, Calendar.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    private Perfil recuperarPreferencias() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
        Perfil perfil = new Gson().fromJson(sharedPreferences.getString("perfilJSON", ""), Perfil.class);
        return perfil;
    }
}