package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import POJO.Disco;
import POJO.Perfil;
import POJO.Resena;

public class FeedActivity extends AppCompatActivity {

    RecyclerView rv;
    String URL = "";
    Perfil perfil;
    String userLoggedIn = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        rv = findViewById(R.id.rvFeed);

        URL = getResources().getString(R.string.url) + "cargarFeed.php";

        perfil = getIntent().getParcelableExtra("perfilIntent");

        userLoggedIn = perfil.getUsuario();

        cargarFeed(URL, userLoggedIn);
    }

    private void cargarFeed(String URL, String userLoggedIn) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("{")) {
                    Gson gson = new Gson();
                    Type resenaListType = new TypeToken<ArrayList<Resena>>() {
                    }.getType();
                    List<Resena> resenaArray = gson.fromJson(response, resenaListType);
                    mostrarFeed(resenaArray);
                } else {
                    rv.setVisibility(View.INVISIBLE);
                    Toast.makeText(FeedActivity.this, "No se encuentran publicaciones", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FeedActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void mostrarFeed(List<Resena> resenaArray) {
        int i = resenaArray.size();
        String[] profilePic = new String[i], user = new String[i], cover = new String[i],
                title = new String[i], text = new String[i];

        for (Resena resena : resenaArray) {
            profilePic[resenaArray.indexOf(resena)] = resena.getImagen();
            user[resenaArray.indexOf(resena)] = resena.getUsuario();
            cover[resenaArray.indexOf(resena)] = resena.getImagen();
            title[resenaArray.indexOf(resena)] = resena.getTitulo();
            text[resenaArray.indexOf(resena)] = resena.getTexto();
        }

        rv.setVisibility(View.INVISIBLE);
        PublicacionAdapter feed = new PublicacionAdapter(this, profilePic, user, cover, title, text);
        rv.setAdapter(feed);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setVisibility(View.VISIBLE);
    }

    public void fromFeedtoSearch(View view) {
        Intent intent = new Intent(FeedActivity.this, Buscador.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromFeedtoCalendar(View view) {
        Intent intent = new Intent(FeedActivity.this, Calendar.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromFeedtoProfile(View view) {
        Intent intent = new Intent(FeedActivity.this, Profile.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }
}