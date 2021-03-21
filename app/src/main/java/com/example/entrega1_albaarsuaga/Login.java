package com.example.entrega1_albaarsuaga;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    // Instanciamos los objetos para los campos y botones del formulario
    EditText editTextNombreUsuarioL, editTextContrasenaL;
    Button buttonIniciarSesionL;

    // Instanciamos el gestorDB
    GestorBD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Asignamos los id a las variables
        editTextNombreUsuarioL= (EditText)findViewById(R.id.editTextNombreUsuarioL);
        editTextContrasenaL= (EditText)findViewById(R.id.editTextContrasenaL);

        buttonIniciarSesionL= (Button)findViewById(R.id.buttonIniciarSesionL);

        bd = new GestorBD(getApplicationContext());

        // Botón de iniciar sesión
        buttonIniciarSesionL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos lo insertado en los editText
                String nombreUsuL = editTextNombreUsuarioL.getText().toString();
                String contraL = editTextContrasenaL.getText().toString();

                // Si el usuario no ha insertado datos
                if(nombreUsuL.equals("") | contraL.equals("")){
                    // Se le pide que los inserte
                    Toast.makeText(Login.this, "Introduzca sus datos, por favor.", Toast.LENGTH_LONG).show();
                }else{
                    // Si ha insertado, se comprueba que estén bien
                    Boolean comprobarUsu = bd.comprobarUsuarioContrasema(nombreUsuL,contraL);

                    // Si la contraseña está bien
                    if(comprobarUsu == true){
                        // Notificamos al usuario que ha iniciado sesión correctamente

                        // Crear el builder
                        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getApplicationContext(), "IdCanal");
                        elBuilder.setContentTitle("Inicio de sesión completa.");
                        elBuilder.setContentText("¡Has iniciado sesión con éxito!");
                        elBuilder.setSmallIcon(R.drawable.ic_launcher_background);
                        elBuilder.setAutoCancel(true);

                        // Notificar al usuario
                        NotificationManagerCompat elManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                        elManagerCompat.notify(1,elBuilder.build());

                        // Redirigimos a la página principal
                        Intent intent = new Intent (v.getContext(), MainActivity.class);
                        startActivityForResult(intent, 0);

                    // Si es correcto
                    }else{
                        // Se informa al usuario
                        Toast.makeText(Login.this, "¡Datos incorrectos!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}