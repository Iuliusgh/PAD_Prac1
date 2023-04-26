package es.ucm.fdi.educavial;



import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;

import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.HttpUrl;


public class ListaSenalesActivity extends AppCompatActivity {
    private static final String TAG = "ListaSenalesActivity";
    private AlertDialog dialog, senalDialog;
    private Button volver;
    private TextView senal_titulo, senal_info;
    private ShapeableImageView senal_imagen;
    private Senalviewmodel viewModel;
    private static final String BASE_URL =
            "https://en.wikipedia.org/w/api.php?";
    private static final String QUERY_PARAM = "action";
    private static final String TITLES = "titles";
    private static final String PROP = "prop";

    private static final String IIPROP = "iiprop";
    private static final String FORMAT = "format";
    private final String[] res = new String[91];
    ArrayList<Bitmap> fotos=new ArrayList<Bitmap>();
    ArrayList<MyButton> senales=new ArrayList<MyButton>();



    private Button filtroNombre, filtroForma,filtroColor;

    private int numRequests = 0; //counts volley requests


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_senales);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setHomeAsUpIndicator(R.drawable.volver);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.learnt_trafic_sign);
        }
        filtroNombre = findViewById(R.id.filtro_nombre);
        filtroColor=findViewById(R.id.filtro_color);
        filtroForma=findViewById(R.id.filtro_forma);
        LinearLayout parentLinearLayout=findViewById(R.id.lista);
        initUI(parentLinearLayout);

        filtroNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.GetSenalsbynombre().observe(ListaSenalesActivity.this, senalList -> {
                    Collections.sort(senales,(MyButton a, MyButton b)->{
                        int idA=a.getIdButton();
                        int idB=b.getIdButton();
                        int indexA=-1;
                        int indexB=-1;
                        for(int i=0;i<senalList.size();i++){
                            if(senalList.get(i).getId()==idA){
                                indexA=i;
                            }
                            if(senalList.get(i).getId()==idB){
                                indexB=i;
                            }
                        }
                       return Integer.compare(indexA,indexB);
                    });
                    parentLinearLayout.removeAllViews();
                    int tam=senalList.size();
                    for (int i = 0; i <Math.ceil((double)tam/3); i++) {
                        LinearLayout row = new LinearLayout(ListaSenalesActivity.this);
                        row.setOrientation(LinearLayout.HORIZONTAL);
                        row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        for (int j = 0; j < 3 && (3 * i) + j < tam; j++) {
                            MyButton senal= new MyButton(ListaSenalesActivity.this);
                            senal.copy(senales.get(i * 3 + j));
                            senal.setBackgroundColor(Color.TRANSPARENT);
                            senal.setTextColor(Color.BLACK);
                            senal.setTextSize(10);
                            senal.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
                            row.addView(senal);
                        }
                        parentLinearLayout.addView(row);
                    }
                });
            }
            });
        filtroColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.GetSenalsbycolor().observe(ListaSenalesActivity.this, senalList -> {
                    Collections.sort(senales,(MyButton a, MyButton b)->{
                        int idA=a.getIdButton();
                        int idB=b.getIdButton();
                        int indexA=-1;
                        int indexB=-1;
                        for(int i=0;i<senalList.size();i++){
                            if(senalList.get(i).getId()==idA){
                                indexA=i;
                            }
                            if(senalList.get(i).getId()==idB){
                                indexB=i;
                            }
                        }
                        return Integer.compare(indexA,indexB);
                    });
                    parentLinearLayout.removeAllViews();
                    int tam=senalList.size();
                    for (int i = 0; i <Math.ceil((double)tam/3); i++) {
                        LinearLayout row = new LinearLayout(ListaSenalesActivity.this);
                        row.setOrientation(LinearLayout.HORIZONTAL);
                        row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        for (int j = 0; j < 3 && (3 * i) + j < tam; j++) {
                            MyButton senal= new MyButton(ListaSenalesActivity.this);
                            senal.copy(senales.get(i * 3 + j));
                            senal.setBackgroundColor(Color.TRANSPARENT);
                            senal.setTextColor(Color.BLACK);
                            senal.setTextSize(10);
                            senal.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
                            row.addView(senal);
                        }
                        parentLinearLayout.addView(row);
                    }
                });
            }
        });
        filtroForma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.GetSenalsbyforma().observe(ListaSenalesActivity.this, senalList -> {
                    Collections.sort(senales,(MyButton a, MyButton b)->{
                        int idA=a.getIdButton();
                        int idB=b.getIdButton();
                        int indexA=-1;
                        int indexB=-1;
                        for(int i=0;i<senalList.size();i++){
                            if(senalList.get(i).getId()==idA){
                                indexA=i;
                            }
                            if(senalList.get(i).getId()==idB){
                                indexB=i;
                            }
                        }
                        return Integer.compare(indexA,indexB);
                    });
                    parentLinearLayout.removeAllViews();
                    int tam=senalList.size();
                    for (int i = 0; i <Math.ceil((double)tam/3); i++) {
                        LinearLayout row = new LinearLayout(ListaSenalesActivity.this);
                        row.setOrientation(LinearLayout.HORIZONTAL);
                        row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        for (int j = 0; j < 3 && (3 * i) + j < tam; j++) {
                            MyButton senal= new MyButton(ListaSenalesActivity.this);
                            senal.copy(senales.get(i * 3 + j));
                            senal.setBackgroundColor(Color.TRANSPARENT);
                            senal.setTextColor(Color.BLACK);
                            senal.setTextSize(10);
                            senal.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
                            row.addView(senal);
                        }
                        parentLinearLayout.addView(row);
                    }
                });
            }
        });

        Log.d(TAG, "Creando mensaje de ayuda");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog_senial, null);
        senal_info = (TextView) customLayout.findViewById(R.id.senal_info);
        senal_titulo = (TextView) customLayout.findViewById(R.id.senal_titulo);
        volver = (Button) customLayout.findViewById(R.id.senal_volver);
        senal_imagen = (ShapeableImageView) customLayout.findViewById(R.id.senal_imagen);
        builder.setView(customLayout);
        senalDialog = builder.create();
        senalDialog.setCancelable(false);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senalDialog.dismiss();
            }
        });
    }
    private void initUI(LinearLayout parentLinearLayout){
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);//1MB
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();


        viewModel= ViewModelProviders.of(this).get(Senalviewmodel.class);
        viewModel.getSenallist().observe(this,senalList->{
            int tam=senalList.size();
            int index=0;
            for (int i = 0; i <Math.ceil((double)tam/3); i++){
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                for (int j=0;j<3&&(3*i)+j<tam;j++){
                    MyButton senal =  new MyButton(this);
                    String signalCode=senalList.get(i *3+j).codigo.toLowerCase().replaceFirst("-","");
                    final int aux=index;
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
                    urlBuilder.addQueryParameter(QUERY_PARAM, "query");
                    urlBuilder.addQueryParameter(TITLES, "File"+ Uri.decode(":")+"Spain_traffic_signal_"+signalCode+".svg");
                    urlBuilder.addQueryParameter(PROP, "imageinfo");
                    urlBuilder.addQueryParameter(IIPROP, "url");
                    urlBuilder.addQueryParameter(FORMAT, "json");
                    senal.setCodigo(senalList.get(i *3+j).codigo);
                    senal.setDescripcionSenal(senalList.get(i *3+j).descripcion);
                    senal.setAprendida(senalList.get(i*3+j).aprendido);
                    senal.setIdButton(senalList.get(i*3+j).id);
                    String urlWithQueryParams = urlBuilder.build().toString();
                    StringRequest stringRequest = new StringRequest(GET, urlWithQueryParams,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONObject queryObject = jsonObject.getJSONObject("query");
                                        JSONObject pagesObject = queryObject.getJSONObject("pages");
                                        JSONObject menosUnoObject = pagesObject.getJSONObject("-1");
                                        JSONArray imageinfoArray = (JSONArray) menosUnoObject.get("imageinfo");
                                        JSONObject info = imageinfoArray.getJSONObject(0);
                                        String url = info.getString("url");
                                        res[aux]=url;
                                    }
                                    catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                    numRequests--;
                                    if(numRequests==0) {
                                        Log.i("Volley", "Requests completed");
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    for (int l=0;l<tam;l++) {
                                                        InputStream inputStream = new URL(res[l]).openStream();
                                                        SVG svg = SVG.getFromInputStream(inputStream);
                                                        Drawable drawable = new PictureDrawable(svg.renderToPicture());
                                                        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                                                        Canvas c=new Canvas(bitmap);
                                                        drawable.setBounds(0,0,c.getWidth(),c.getHeight());
                                                        drawable.draw(c);
                                                        fotos.add(bitmap);
                                                    }
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            setFotos();
                                                        }
                                                    });
                                                } catch (IOException | SVGParseException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        }).start();
                                    }
                                }
                            },
                            new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("Volley", "fallo de conexion");
                                }
                            });
                    requestQueue.add(stringRequest);
                    senal.setPosicionEnLista(index);
                    index++;
                    numRequests++;
                    senal.setText(senalList.get(i *3+j).nombre);

                    senal.setBackgroundColor(Color.TRANSPARENT);
                    senal.setTextColor(Color.BLACK);
                    senal.setTextSize(10);
                    senal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            senal_titulo.setText(senal.getText());
                            senal_info.setText(senal.getDescripcionSenal());
                            if(numRequests == 0){
                                int w=senales.get(2).getWidth();
                                int h=senales.get(2).getWidth();
                                int i = senal.getPosicionEnLista();
                                senal_imagen.setImageDrawable(senal.getCompoundDrawables()[1]);
                            }
                            senalDialog.show();
                        }
                    });
                    senal.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
                    row.addView(senal);
                    senales.add(senal);
                }
                parentLinearLayout.addView(row);
            }
        });
    }

    private void setFotos() {
            if (numRequests == 0) {
                int w=senales.get(2).getWidth();
                int h=senales.get(2).getWidth();
                for (int i = 0; i < fotos.size(); i++) {
                    double r=(double) fotos.get(i).getHeight()/(double) fotos.get(i).getWidth();

                    Drawable d= new BitmapDrawable(Bitmap.createScaledBitmap(fotos.get(i),  w, (int) (h*r),false));
                    if(!senales.get(i).isAprendida()){
                        d.setColorFilter(getColor(R.color.senal_bloqueada), PorterDuff.Mode.MULTIPLY);
                    }
                    senales.get(i).setCompoundDrawablesWithIntrinsicBounds(null, d, null, null);
                }
                findViewById(androidx.constraintlayout.widget.R.id.constraint).setVisibility(View.INVISIBLE);
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