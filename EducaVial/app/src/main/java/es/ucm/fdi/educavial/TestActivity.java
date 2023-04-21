package es.ucm.fdi.educavial;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    private final static String TAG = "TestActivity";
    private TextView message, title;
    private Button bt1, bt2, bt3;
    private ImageButton ibt1, ibt2, ibt3;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setHomeAsUpIndicator(R.drawable.volver);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.examination_title);
        }

        Log.d(TAG, "Inicializando los botones");
        bt1 = findViewById(R.id.button5);
        bt2 = findViewById(R.id.button6);
        bt3 = findViewById(R.id.button7);

        ibt1 = findViewById(R.id.botonSenal1);
        ibt2 = findViewById(R.id.botonSenal2);
        ibt3 = findViewById(R.id.botonSenal3);

        addListener(bt1);
        addListener(bt2);
        addListener(bt3);
        addListener(ibt1);
        addListener(ibt2);
        addListener(ibt3);

        Log.d(TAG, "Creando mensaje de ayuda");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        message = (TextView) customLayout.findViewById(R.id.help_text);
        title = (TextView) customLayout.findViewById(R.id.help_title);
        message.setText(R.string.alert_profile_text);
        title.setText(R.string.alert_title);
        builder.setView(customLayout);
        dialog = builder.create();
    }

    private void addListener(Object obj){
        if(obj instanceof Button){
            Button res = ((Button)obj);
            res.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    res.setBackgroundResource(R.drawable.forma_boton_seleccionada);
                }
            });
        }
        else if(obj instanceof ImageButton){
            ImageButton res = ((ImageButton)obj);
            res.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    res.setBackgroundResource(R.drawable.forma_boton_seleccionada);
                }
            });
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