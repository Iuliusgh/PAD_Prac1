package es.ucm.fdi.educavial;
import androidx.room.*;
@Entity
public class Senal {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public Senal(){};
    public String nombre;
    public String descripcion;
    public boolean aprendido;

    public Senal(String nombre,String descripcion){
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.aprendido=false;
    }

}

