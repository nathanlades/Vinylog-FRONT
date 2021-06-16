package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

import POJO.Evento;
import POJO.Perfil;
import POJO.Resena;

public class Calendar extends AppCompatActivity {

    Perfil perfil;
    RecyclerView rv_calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        rv_calendario = findViewById(R.id.rv_calendario);

        perfil  = recuperarPreferencias();

        cargarEventos("http://95.39.184.89/vinyl/cargarEventosUsuario.php", String.valueOf(perfil.getId()));
    }

    private void cargarEventos(String URL, String busqueda) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type eventoListType = new TypeToken<ArrayList<Evento>>(){}.getType();
                    List<Evento> eventoArray = gson.fromJson(response, eventoListType);
                    mostrarEventos(eventoArray);
                } else {
                    Toast.makeText(Calendar.this, "No hay eventos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Calendar.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void mostrarEventos(List<Evento> eventoArray) {
        CalendarAdapter calendarAdapter = new CalendarAdapter(this, eventoArray);
        rv_calendario.setAdapter(calendarAdapter);
        rv_calendario.setLayoutManager(new LinearLayoutManager(this));
    }

    public void fromCalendartoSearch(View view){
        Intent intent = new Intent(Calendar.this,Buscador.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromCalendartoFeed(View view){
        Intent intent = new Intent(Calendar.this,FeedActivity.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromCalendartoProfile(View view){
        Intent intent = new Intent(Calendar.this, ProfileActivity.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    private Perfil recuperarPreferencias(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
        Perfil perfil = new Gson().fromJson(sharedPreferences.getString("perfilJSON" , ""), Perfil.class);
        return perfil;
    }
}