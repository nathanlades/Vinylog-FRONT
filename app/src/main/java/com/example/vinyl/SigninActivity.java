package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;
import org.apache.commons.validator.routines.EmailValidator;

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

public class SigninActivity extends AppCompatActivity {

    EditText etUsuario, etPass1, etPass2, etMail, etFecha_nac, etNombre, etPoblacion;
    Button btRegistro;

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
        btRegistro = findViewById(R.id.bt_signin2);
//        spLocalidad = findViewById(R.id.sp_poblacion);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sp_poblacion, android.R.layout.simple_spinner_dropdown_item);
//        spLocalidad.setAdapter(adapter);

//        requestQueue = Volley.newRequestQueue(this);
    }

    public void registrar(View v){
        boolean passcheck = false, passUsuario = false, passMail = false;


        String usuario = etUsuario.getText().toString();
        String pass = etPass1.getText().toString();
        String passHash;
        String mail = etMail.getText().toString();

        if(!etUsuario.getText().toString().equals("")){
            passUsuario = true;
        } else {
            Toast.makeText(this, "El nombre de usuario no puede estar en blanco", Toast.LENGTH_SHORT).show();
        }

        //Este if comprueba que las contraseñas sean igual y no estén en blanco y tengan 6 o más caracteres
        if (etPass1.getText().toString().equals(etPass2.getText().toString()) && !etPass1.getText().toString().equals("")){
            if (pass.length()>=6){
                passcheck = true;
            } else {
                Toast.makeText(this, "La contraseña tiene menos de 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SigninActivity.this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }

        //EmailValidator sale de una librería de apache que he incorporado al proyecto
        //que valida una dirección de email introducida ahorrándonos bastante código en hacerlo nosotros mismos.
        EmailValidator validator = EmailValidator.getInstance();
        if(!etMail.getText().toString().equals("") && validator.isValid(mail)){
            passMail = true;
        } else {
            Toast.makeText(this, "El email no es correcto", Toast.LENGTH_SHORT).show();
        }

        if(passcheck && passMail && passUsuario) {
            Intent intent = new Intent(SigninActivity.this, Signin2.class);
            intent.putExtra("usuarioIntent", usuario);
            intent.putExtra("passIntent", pass);
            intent.putExtra("mailIntent", mail);
            startActivity(intent);
        }
    }
}