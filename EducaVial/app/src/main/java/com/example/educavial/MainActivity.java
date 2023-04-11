package com.example.educavial;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton settings, profile;
    private Button learn, examination;

    private final static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Log.d(TAG, "Inicializando variables");
        settings = findViewById(R.id.ajustes);
        profile = findViewById(R.id.perfil);
        learn = findViewById(R.id.aprender);
        examination = findViewById(R.id.evaluar);

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
}