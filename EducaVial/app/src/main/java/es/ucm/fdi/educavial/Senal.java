package es.ucm.fdi.educavial;
import androidx.room.*;
@Entity
public class Senal {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public Senal(){};
    protected String nombre;
    protected String descripcion;
    protected boolean aprendido;

    protected String codigo;

    public Senal(String nombre,String descripcion,boolean aprendido,String codigo){
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.aprendido=aprendido;
        this.codigo=codigo;
    }
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isAprendido() {
        return aprendido;
    }

    public String getCodigo() {
        return codigo;
    }


}

