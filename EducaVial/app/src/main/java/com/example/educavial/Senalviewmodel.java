package com.example.educavial;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class Senalviewmodel extends AndroidViewModel {
    private SenalRepository repository;
    private LiveData<List<Senal>> Senallist;
    private boolean seInsertaronDatosIniciales = false;
    private static final String PREFS_NAME = "com.example.educavial.prefs";
    private static final String KEY_INSERTED_INITIAL_DATA = "inserted_initial_data";
    private static Senalviewmodel instance;

    public Senalviewmodel(Application application){
        super(application);
        repository=new SenalRepository(application);
        seInsertaronDatosIniciales = getInsertedInitialDataFlag();
        Senallist= repository.GetAllSenals();
        insertarDatosIniciales();
    }
    LiveData<List<Senal>> getSenallist(){
        return Senallist;
    }
    public void insertSenal(Senal senal){
        repository.insert(senal);
    }
    public void deleteAllSenals(){
        repository.deleteAllSenals();
    }
    public static Senalviewmodel getInstance(Application application) {
        if (instance == null) {
            instance = new Senalviewmodel(application);
        }
        return instance;
    }
    private void insertarDatosIniciales() {
        if(!seInsertaronDatosIniciales) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Insertar datos iniciales en la base de datos
                    repository.insert(new Senal("Ceda el paso", "Obligación para todo conductor de ceder el paso en la próxima intersección a los vehículos que circulen por la vía a la que se aproxime o al carril al que pretende incorporarse.",false,"R-1"));
                    repository.insert(new Senal("STOP", "Obligación para todo conductor de detener su vehículo ante la próxima línea de detención o, si no existe, inmediatamente antes de la intersección, y ceder el paso en ella a los vehículos que circulen por la vía a la que se aproxime. Si, por circunstancias excepcionales, desde el lugar donde se ha efectuado la detención no existe visibilidad suficiente, el conductor deberá detenerse de nuevo en el lugar desde donde tenga visibilidad, sin poner en peligro a ningún usuario de la vía.",false,"R-2"));
                    repository.insert(new Senal("Circulación prohibida", "Prohibida la circulación de toda clase de vehículos en ambos sentidos",false,"R-100"));
                    repository.insert(new Senal("Entrada prohibida", "prohíbe el acceso a toda clase de vehículos en una dirección determinada",false,"R-101"));
                    repository.insert(new Senal("Entrada Prohibida a Ciclomotores", "Señal de tráfico R-105 que prohíbe el acceso de ciclomotores a una vía determinada",false,"R-105"));
                    repository.insert(new Senal("Entrada prohibida a vehículos destinados al transporte de mercancías", "prohíbe el paso de vehículos destinados al transporte de mercancías a una vía determinada",false,"R-106"));
                    repository.insert(new Senal("Entrada prohibida a vehículos destinados al transporte", "Entrada prohibida a vehículos destinados al transporte de mercancías con mayor masa autorizada que la indicada",false,"R-107"));
                    repository.insert(new Senal("Entrada prohibida a vehículos que transporten mercancías peligrosas", "a",false,"R-2"));
                    repository.insert(new Senal("Tema 1", "a",false,"R-2"));
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
