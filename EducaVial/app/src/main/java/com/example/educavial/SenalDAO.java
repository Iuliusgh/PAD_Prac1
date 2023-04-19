package com.example.educavial;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SenalDAO {
    @Insert
    void insertAllSenal(Senal... senals);

    @Query("DELETE FROM senal")
    void deleteAllSenals();

    @Query("SELECT * FROM senal")
    LiveData<List<Senal>> GetAllSenals();
}


