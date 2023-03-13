package es.ucm.fdi.googlebooksclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText mAutor, mTitle;
    public static TextView mResult;

    private RadioGroup mPrintType;
    private RecyclerView mRecyclerView;
    private static BooksResultListAdapter mAdapter;

    private static final String TAG = "MainActivity";
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Inicializando variables");
        Button buscar = findViewById(R.id.button);
        mAutor = findViewById(R.id.autor);
        mTitle = findViewById(R.id.titulo);
        mPrintType = findViewById(R.id.printType);
        mRecyclerView = findViewById(R.id.recyclerview);
        mResult = findViewById(R.id.textView);

        mAdapter = new BooksResultListAdapter(new ArrayList<BookInfo>(), this);
        /*
        try {
            URL a = new URL("https:www.google.es");
            List<BookInfo> b = Arrays.asList(new BookInfo("a", "b", a), new BookInfo("c", "d", a));
            updateBooksResultList(b);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
         */
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        Log.d(TAG, "Preparando mensaje de alerta");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_title);
        builder.setMessage(R.string.alert_message);
        builder.setPositiveButton(R.string.alert_acept, null);
        AlertDialog dialog = builder.create();


        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Boton de busqueda pulsado");
                if(mAutor.getText().toString().equals("") && mTitle.getText().toString().equals("")){
                    dialog.show();
                }else{
                    searchBooks(view);
                }
            }
        });

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(0) != null){
            loaderManager.initLoader(0,null, bookLoaderCallbacks);
        }

    }

    public void searchBooks(View view){
        Log.i(TAG, "Capturando informaciones de busqueda");
        mResult.setText(R.string.loading);
        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString());
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType());
        LoaderManager.getInstance(this)
                .restartLoader(0, queryBundle, bookLoaderCallbacks);
    }

    private String queryString(){
        String res = "";
        if(mAutor.getText().toString().equals("")){
            res = "intitle:" + mTitle.getText().toString();
        }
        else if(mTitle.getText().toString().equals("")){
            res = "inauthor:" + mAutor.getText().toString();
        }
        else{
            res = "inauthor:" + mAutor.getText().toString() + " intitle:" + mTitle.getText().toString();
        }
        return res;
    }

    private String printType(){
        String res = null;
        int id = mPrintType.getCheckedRadioButtonId();
        if(id == R.id.RBLibros){
            res = "BOOKS";
        }
        else if(id == R.id.RBRevistas){
            res = "MAGAZINES";
        }
        else{
            res = "ALL";
        }
        return res;
    }

    public static void updateBooksResultList(List<BookInfo> bookInfos){
        Log.i(TAG, "Actualizando informacion de RecyclerView");
        mAdapter.setBooksData(bookInfos);
        mAdapter.notifyDataSetChanged();
    }
}