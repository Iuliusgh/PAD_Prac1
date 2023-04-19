package es.ucm.fdi.educavial;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class Senalviewmodel extends AndroidViewModel {
    private SenalRepository repository;
    private LiveData<List<Senal>> Senallist;

    public Senalviewmodel(Application application){
        super(application);
        repository=new SenalRepository(application);
        Senallist= repository.GetAllSenals();
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

}
