package com.example.educavial;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TemaXActivity extends AppCompatActivity {

    private final static String TAG = "TemaXActivity";
    private TextView message, title;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tema_xactivity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setHomeAsUpIndicator(R.drawable.volver);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.syllabus_title);
        }

        Log.d(TAG, "Creando mensaje de ayuda");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        message = (TextView) customLayout.findViewById(R.id.help_text);
        title = (TextView) customLayout.findViewById(R.id.help_title);
        message.setText(R.string.alert_syllabus_text);
        title.setText(R.string.alert_title);
        builder.setView(customLayout);
        dialog = builder.create();
    }
}