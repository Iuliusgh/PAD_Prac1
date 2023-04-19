package com.example.educavial;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton settings, profile;
    private Button learn, examination, scan;

    private final static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Log.d(TAG, "Inicializando variables");
        settings = findViewById(R.id.ajustes);
        profile = findViewById(R.id.perfil);
        learn = findViewById(R.id.aprender);
        examination = findViewById(R.id.evaluar);
        scan= findViewById(R.id.escanear);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfil();
            }
        });

        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temario();
            }
        });

        examination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluar();
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {escanear();}
        });

        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean("is_first_time", true);

        if (isFirstTime) {
            // La aplicación está siendo iniciada por primera vez
            // Mostrar un diálogo para solicitar correo y usuario

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            View customLayout = getLayoutInflater().inflate(R.layout.custom_activity_dialog_main, null);
            builder.setView(customLayout);
            AlertDialog dialog = builder.create();

            builder.setPositiveButton("Aceptar", new   DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText coro = customLayout.findViewById(R.id.editTextTextEmailAddress);
                    EditText usu = customLayout.findViewById(R.id.editTextTextPersonName);
                    // Guardar el correo y usuario ingresados
                    String correo = coro.getText().toString();
                    String usuario = usu.getText().toString();

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("correo", correo);
                    editor.putString("usuario", usuario);
                    editor.putBoolean("is_first_time", false);
                    editor.apply();

                    dialog.dismiss();
                }
            });

            builder.show();
        }
    }

    private void config(){
        Intent i = new Intent(this, AjustesActivity.class);
        startActivity(i);
    }

    private void perfil(){
        Intent i = new Intent(this, PerfilActivity.class);
        startActivity(i);
    }

    private void temario(){
        Intent i = new Intent(this, TemarioActivity.class);
        startActivity(i);
    }

    private void evaluar(){
        Intent i = new Intent(this, EvaluacionActivity.class);
        startActivity(i);
    }
    private void escanear(){
        Intent i = new Intent(this,EscanearActivity.class);
        startActivity(i);
    }
}