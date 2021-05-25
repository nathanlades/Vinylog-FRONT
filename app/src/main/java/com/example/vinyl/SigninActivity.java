package com.example.vinyl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
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

public class SigninActivity extends AppCompatActivity {

    EditText etUsuario, etPass1, etPass2, etMail, etFecha_nac, etNombre, etPoblacion;
    DatePickerDialog picker;

    RequestQueue requestQueue;
    private static final String URL1 = "http://95.39.184.89/vinyl/signin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        etUsuario = findViewById(R.id.et_usuario);
        etPass1 = findViewById(R.id.et_pass1);
        etPass2 = findViewById(R.id.et_pass2);
        etMail = findViewById(R.id.et_mail);
        etFecha_nac = findViewById(R.id.et_fecha_nac);
        etNombre = findViewById(R.id.et_nombre);
        etPoblacion = findViewById(R.id.et_poblacion);

        requestQueue = Volley.newRequestQueue(this);
    }

    public void registrar(View v){
        boolean passcheck = false;
        String usuario = etUsuario.getText().toString();
        if (etPass1.getText().toString().equals(etPass2.getText().toString())){
            passcheck = true;
        }
        String pass = etPass1.getText().toString();
        String mail = etMail.getText().toString();
        String fecha_nac = etFecha_nac.getText().toString();
        String nombre = etNombre.getText().toString();
        String poblacion = etPoblacion.getText().toString();

        createUser(usuario,pass,mail,fecha_nac,nombre,poblacion);
    }

    private void createUser(final String usuario, final String pass, final String mail, final String fecha_nac, final String nombre, final String poblacion) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SigninActivity.this,"Usuario registrado", Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent(SigninActivity.this, MainActivity.class);
                        intent.putExtra("usuarioIntent", usuario);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SigninActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("usuario", usuario);
                params.put("pass", pass);
                params.put("mail", mail);
                params.put("fecha_nac", fecha_nac);
                params.put("nombre", nombre);
                params.put("poblacion", poblacion);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void showDatePickerDialog(View v){
        picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                etFecha_nac.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, 2000,1,1);
        picker.show();
    }
}