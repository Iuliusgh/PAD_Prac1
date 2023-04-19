package com.example.educavial;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SenalRepository {
    private SenalDAO Senaldao;
    private LiveData<List<Senal>> senal;
    SenalRepository(Application application){
        Database db= Database.getDatabase(application);
        Senaldao=db.SenalDAO();
        senal=Senaldao.GetAllSenals();
    }
    LiveData<List<Senal>> GetAllSenals(){
        return Senaldao.GetAllSenals();
    }
    void insert(Senal senal){
        new insertAsyncTask(Senaldao).execute(senal);
    }

    public void deleteAllSenals() {
        new SenalRepository.DeleteAllAsyncTask(Senaldao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Senal,Void,Void>{
        private SenalDAO taskDao;
        insertAsyncTask(SenalDAO Senaldao){
            taskDao=Senaldao;
        }
        @Override
        protected Void doInBackground(Senal... senal){
            taskDao.insertAllSenal(senal[0]);
            return null;
        }

    }


    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private SenalDAO taskDao;

        DeleteAllAsyncTask(SenalDAO senalDao) {
            taskDao = senalDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAllSenals();
            return null;
        }
    }

}
