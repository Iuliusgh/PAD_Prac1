package com.example.educavial;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton settings, profile;
    private Button learn;

    private final static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Log.d(TAG, "Inicializando variables");
        settings = findViewById(R.id.ajustes);
        profile = findViewById(R.id.perfil);
        learn = findViewById(R.id.aprender);

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
        Userviewmodel viewmodel = ViewModelProviders.of(this).get(Userviewmodel.class);
        User juan=new User("xd","lopez");
        User pepe=new User("pepe","lopez");
        viewmodel.deleteAllUsers();
        viewmodel.insertUser(juan);
        viewmodel.insertUser(pepe);
        LiveData<List<User>> todos=viewmodel.getUserlist();
        todos.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                for (User user : userList) {
                    Log.d("Usuario", "Nombre: " + user.username + " - Correo: " + user.email);
                }
            }
        });

    }
    private static class insertAsyncTask extends AsyncTask
            <User, Void, Void> {
        private UserDAO mAsyncTaskDao;
        insertAsyncTask(UserDAO dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insertAllUser(params[0]);
            return null;
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
}