package es.ucm.fdi.educavial;



import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ListaSenalesActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private Senalviewmodel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_senales);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.volver);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.learnt_trafic_sign);
        }
        LinearLayout parentLinearLayout=findViewById(R.id.lista);
        viewModel= ViewModelProviders.of(this).get(Senalviewmodel.class);
        viewModel.getSenallist().observe(this,senalList->{
            int tam=senalList.size();
            for (int i=0;i<tam/3;i++){
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                for (int j=0;j<3;j++){
                    AppCompatButton senal =  new AppCompatButton(this);
                    String signalCode=senalList.get(i*3+j).codigo.toLowerCase().replace("-","");
                    setImageFromAPI(senal,signalCode);
                    senal.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(this,R.drawable.placeholder),null,null);
                    senal.setText(senalList.get(i*3+j).nombre);
                    senal.setBackgroundColor(Color.TRANSPARENT);
                    senal.setTextColor(Color.BLACK);
                    senal.setMaxLines(5);
                    senal.setTextSize(10);
                    senal.setLayoutParams(new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                            ,1));
                    row.addView(senal);
                }
                parentLinearLayout.addView(row);
            }
        });

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
    private void setImageFromAPI(AppCompatButton button, String signalCode){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String APIResult=NetworkUtils.getSignalInfoJson(signalCode);
                try {
                    JSONObject jsonObject = new JSONObject(APIResult);
                    JSONArray listOfItems = (JSONArray) jsonObject.get("query");
                    int a = 3;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        };



    }
}