package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import metodosExternos.JavaMailAPI;

public class ForgotPass extends AppCompatActivity {

    EditText etMail_recovery;
    Button btRecovery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        etMail_recovery = findViewById(R.id.et_mail_recovery);
        btRecovery = findViewById(R.id.bt_recovery);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            etMail_recovery.setText(extras.getString("mail"));
        }
    }

    public void recuperar(View view) {

        String mail = "";
        boolean mailCheck = false;

        //Generamos un código de 4 dígitos aleatorio que mandaremos por mail para verificar la identidad
        //y que pueda cambiar su contraseña
        int verificacion = ThreadLocalRandom.current().nextInt(1000,9999);
        EmailValidator validator = EmailValidator.getInstance();
        if (!etMail_recovery.getText().toString().equals("") && validator.isValid(etMail_recovery.getText().toString())){
            mail = etMail_recovery.getText().toString();
            mailCheck = true;
        } else {
            Toast.makeText(this, "Debes introducir un mail correcto", Toast.LENGTH_SHORT).show();
        }

        String message = "El código para reiniciar la contraseña es: " + verificacion + ", introdúcelo en la aplicación para permitir el cambio." +
                " \n \nSi tú no has solicitado cambiar la contraseña, no hagas caso de este email." +
                "\n \n Vinylog.";
        String subject = "Cambio de contraseña — Vinylog";

        if (mailCheck) {
            JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);
            javaMailAPI.execute();
            Intent intent = new Intent(this, ForgotPass2.class);
            intent.putExtra("verificacion", verificacion);
            intent.putExtra("mail", mail);
            startActivity(intent);
        }
    }


}