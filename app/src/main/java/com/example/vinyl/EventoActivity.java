package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import POJO.Evento;
import POJO.Perfil;
import metodosExternos.IraEvento;

public class EventoActivity extends AppCompatActivity{

    TextView tv_evento_dia, tv_evento_mes, tv_evento_nombre, tv_evento_hora, tv_evento_direccion;
    ImageView iv_evento;
    ImageButton ibt_evento_ir;
    RecyclerView rv_evento_asistentes;

    Evento evento;

    ImageView iv_evento_asistentes1, iv_evento_asistentes2, iv_evento_asistentes3;
    TextView tv_evento_asistentes_lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        tv_evento_dia = findViewById(R.id.tv_evento_dia);
        tv_evento_direccion = findViewById(R.id.tv_evento_direccion);
        tv_evento_hora = findViewById(R.id.tv_evento_hora);
        tv_evento_mes = findViewById(R.id.tv_evento_mes);
        tv_evento_nombre = findViewById(R.id.tv_evento_nombre);
        iv_evento = findViewById(R.id.iv_evento);
        //rv_evento_asistentes = findViewById(R.id.rv_evento_asistentes);
        ibt_evento_ir = findViewById(R.id.ibt_evento_ir);
        iv_evento_asistentes1 = findViewById(R.id.iv_evento_asistentes1);
        iv_evento_asistentes2 = findViewById(R.id.iv_evento_asistentes2);
        iv_evento_asistentes3 = findViewById(R.id.iv_evento_asistentes3);
        tv_evento_asistentes_lista = findViewById(R.id.tv_evento_asistentes_lista);

        evento = getIntent().getParcelableExtra("evento");
        Glide.with(this).load(evento.getImagen()).into(iv_evento);
        tv_evento_direccion.setText(evento.getLugar());
        tv_evento_nombre.setText(evento.getNombre());

        //Rellenamos los campos de la fecha
        ponerFecha();

        //Cargamos los asistentes
        cargarAsistentes("http://95.39.184.89/vinyl/cargarPerfilesdeEvento.php", String.valueOf(evento.getId()));

        //Movidas del mapa
        Fragment fragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();

        //Comprobamos si ya ha seleccionado que va a ir al evento
        IraEvento.checkIr(this, recuperarIdPerfil(), String.valueOf(evento.getId()), ibt_evento_ir);
    }

    private void cargarAsistentes(String URL, String busqueda) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    Gson gson = new Gson();
                    Type perfilListType = new TypeToken<ArrayList<Perfil>>(){}.getType();
                    List<Perfil> perfilArray = gson.fromJson(response, perfilListType);
                    mostrarAsistentes(perfilArray);
                } else {
                    tv_evento_asistentes_lista.setText("Aún no hay asistentes inscritos");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EventoActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void mostrarAsistentes(List<Perfil> perfilArray) {
        int i = perfilArray.size();
        switch (i){
            case 0:
                tv_evento_asistentes_lista.setText("Aún no hay asistentes inscritos");
                break;
            case 1:
                Glide.with(this).load(perfilArray.get(0).getFoto()).into(iv_evento_asistentes1);
                iv_evento_asistentes1.setVisibility(View.VISIBLE);
                tv_evento_asistentes_lista.setText(perfilArray.get(0).getNombre() + " asistirá");
                break;
            case 2:
                Glide.with(this).load(perfilArray.get(0).getFoto()).into(iv_evento_asistentes1);
                Glide.with(this).load(perfilArray.get(1).getFoto()).into(iv_evento_asistentes2);
                iv_evento_asistentes1.setVisibility(View.VISIBLE);
                iv_evento_asistentes2.setVisibility(View.VISIBLE);
                tv_evento_asistentes_lista.setText(perfilArray.get(0).getNombre() +
                        ", " + perfilArray.get(1).getNombre() +
                        " asistirán al evento");
                break;
            case 3:
                Glide.with(this).load(perfilArray.get(0).getFoto()).into(iv_evento_asistentes1);
                Glide.with(this).load(perfilArray.get(1).getFoto()).into(iv_evento_asistentes2);
                Glide.with(this).load(perfilArray.get(2).getFoto()).into(iv_evento_asistentes3);
                iv_evento_asistentes1.setVisibility(View.VISIBLE);
                iv_evento_asistentes2.setVisibility(View.VISIBLE);
                iv_evento_asistentes3.setVisibility(View.VISIBLE);
                tv_evento_asistentes_lista.setText(perfilArray.get(0).getNombre() +
                        ", " + perfilArray.get(1).getNombre() +
                        " y " + perfilArray.get(2).getNombre() +
                        " asistirán al evento");
                break;
            default:
                Glide.with(this).load(perfilArray.get(0).getFoto()).into(iv_evento_asistentes1);
                Glide.with(this).load(perfilArray.get(1).getFoto()).into(iv_evento_asistentes2);
                Glide.with(this).load(perfilArray.get(2).getFoto()).into(iv_evento_asistentes3);
                iv_evento_asistentes1.setVisibility(View.VISIBLE);
                iv_evento_asistentes2.setVisibility(View.VISIBLE);
                iv_evento_asistentes3.setVisibility(View.VISIBLE);
                tv_evento_asistentes_lista.setText(perfilArray.get(0).getNombre() +
                        ", " + perfilArray.get(1).getNombre() +
                        ", " + perfilArray.get(2).getNombre() +
                        " y otras " + (perfilArray.size()-3) + " personas asistirán al evento");
                break;
        }
    }

    public void ir(View view){
        IraEvento.iroNo(this, recuperarIdPerfil(), String.valueOf(evento.getId()), ibt_evento_ir);
    }

    public void iraMaps(View view){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(evento.getLugar()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void iraEntradas(View view){
        Uri webPage = Uri.parse(evento.getEntradas());
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        startActivity(intent);
    }

    private void ponerFecha() {
        String s = evento.getFecha();
        /* El primer corte separa la fecha de la hora; el segundo corte separa: 0-año, 1-mes, 2-dia
         * El tercer corte separa 0-hora, 1-minutos, 2-segundos */
        String[] primerCorte = s.split(" ");
        String[] segundoCorte = primerCorte[0].split("-");
        String[] tercerCorte = primerCorte[1].split(":");

        tv_evento_dia.setText(segundoCorte[2]);
        tv_evento_mes.setText(seleccionarMes(Integer.parseInt(segundoCorte[1])));
        tv_evento_hora.setText(tercerCorte[0] + ":" + tercerCorte[1]);
    }

    private String seleccionarMes(int i){
        String mes = "";
        switch (i){
            case 1:
                mes = "Ene";
                break;
            case 2:
                mes ="Feb";
                break;
            case 3:
                mes = "Mar";
                break;
            case 4:
                mes ="Abr";
                break;
            case 5:
                mes = "May";
                break;
            case 6:
                mes = "Jun";
                break;
            case 7:
                mes = "Jul";
                break;
            case 8:
                mes = "Ago";
                break;
            case 9:
                mes = "Sep";
                break;
            case 10:
                mes = "Oct";
                break;
            case 11:
                mes = "Nov";
                break;
            case 12:
                mes = "Dic";
                break;
        }
        return mes;
    }

    public Evento getEvento() {
        return evento;
    }

    private String recuperarIdPerfil() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
        String perfilJSON = sharedPreferences.getString("perfilJSON", "");
        Perfil perfil = new Gson().fromJson(perfilJSON,Perfil.class);
        String id_perfil = String.valueOf(perfil.getId());
        return id_perfil;
    }

    public void fromEventotoSearch(View view) {
        Intent intent = new Intent(this, Buscador.class);
        startActivity(intent);
        this.finish();
    }

    public void fromEventotoCalendar(View view) {
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
        this.finish();
    }

    public void fromEventotoProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void fromEventotoFeed(View view) {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        this.finish();
    }
}