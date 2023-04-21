package com.example.educavial;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TemaRepository {
    private TemaDAO Temadao;
    private LiveData<List<Tema>> temas;
    TemaRepository(Application application){
        Database db= Database.getDatabase(application);
        Temadao=db.temaDAO();
        temas=Temadao.getAllTemas();
    }
    LiveData<List<Tema>> GetAllTemas(){
        return Temadao.getAllTemas();
    }
    void insert(Tema tema){
        new insertAsyncTask(Temadao).execute(tema);
    }

    private static class insertAsyncTask extends AsyncTask<Tema,Void,Void> {
        private TemaDAO taskDao;
        insertAsyncTask(TemaDAO temadao){
            taskDao=temadao;
        }
        @Override
        protected Void doInBackground(Tema... tema){
            taskDao.insertAll(tema[0]);
            return null;
        }

    }

}
