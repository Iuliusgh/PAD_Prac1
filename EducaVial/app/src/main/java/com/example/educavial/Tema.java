package com.example.educavial;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tema {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public Tema() {};
    public String titulo;
    public String contenido;

    public Tema(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
    }

}


