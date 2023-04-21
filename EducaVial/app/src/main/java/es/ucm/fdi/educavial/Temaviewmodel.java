package es.ucm.fdi.educavial;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class Temaviewmodel extends AndroidViewModel {
    private TemaRepository repository;
    private LiveData<List<Tema>> Temalist;
    private boolean seInsertaronDatosIniciales = false;
    private static final String PREFS_NAME = "com.example.educavial.prefs";
    private static final String KEY_INSERTED_INITIAL_DATA = "inserted_initial_data";
    private static Temaviewmodel instance;
    public static Temaviewmodel getInstance(Application application) {
        if (instance == null) {
            instance = new Temaviewmodel(application);
        }
        return instance;
    }
    public Temaviewmodel(Application application){
        super(application);
        repository=new TemaRepository(application);
        seInsertaronDatosIniciales = getInsertedInitialDataFlag();
        Temalist= repository.GetAllTemas();
         insertarDatosIniciales();
    }
    LiveData<List<Tema>> getTemalist(){
        return Temalist;
    }
    public void insertTema(Tema tema){
        repository.insert(tema);
    }
    private void insertarDatosIniciales() {
        if(!seInsertaronDatosIniciales) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Insertar datos iniciales en la base de datos
                    repository.insert(new Tema("Tema 1", "a"));
                    SharedPreferences prefs = getApplication().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(KEY_INSERTED_INITIAL_DATA, true);
                    editor.apply();
                    seInsertaronDatosIniciales = true;
                }
            }).start();
        }
    }
    private boolean getInsertedInitialDataFlag() {
        SharedPreferences prefs = getApplication().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_INSERTED_INITIAL_DATA, false);
    }

}
