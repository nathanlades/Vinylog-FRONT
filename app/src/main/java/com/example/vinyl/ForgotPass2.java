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

import java.util.HashMap;
import java.util.Map;

public class ForgotPass2 extends AppCompatActivity {

    EditText et_verificacion, et_passChange1, et_passChange2;
    Button bt_cambioPass;
    int verificacion;
    String mail;
    RequestQueue requestQueue;
    //String URL = "http://192.168.1.93/cambioPass.php";
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass2);
        et_verificacion = findViewById(R.id.et_verificacion);
        et_passChange1 = findViewById(R.id.et_passChange1);
        et_passChange2 = findViewById(R.id.et_passChange2);
        bt_cambioPass = findViewById(R.id.bt_cambioPass);
        
        Bundle extras = getIntent().getExtras();
        verificacion = extras.getInt("verificacion");
        mail = extras.getString("mail");
        URL = getResources().getString(R.string.url)+"cambioPass.php";

        requestQueue = Volley.newRequestQueue(this);
    }

    public void cambioPass(View view){
        String pass = "";
        boolean passcheck = false;

        if (Integer.parseInt(et_verificacion.getText().toString()) == verificacion){
            if (!et_passChange1.getText().toString().equals("") && et_passChange1.getText().toString().equals(
                    et_passChange2.getText().toString())){
                pass = et_passChange1.getText().toString();
                if (pass.length()>=6){
                    passcheck = true;
                } else {
                    Toast.makeText(this, "La contraseña debe de tener un mínimo de seis caracteres", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Las contraseñas no pueden estar vacías y deben coincidir", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "El código no es correcto", Toast.LENGTH_SHORT).show();
        }

        if (passcheck){
            cambioPass(pass,mail);
        }
    }

    private void cambioPass(final String pass, final String mail) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ForgotPass2.this,"Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent(ForgotPass2.this, MainActivity.class);
                        intent.putExtra("usuarioIntent", mail);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgotPass2.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("mail", mail);
                params.put("pass", pass);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}

