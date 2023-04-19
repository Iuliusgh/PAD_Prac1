package es.ucm.fdi.educavial;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class Temaviewmodel extends AndroidViewModel {
    private TemaRepository repository;
    private LiveData<List<Tema>> Temalist;

    public Temaviewmodel(Application application){
        super(application);
        repository=new TemaRepository(application);
        Temalist= repository.GetAllTemas();
    }
    LiveData<List<Tema>> getTemalist(){
        return Temalist;
    }
    public void insertTema(Tema tema){
        repository.insert(tema);
    }

}
