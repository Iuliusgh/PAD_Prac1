package es.ucm.fdi.educavial;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import es.ucm.fdi.educavial.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton settings, profile;
    private Button learn, examination, scan;
    private AlertDialog dialog1;
    private final static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        //ejemplo de uso

        /*Senal senal = viewmodel.getSenalBycodigo("R-1");
        viewmodel.updateValorBooleanoById("R-1",true);
        Senal senal2 = viewmodel.getSenalBycodigo("R-1");
        Log.d("ssenal2",senal2.codigo);*/
        /*LiveData<List<Senal>> senalListLiveData=viewmodel.getSenallist();
        senalListLiveData.observeForever(new Observer<List<Senal>>() {
            @Override
            public void onChanged(List<Senal> senalList) {
                // Recorre la lista y obtiene el ID de cada objeto Senal
                for (Senal senal : senalList) {
                    Log.d("tamaño:","tamaño: "+senal.codigo);
                    // Haga lo que sea necesario con el ID de la Senal
                }
                Log.d("tamaño:","tamaño: "+senalList.size());
            }
        });
        */

        // para mostrar por consola el nombre de las señales,solo la primera vez
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

            builder.setPositiveButton("Aceptar",null );
            dialog1 = builder.create();
            View.OnClickListener myListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText coro = customLayout.findViewById(R.id.editTextTextEmailAddress);
                    EditText usu = customLayout.findViewById(R.id.editTextTextPersonName);
                    // Guardar el correo y usuario ingresados
                    String correo = coro.getText().toString();
                    String usuario = usu.getText().toString();

                    if (TextUtils.isEmpty(correo)) {
                        Toast.makeText(MainActivity.this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(usuario)) {
                        Toast.makeText(MainActivity.this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();

                    } else {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("correo", correo);
                        editor.putString("usuario", usuario);
                        editor.putBoolean("is_first_time", false);
                        editor.apply();
                        recreate();

                        dialog1.dismiss();
                    }
                }
            };
            if (dialog1 != null) {
                dialog1.setCanceledOnTouchOutside(false);
                dialog1.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button b = dialog1.getButton(DialogInterface.BUTTON_POSITIVE);
                        b.setOnClickListener(myListener);
                    }
                });

                dialog1.show();
            }
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