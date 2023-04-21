package com.example.educavial;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class PerfilActivity extends AppCompatActivity {
    private   Userviewmodel userviewmodel;
    private final static String TAG = "PerfilActivity";
    private TextView message, title;
    private TextView user_name;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_perfil);
        //user_name=findViewById(R.id.user_name);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setHomeAsUpIndicator(R.drawable.volver);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.profile_title);
        }

        Log.d(TAG, "Creando mensaje de ayuda");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        message = (TextView) customLayout.findViewById(R.id.help_text);
        title = (TextView) customLayout.findViewById(R.id.help_title);
        message.setText(R.string.alert_profile_text);
        title.setText(R.string.alert_title);
        builder.setView(customLayout);
        dialog = builder.create();
        /*userviewmodel = ViewModelProviders.of(this).get(Userviewmodel.class);
        User juan=new User("xd","lopez");
        userviewmodel.deleteAllUsers();
        userviewmodel.insertUser(juan);
        userviewmodel.getUserlist().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> taskList) {
                if (!taskList.isEmpty()) {
                    // Mostrar el título de la primera tarea en el TextView
                    user_name.setText(taskList.get(0).username);

                }
            }
        });*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.help:
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}