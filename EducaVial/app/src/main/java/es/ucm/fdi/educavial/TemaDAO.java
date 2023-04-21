package es.ucm.fdi.educavial;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TemaDAO {
    @Insert
    void insertAll(Tema... Temas);

    @Query("SELECT * FROM Tema")
    LiveData<List<Tema>> getAllTemas();
}
