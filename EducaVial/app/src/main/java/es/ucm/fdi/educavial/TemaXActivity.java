package es.ucm.fdi.educavial;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Locale;

public class TemaXActivity extends AppCompatActivity {

    private final static int MAX_BAR_VALUE = 100;
    private final static String TAG = "TemaXActivity";
    private TextView message, title, titulo, texto;
    private Button ant, sig;
    private AlertDialog dialog, endDialog;
    private int pos, barValue;
    private ImageButton play;
    private ArrayList<String> cont = new ArrayList<String>();
    private ShapeableImageView imagen;
    private ProgressBar bar;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_tema_xactivity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setHomeAsUpIndicator(R.drawable.volver);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.syllabus_title);
        }

        imagen = findViewById(R.id.imagen);
        titulo = findViewById(R.id.titulo);
        texto = findViewById(R.id.texto);

        imagen.setImageResource(R.drawable.stop);

        pos = 0;
        bar = findViewById(R.id.progressBar);
        bar.setScaleY(1.5f);
        bar.setMax(MAX_BAR_VALUE);
        bar.setProgressTintList(ColorStateList.valueOf(Color.BLACK));

        cont.add("Señal 0");
        cont.add("Señal 1");
        cont.add("Señal 2");

        barValue = MAX_BAR_VALUE / cont.size();

        titulo.setText(cont.get(pos));
        texto.setText(cont.get(pos));

        ant = findViewById(R.id.ant);
        ant.setEnabled(false);
        ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos--;
                if(pos == 0){
                    ant.setEnabled(false);
                }
                changeContent();
                bar.setProgress(bar.getProgress() - barValue);
            }
        });

        sig = findViewById(R.id.sig);
        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos++;
                bar.setProgress( bar.getProgress() + barValue);
                if(pos == cont.size()){
                    endDialog.show();
                }
                if(pos < cont.size()) {
                    ant.setEnabled(true);
                    changeContent();

                }
            }
        });
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });

        play = findViewById(R.id.reproducir);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textToSpeech != null && textToSpeech.isSpeaking()){
                    textToSpeech.stop();
                }
                else{
                    String text = "We and our partners store or access information on a device, such as cookies, and process personal data, such as unique identifiers and standard information sent by a device, for ads and personalized content, ad and content measurement, and information about the user. public, as well as to develop and improve products. With your permission, we and our partners may use precise geographic location data and identification through device characteristics. You can click to give your consent to us and our partners to carry out the processing described above. Alternatively, you can access more detailed information and change your preferences before giving or denying your consent. Please note that some processing of your personal data may not require your consent, but you have the right to object to such processing. Your preferences will apply only to this website. You can change your preferences at any time by re-entering this website or by visiting our privacy policy.";
                    Bundle params = new Bundle();
                    params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "hello");
                    textToSpeech.speak(titulo.getText().toString() + texto.getText().toString(), TextToSpeech.QUEUE_FLUSH, params, "hello");
                }
            }
        });

        AlertDialog.Builder aux = new AlertDialog.Builder(this);
        aux.setTitle(R.string.congratulation);
        aux.setMessage(R.string.cong_info);
        aux.setPositiveButton(R.string.return_to_syllabus, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                end();
            }
        });

        endDialog = aux.create();


        Log.d(TAG, "Creando mensaje de ayuda");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        message = (TextView) customLayout.findViewById(R.id.help_text);
        title = (TextView) customLayout.findViewById(R.id.help_title);
        message.setText(R.string.alert_theme_text);
        title.setText(R.string.alert_title);
        builder.setView(customLayout);
        dialog = builder.create();
    }

    private void changeContent(){
        titulo.setText(cont.get(pos));
        texto.setText(cont.get(pos));
    }


    private void end(){
        Intent i = new Intent(this, TemarioActivity.class);
        startActivity(i);
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