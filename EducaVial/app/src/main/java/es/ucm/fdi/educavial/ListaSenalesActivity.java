package es.ucm.fdi.educavial;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ListaSenalesActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private Senalviewmodel viewModel;
    private SignalLoaderCallbacks signalLoaderCallbacks = new SignalLoaderCallbacks(this);
    private static final String BASE_URL =
            "https://en.wikipedia.org/w/api.php?";
    private static final String QUERY_PARAM = "action";
    private static final String TITLES = "titles";
    private static final String PROP = "prop";

    private static final String IIPROP = "iiprop";
    private static final String FORMAT = "format";
    private ArrayList<String> res = new ArrayList<String>();

    private int b[] = new int[1];

    private Button btn2;
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
        btn2 = findViewById(R.id.button2);
        LinearLayout parentLinearLayout=findViewById(R.id.lista);
        viewModel= ViewModelProviders.of(this).get(Senalviewmodel.class);
        viewModel.getSenallist().observe(this,senalList->{
            int tam=senalList.size();
            for (int i = 0; i <tam/3; i++){
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                for (int j=0;j<3;j++){
                    AppCompatButton senal =  new AppCompatButton(this);
                    String signalCode=senalList.get(i *3+j).codigo.toLowerCase().replaceFirst("-","");

                    //setImageFromAPI(senal, signalCode);


                    OkHttpClient client = new OkHttpClient();
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
                    urlBuilder.addQueryParameter(QUERY_PARAM, "query");
                    urlBuilder.addQueryParameter(TITLES, "File"+ Uri.decode(":")+"Spain_traffic_signal_"+signalCode+".svg");
                    urlBuilder.addQueryParameter(PROP, "imageinfo");
                    urlBuilder.addQueryParameter(IIPROP, "url");
                    urlBuilder.addQueryParameter(FORMAT, "json");
                    String urlWithQueryParams = urlBuilder.build().toString();
                    Request request = new Request.Builder()
                            .url(urlWithQueryParams).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Log.i("OkHttpClient", "fallo de conexion");
                        }

                        @Override
                        public void onResponse(Call call, final Response response)
                                throws IOException {
                            try {
                                String responseData = response.body().string();
                                Log.d("onResponse", responseData);
                                JSONObject jsonObject = new JSONObject(responseData);
                                JSONObject queryObject = jsonObject.getJSONObject("query");
                                JSONObject pagesObject = queryObject.getJSONObject("pages");
                                JSONObject menosUnoObject = pagesObject.getJSONObject("-1");
                                JSONArray imageinfoArray = (JSONArray) menosUnoObject.get("imageinfo");
                                JSONObject info = imageinfoArray.getJSONObject(0);
                                res.add(info.getString("url"));
                                Log.d("onResponse image", info.getString("url"));
                                b[0]++;


                                /*try {
                                    Bitmap bitmap = BitmapFactory.decodeStream(new URL(res).openConnection().getInputStream());
                                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                                    senal.setCompoundDrawablesWithIntrinsicBounds(null, drawable,null,null);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }*/
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                    senal.setText(senalList.get(i *3+j).nombre);
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
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(0) != null){
            loaderManager.initLoader(0,null, signalLoaderCallbacks);
        }

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(String a: res){
                    Log.d("Urls " + b[0], a);
                }


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

        Bundle queryBundle = new Bundle();
        queryBundle.putString(SignalLoaderCallbacks.EXTRA_QUERY, signalCode);
        LoaderManager.getInstance(this)
                .restartLoader(0, queryBundle, signalLoaderCallbacks);
    }
}