package com.example.educavial;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@androidx.room.Database(entities = {User.class, Tema.class,Senal.class},version = 2)
public abstract class Database extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract TemaDAO temaDAO();
    public abstract SenalDAO SenalDAO();
    public static Database instance;
    public static Database getDatabase(final Context context){
        if(instance==null){
            synchronized (Database.class){
                if(instance==null){
                    instance= Room.databaseBuilder(context.getApplicationContext(), Database.class,"basededato").fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }

}
