package es.ucm.fdi.educavial;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SenalDAO {
    @Insert
    void insertAllSenal(Senal... senals);

    @Query("DELETE FROM senal")
    void deleteAllSenals();

    @Query("SELECT * FROM senal")
    LiveData<List<Senal>> GetAllSenals();

    @Query("SELECT * FROM senal WHERE codigo = :codigo")
    Senal getSenalBycodigo(String codigo);
    @Query("UPDATE senal SET aprendido = :valor WHERE codigo = :id")
    void updateValorBooleanoById(String id, boolean valor);



}


