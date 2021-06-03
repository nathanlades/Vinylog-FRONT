package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Signin2 extends AppCompatActivity {

    EditText etFecha_nac, etNombre;
    DatePickerDialog picker;
    Button btRegistro;
    String nombre, usuario, pass, mail, localidad, fecha_nac, passHash;
    Spinner spLocalidad;
    RequestQueue requestQueue;
    //private static final String URL1 = "http://192.168.1.93/signin.php";
    private static final String URL1 = "http://95.39.184.89/vinyl/signin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin2);
        spLocalidad = findViewById(R.id.sp_poblacion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sp_poblacion, R.layout.custom_spinner);
        spLocalidad.setAdapter(adapter);
        btRegistro = findViewById(R.id.bt_signin);
        etFecha_nac = findViewById(R.id.et_fecha_nac);
        etNombre = findViewById(R.id.et_nombre);

        //Variables pasadas del otro Activity
        Bundle extras = getIntent().getExtras();
        usuario = extras.getString("usuarioIntent");
        pass = extras.getString("passIntent");
        mail = extras.getString("mailIntent");

        requestQueue = Volley.newRequestQueue(this);
    }

    public void registrar(View view){

        boolean localidadcheck = false, fecha_nacCheck = false, nombreCheck = false;

        //Comprobamos que haya una provincia seleccionada y la cogemos en un String
        String poblacion = "";
        if(spLocalidad.getSelectedItemPosition()!=0){
            poblacion = (String)spLocalidad.getSelectedItem();
            localidadcheck = true;
        } else {
            Toast.makeText(this, "Debe seleccionar una provincia", Toast.LENGTH_SHORT).show();
        }

        String fecha_nac = etFecha_nac.getText().toString();
        String nombre = etNombre.getText().toString();
        
        if (!etFecha_nac.getText().toString().equals("")){
            fecha_nacCheck = true;
        } else {
            Toast.makeText(this, "Debes seleccionar una fecha de nacimiento", Toast.LENGTH_SHORT).show();
        }
        
        if (!etNombre.getText().toString().equals("")){
            nombreCheck = true;
        } else {
            Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
        }

        if (localidadcheck && fecha_nacCheck && nombreCheck){
            //Antes de llamar al método que finalmente registra al usuario, generamos el hash de la contraseña
            //que será lo que guardaremos en la DB
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                byte data[] = pass.getBytes();
                md.update(data);
                byte resumen[] = md.digest();
                passHash = resumen.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            createUser(usuario,pass,mail,fecha_nac,nombre,poblacion);
        }


    }

    private void createUser(final String usuario, final String pass, final String mail, final String fecha_nac, final String nombre, final String poblacion) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Signin2.this,"Usuario registrado", Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent(Signin2.this, MainActivity.class);
                        intent.putExtra("usuarioIntent", usuario);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Signin2.this, error.toString(), Toast.LENGTH_LONG).show();
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