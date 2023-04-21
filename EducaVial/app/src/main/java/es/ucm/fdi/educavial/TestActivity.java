package es.ucm.fdi.educavial;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class TestActivity extends AppCompatActivity {

    private final static String TAG = "TestActivity";
    private TextView message, title, endDialogText, endDialogTitle;
    private Button bt1, bt2, bt3, dialogBtn;
    private ImageButton ibt1, ibt2, ibt3;
    private AlertDialog dialog, endDialog;
    private Object ant;
    private GifImageView gif;

    private boolean resultado = true;

    private int cont = 0;

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

        ibt1.setImageResource(R.drawable.stop);
        ibt1.setContentDescription(getResources().getString(R.string.stop));
        ibt2.setImageResource(R.drawable.senal_v_30);
        ibt2.setContentDescription(getResources().getString(R.string.velocidad));
        ibt3.setImageResource(R.drawable.p_anda);
        ibt3.setContentDescription(getResources().getString(R.string.prohibido));

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

        Log.d(TAG, "Creando mensaje del fin");
        AlertDialog.Builder aux = new AlertDialog.Builder(this);
        View customDialogLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog_test, null);
        gif = (GifImageView) customDialogLayout.findViewById(R.id.gif);
        gif.setImageResource(R.drawable.happy);
        endDialogTitle = (TextView) customDialogLayout.findViewById(R.id.title);
        endDialogText = (TextView) customDialogLayout.findViewById(R.id.text);
        dialogBtn = (Button) customDialogLayout.findViewById(R.id.button);
        dialogBtn.setText(R.string.cont);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDialog.dismiss();
                finish();
            }
        });
        endDialogTitle.setText(R.string.cong_title);
        endDialogText.setText(R.string.cong_text);
        aux.setView(customDialogLayout);

        endDialog = aux.create();
    }

    private void addListener(Object obj){
        if(obj instanceof Button){
            Button res = ((Button)obj);
            res.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    disable(res);
                    if(res.isEnabled()){
                        res.setBackgroundResource(R.drawable.forma_boton_seleccionada);
                        res.setEnabled(false);
                    }

                    if(cont % 2 == 0){
                        if(res.getText().equals(((ImageButton)ant).getContentDescription())){
                            res.setBackgroundResource(R.drawable.forma_boton_correcto);
                            ((ImageButton) ant).setBackgroundResource(R.drawable.forma_boton_correcto);
                        }
                        else{
                            res.setBackgroundResource(R.drawable.forma_boton_incorrecta);
                            ((ImageButton) ant).setBackgroundResource(R.drawable.forma_boton_incorrecta);
                            resultado = false;
                        }
                        enable(res);
                    }
                    if(cont == 6){
                        fail();
                        endDialog.show();
                    }
                    ant = res;
                }
            });
        }
        else if(obj instanceof ImageButton){
            ImageButton res = ((ImageButton)obj);
            res.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    disable(res);
                    if(res.isEnabled()){
                        res.setEnabled(false);
                        res.setBackgroundResource(R.drawable.forma_boton_seleccionada);
                    }
                    if(cont % 2 == 0) {
                        if(res.getContentDescription().equals(((Button)ant).getText())){
                            res.setBackgroundResource(R.drawable.forma_boton_correcto);
                            ((Button) ant).setBackgroundResource(R.drawable.forma_boton_correcto);
                        }
                        else{
                            res.setBackgroundResource(R.drawable.forma_boton_incorrecta);
                            ((Button) ant).setBackgroundResource(R.drawable.forma_boton_incorrecta);
                            resultado = false;
                        }
                        enable(res);
                    }
                    if(cont == 6){
                        fail();
                        endDialog.show();
                    }
                    ant = res;
                }
            });
        }

    }

    private void disable(Object btn){
        if(btn instanceof ImageButton){
            if(ibt1 != ((ImageButton) btn) && !ibt1.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_seleccionada).getConstantState()))
                ibt1.setEnabled(false);
            if(ibt2 != ((ImageButton) btn) && !ibt2.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_seleccionada).getConstantState()))
                ibt2.setEnabled(false);
            if(ibt3 != ((ImageButton) btn) && !ibt3.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_seleccionada).getConstantState()))
                ibt3.setEnabled(false);
        }
        else if(btn instanceof Button){
            if(bt1 != ((Button) btn) && !bt1.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_seleccionada).getConstantState()))
                bt1.setEnabled(false);
            if(bt2 != ((Button) btn) && !bt2.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_seleccionada).getConstantState()))
                bt2.setEnabled(false);
            if(bt3 != ((Button) btn) && !bt3.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_seleccionada).getConstantState()))
                bt3.setEnabled(false);
        }
        cont++;
    }

    private void enable(Object btn){
        if(!ibt1.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_seleccionada).getConstantState())
            && !ibt1.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_incorrecta).getConstantState())
            && !ibt1.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_correcto).getConstantState()))
            ibt1.setEnabled(true);
        if(!ibt2.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_seleccionada).getConstantState())
                && !ibt2.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_incorrecta).getConstantState())
                && !ibt2.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_correcto).getConstantState()))
            ibt2.setEnabled(true);
        if(!ibt3.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_seleccionada).getConstantState())
                && !ibt3.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_incorrecta).getConstantState())
                && !ibt3.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_correcto).getConstantState()))
            ibt3.setEnabled(true);
        if(!bt1.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_seleccionada).getConstantState())
                && !bt1.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_incorrecta).getConstantState())
                && !bt1.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_correcto).getConstantState()))
            bt1.setEnabled(true);
        if(!bt2.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_seleccionada).getConstantState())
                && !bt2.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_incorrecta).getConstantState())
                && !bt2.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_correcto).getConstantState()))
            bt2.setEnabled(true);
        if(!bt3.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_seleccionada).getConstantState())
                && !bt3.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_incorrecta).getConstantState())
                && !bt3.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.forma_boton_correcto).getConstantState()))
            bt3.setEnabled(true);
    }

    private void fail(){
        if(!resultado){
            endDialogTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.red, null));
            endDialogTitle.setText(R.string.f_title);
            endDialogText.setTextColor(ResourcesCompat.getColor(getResources(), R.color.red, null));
            endDialogText.setText(R.string.f_text);
            dialogBtn.setText(R.string.again);
            gif.setImageResource(R.drawable.sad);
            dialogBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    endDialog.dismiss();
                    recreate();
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