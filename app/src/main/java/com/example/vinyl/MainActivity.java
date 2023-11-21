package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import POJO.Perfil;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario, etPass;
    Button btLogin;

    //la string del usuario también puede contener el email,
    //ambos valen indistintamente para el login
    String usuario, pass, perfilJSON = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.println(Log.INFO, "a", "Se abre la página de login");
        etUsuario = findViewById(R.id.et_user);
        etPass = findViewById(R.id.et_pass);
        btLogin = findViewById(R.id.bt_login);

        recuperarPreferencias();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            etUsuario.setText(extras.getString("usuarioIntent"));
        }
    }

    public void registro(View view) {
        Intent i = new Intent(this, SigninActivity.class);
        startActivity(i);
    }

    public void login(View view) {
        usuario = etUsuario.getText().toString();
        pass = etPass.getText().toString();
        Log.println(Log.INFO, "a", "Parámetros del login: " + usuario + ", " + pass);
        validarUsuario(getResources().getString(R.string.url) + "login.php");
    }

    private void validarUsuario(String URL) {
        Log.println(Log.INFO, "a", perfilJSON);
        Log.println(Log.INFO, "a", URL);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Por alguna razón, no hace bien el bucle si comparo directamente el "response" en vez de
                //hacer primero una String, por tanto la String perfilJSON solo vale para
                //que entre bien en el bucle.
                Log.println(Log.INFO, "a", "Los datos se mandan bien");
                perfilJSON = response;
                Perfil perfil = new Gson().fromJson(response, Perfil.class); //Con esta línea convierto la String JSON en un objeto de la clase Perfil
                if (perfilJSON.contains("{")) {
                    //Aquí lo que hacer si el login es correcto
                    guardarPreferencias();
                    Toast.makeText(MainActivity.this, "Bienvenido, " + perfil.getNombre(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, FeedActivity.class);
                    intent.putExtra("perfilIntent", perfil);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.INFO, "a", "Algo no va bien en el StringRequest");
                        System.out.println("Volley Error --- " + error);
                        //Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_LONG);
                    }
                }
        ) {

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
              /*parametros.put("usuario", etUsuario.getText().toString());
                parametros.put("mail", etUsuario.getText().toString());
                parametros.put("pass", etPass.getText().toString());*/
                parametros.put("usuario", usuario);
                parametros.put("mail", usuario);
                parametros.put("pass", pass);
                Log.println(Log.INFO, "a", String.valueOf(parametros));
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void forgotPass(View view) {
        Intent i = new Intent(this, ForgotPass.class);

        //Comprobamos que en el login haya puesto el email y no el usuario y, en ese caso
        //lo pasamos al siguiente Acitivty para ahorrarle trabajo al usuario
        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(etUsuario.getText().toString())) {
            i.putExtra("mail", etUsuario.getText().toString());
        }

        startActivity(i);
    }

    /* Estos métodos sirven para hacer el login persistente y cargar
     * directamente la sesión del usuario en concreto directamente
     * tras el activity de carga de la aplicación */
    private void guardarPreferencias() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usuario", usuario);
        editor.putString("pass", pass);
        editor.putBoolean("session", true);
        //Como no se puede guardar un objeto, guardamos la string JSON con la que formamos el objeto
        //en la activity de carga.
        editor.putString("perfilJSON", perfilJSON);
        editor.commit();
    }

    private void recuperarPreferencias() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
        etUsuario.setText(sharedPreferences.getString("usuario", ""));
        etPass.setText(sharedPreferences.getString("pass", ""));
    }
}