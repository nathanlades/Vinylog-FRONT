package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import POJO.Perfil;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario, etPass;
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsuario = findViewById(R.id.et_user);
        etPass = findViewById(R.id.et_pass);
        btLogin = findViewById(R.id.bt_login);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            etUsuario.setText(extras.getString("usuarioIntent"));
        }
    }

    public void registro(View view){
        Intent i = new Intent(this, SigninActivity.class);
        startActivity(i);
    }

    public void login(View view){
         validarUsuario("http://95.39.184.89/vinyl/login.php");
         //validarUsuario("http://192.168.1.93/login.php");
    }

    private void validarUsuario(String URL){
        
        StringRequest stringRequest  = new StringRequest (Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       //Por alguna razón no hace bien el bucle si comparo directamente el "response" en vez de
                        // hacer primero una String, por tanto la String respuestaJson solo vale para
                        //que entre bien en el bucle.
                       String respuestaJson = response;
                       Perfil perfil = new Gson().fromJson(response, Perfil.class); //Con esta línea convierto la String JSON en un objeto de la clase Perfil
                        if(respuestaJson.contains("{")){
                           //Aquí lo que hacer si el login es correcto
                            Toast.makeText(MainActivity.this, "Bienvenido, " + perfil.getNombre(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, FeedActivity.class);
                            intent.putExtra("PerfilIntent", perfil);
                            startActivity(intent);
                            /*Vale, tienes que poner en el intent el siguiente Activity al que tenga que ir la app,
                            el intent te pasa un objeto de la clase Perfil con todos los datos de la tabla Perfil
                            de la persona logeada. Para recuperarlo, tendrás que utilizar la siguiente línea:
                            getIntent().getSerializableExtra("PerfilIntent");
                             */
                       } else {
                           Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                       }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_LONG);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("usuario", etUsuario.getText().toString());
                parametros.put("mail", etUsuario.getText().toString());
                parametros.put("pass", etPass.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void forgotPass(View view){
        Intent i = new Intent(this, ForgotPass.class);

        //Comprobamos que en el login haya puesto el email y no el usuario y, en ese caso
        //lo pasamos al siguiente Acitivty para ahorrarle trabajo al usuario
        EmailValidator validator = EmailValidator.getInstance();
        if(validator.isValid(etUsuario.getText().toString())){
            i.putExtra("mail",etUsuario.getText().toString());
        }

        startActivity(i);
    }
}