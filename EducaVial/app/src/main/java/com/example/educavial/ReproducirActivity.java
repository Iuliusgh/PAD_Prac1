package com.example.educavial;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class ReproducirActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnPlay;
    private Button btnPause;
    private VideoView video;
    private AlertDialog dialog;
    private TextView message, title;
    private final static String TAG = "ReproducirActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproducir);
        //Inicializamos la clase VideoView asociandole el fichero de Video
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setHomeAsUpIndicator(R.drawable.volver);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.content_header);
        }
        video=(VideoView) findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/"+ R.raw.video;
        video.setVideoURI(Uri.parse(path));

        //Obtenemos los tres botones de la interfaz
        btnPlay= (Button)findViewById(R.id.buttonPlay);
        btnPause= (Button)findViewById(R.id.buttonPause);

        //Y les asignamos el controlador de eventos
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);

        MediaController media = new MediaController(this);
        media.setAnchorView(video);
        video.setMediaController(media);

        Log.d(TAG, "Creando mensaje de ayuda");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        message = (TextView) customLayout.findViewById(R.id.help_text);
        title = (TextView) customLayout.findViewById(R.id.help_title);
        message.setText(R.string.alert_video_text);
        title.setText(R.string.alert_title);
        builder.setView(customLayout);
        dialog = builder.create();
    }

    @Override
    public void onClick(View v) {
        //Comprobamos el identificador del boton que ha llamado al evento para ver
        //cual de los botones es y ejecutar la acción correspondiente
        switch(v.getId()){
            case R.id.buttonPlay:
                //Iniciamos el video
                video.start();
                break;
            case R.id.buttonPause:
                //Pausamos el video
                video.pause();
                break;

        }
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