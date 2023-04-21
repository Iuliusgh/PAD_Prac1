package es.ucm.fdi.educavial;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class Senalviewmodel extends AndroidViewModel {
    private SenalRepository repository;
    private LiveData<List<Senal>> Senallist;
    private boolean seInsertaronDatosIniciales = false;
    private static final String PREFS_NAME = "com.example.educavial.prefs";
    private static final String KEY_INSERTED_INITIAL_DATA = "inserted_initial_data";
    private static Senalviewmodel instance;

    public Senalviewmodel(Application application){
        super(application);
        repository=new SenalRepository(application);
        seInsertaronDatosIniciales = getInsertedInitialDataFlag();
        Senallist= repository.GetAllSenals();
        insertarDatosIniciales();
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
    public static Senalviewmodel getInstance(Application application) {
        if (instance == null) {
            instance = new Senalviewmodel(application);
        }
        return instance;
    }
    private void insertarDatosIniciales() {
        if(!seInsertaronDatosIniciales) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Insertar datos iniciales en la base de datos
                    repository.insert(new Senal("Ceda el paso", "Obligación para todo conductor de ceder el paso en la próxima intersección a los vehículos que circulen por la vía a la que se aproxime o al carril al que pretende incorporarse.",false,"R-1"));
                    repository.insert(new Senal("STOP", "Obligación para todo conductor de detener su vehículo ante la próxima línea de detención o, si no existe, inmediatamente antes de la intersección, y ceder el paso en ella a los vehículos que circulen por la vía a la que se aproxime. Si, por circunstancias excepcionales, desde el lugar donde se ha efectuado la detención no existe visibilidad suficiente, el conductor deberá detenerse de nuevo en el lugar desde donde tenga visibilidad, sin poner en peligro a ningún usuario de la vía.",false,"R-2"));
                    repository.insert(new Senal("Circulación prohibida", "Prohibida la circulación de toda clase de vehículos en ambos sentidos",false,"R-100"));
                    repository.insert(new Senal("Entrada prohibida", "prohíbe el acceso a toda clase de vehículos en una dirección determinada",false,"R-101"));
                    repository.insert(new Senal("Entrada Prohibida a Ciclomotores", "Señal de tráfico R-105 que prohíbe el acceso de ciclomotores a una vía determinada",false,"R-105"));
                    repository.insert(new Senal("Entrada prohibida a vehículos destinados al transporte de mercancías", "prohíbe el paso de vehículos destinados al transporte de mercancías a una vía determinada",false,"R-106"));
                    repository.insert(new Senal("Entrada prohibida a vehículos destinados al transporte", "Entrada prohibida a vehículos destinados al transporte de mercancías con mayor masa autorizada que la indicada",false,"R-107"));
                    repository.insert(new Senal("Entrada prohibida a vehículos que transporten mercancías peligrosas", "Las flechas señalan la única dirección y sentidos que los\n" +
                            "vehículos pueden tomar",false,"R-403c"));
                    repository.insert(new Senal("Única dirección y sentidos permitidos ", "a",false,"R-2"));
                    repository.insert(new Senal("Paso obligatorio", "La flecha señala el lado del refugio, de la isleta o del\n" +
                            "obstáculo por el que los vehículos han de pasar\n" +
                            "obligatoriamente.",false,"R-401a"));
                    repository.insert(new Senal("Únicas direcciones y sentidos permitidos", "Las flechas señalan las únicas direcciones y sentidos que los\n" +
                            "vehículos pueden tomar.",false,"R-403a"));
                    repository.insert(new Senal("Velocidad máxima - 20", "Prohibición de circular a velocidad superior, expresada en\n" +
                            "kilómetros por hora, a la indicada en la señal.",false,"R-301-20"));
                    repository.insert(new Senal("Velocidad máxima - 40", "Prohibición de circular a velocidad superior, expresada en\n" +
                            "kilómetros por hora, a la indicada en la señal.",false,"R-301-40"));
                    repository.insert(new Senal("Velocidad máxima - 60", "Prohibición de circular a velocidad superior, expresada en\n" +
                            "kilómetros por hora, a la indicada en la señal.",false,"R-301-60"));
                    repository.insert(new Senal("Velocidad máxima - 80", "Prohibición de circular a velocidad superior, expresada en\n" +
                            "kilómetros por hora, a la indicada en la señal.",false,"R-301-80"));
                    repository.insert(new Senal("Estacionamiento para minusválidos", "Indica el lugar donde está autorizado el estacionamiento de\n" +
                            "vehículos.",false,"S-17"));
                    repository.insert(new Senal("Velocidad máxima aconsejada", "Recomienda una velocidad de circulación, en kilómetros por hora,\n" +
                            "que se aconseja no sobrepasar aunque las condiciones\n" +
                            "meteorológicas y ambientales de la vía y de la circulación sean\n" +
                            "favorables. ",false,"S-7-80"));
                    repository.insert(new Senal("Velocidad máxima aconsejada", "Recomienda una velocidad de circulación, en kilómetros por hora,\n" +
                            "que se aconseja no sobrepasar aunque las condiciones\n" +
                            "meteorológicas y ambientales de la vía y de la circulación sean\n" +
                            "favorables. ",false,"S-7-40"));
                    repository.insert(new Senal("Señal desconocida", "Señal desconocida",false,"Unknown"));
                    repository.insert(new Senal("Área de descanso", "Indica la situación de un área de descanso.",false,"S-123"));
                    repository.insert(new Senal("Otros servicios", "Señal genérica para cualquier otro servicio, que se inscribirá en el\n" +
                            "recuadro blanco.",false,"S-122"));
                    repository.insert(new Senal("Campamento", "Indica la situación de un lugar donde está permitida la acampada.",false,"S-107"));
                    repository.insert(new Senal("Surtidor de carburante", "Indica la situación de un surtidor o estación de servicio de\n" +
                            "carburante.",false,"S-105"));
                    repository.insert(new Senal("Servicio de inspección técnica de vehículos", "Indica la situación de una estación de inspección técnica de\n" +
                            "vehículos.",false,"S-102"));
                    repository.insert(new Senal("Bifurcación hacia la izquierda en calzada de tres\n" +
                            "carriles", "Indica, en una calzada con tres carriles de circulación en el mismo\n" +
                            "sentido, que se producirá una bifurcación con cambio de dirección\n" +
                            "en el carril izquierdo.",false,"S-61a"));
                    repository.insert(new Senal("Bifurcación hacia la derecha en calzada de dos carriles", "Indica, en una calzada con dos carriles de circulación en el mismo\n" +
                            "sentido, que se producirá una bifurcación con cambio de dirección\n" +
                            "en el carril derecho",false,"S-60b"));
                    repository.insert(new Senal("Final de carril destinado a la circulación", "ndica, en una calzada de doble sentido de circulación con dos\n" +
                            "carriles de circulación en el sentido de la marcha, la desaparición del\n" +
                            "carril izquierdo y el cambio de carril preciso.",false,"S-52b"));
                    repository.insert(new Senal("Carriles reservados en función de la velocidad\n" +
                            "señalizada", "ndica, en una calzada de doble sentido de circulación, la ampliación\n" +
                            "de un carril por la derecha. El carril sobre el que está situada la señal\n" +
                            "de velocidad mínima solo podrá ser utilizado por los vehículos que\n" +
                            "circulen a velocidad igual o superior a la indicada, aunque si las\n" +
                            "circunstancias lo permiten deben circular por el carril de la derecha.",false,"S-50b"));
                    repository.insert(new Senal("Carriles reservados en función de la velocidad\n" +
                            "señalizada", "Indica, en una calzada de doble sentido de circulación, la ampliación\n" +
                            "de un carril por la izquierda que solo puede ser utilizado por los\n" +
                            "vehículos que circulen a velocidad igual o superior a la indicada,\n" +
                            "aunque si las circunstancias lo permiten deben circular por el carril de\n" +
                            "la derecha",false,"S-50a"));
                    repository.insert(new Senal("Panel de aproximación a salida (300 m)", "Indica, en una autopista o en una autovía, que la próxima salida está\n" +
                            "situada aproximadamente a 300 m. Si la salida fuera por la izquierda\n" +
                            "la diagonal sería descendente de izquierda a derecha y la señal se\n" +
                            "situaría a la izquierda de la calzada",false,"S-26a"));
                    repository.insert(new Senal("Panel de aproximación a salida (200 m)", "Indica, en una autopista o en una autovía, que la próxima salida está\n" +
                            "situada aproximadamente a 200 m. Si la salida fuera por la izquierda\n" +
                            "la diagonal sería descendente de izquierda a derecha y la señal se\n" +
                            "situaría a la izquierda de la calzada",false,"S-26b"));
                    repository.insert(new Senal("Panel de aproximación a salida (100 m)", "Indica, en una autopista o en una autovía, que la próxima salida está\n" +
                            "situada aproximadamente a 100 m. Si la salida fuera por la izquierda\n" +
                            "la diagonal sería descendente de izquierda a derecha y la señal se\n" +
                            "situaría a la izquierda de la calzada",false,"S-26c"));
                    repository.insert(new Senal("Cambio de sentido a distinto nivel", "Indica la proximidad de una salida a través de la cual se puede\n" +
                            "efectuar un cambio de sentido a distinto nivel.",false,"S-25"));
                    repository.insert(new Senal("Fin de obligación de alumbrado de corto alcance", "Indica el final de un tramo en el que es obligatorio el alumbrado al\n" +
                            "menos de cruce o corto alcance y recuerda la posibilidad de\n" +
                            "prescindir de este, siempre que no venga impuesto por\n" +
                            "circunstancias de visibilidad, horario o iluminación de la vía.",false,"S-24"));
                    repository.insert(new Senal("Cambio de sentido al mismo nivel", "Indica la proximidad de un lugar en el que se puede efectuar un\n" +
                            "cambio de sentido al mismo nivel.",false,"S-22"));
                    repository.insert(new Senal("Parada de autobuses", "Indica el lugar reservado para parada de autobuses.",false,"S-19"));
                    repository.insert(new Senal("Lugar reservado para taxis", "Indica el lugar reservado a la parada y al estacionamiento de taxis\n" +
                            "libres y en servicio. La inscripción de un número indica el número\n" +
                            "total de espacios reservados a este fin.",false,"S-18"));
                    repository.insert(new Senal("Estacionamiento", "Indica el lugar donde está autorizado el estacionamiento de\n" +
                            "vehículos. Una inscripción o un símbolo representando ciertas\n" +
                            "clases de vehículos indica que el estacionamiento está reservado a\n" +
                            "esas clases. Una inscripción con indicaciones de tiempo limita la\n" +
                            "duración del estacionamiento al señalado.",false,"S-17"));
                    repository.insert(new Senal("Calzada sin salida", "Indica que la calzada que figura en la señal con un recuadro en rojo\n" +
                            "no tiene salida.",false,"S-15a"));
                    repository.insert(new Senal("Situación de un paso para peatones", "Indica la situación de un paso para peatones.",false,"S-13"));
                    repository.insert(new Senal("Calzada de dos carriles de sentido único", "Indica que en la calzada de dos carriles que se prolonga en la\n" +
                            "dirección de las flechas los vehículos deben circular en el sentido\n" +
                            "indicado por estas, y que está prohibida la circulación en sentido\n" +
                            "contrario.",false,"S-11a"));
                    repository.insert(new Senal("Calzada de un carril de sentido único", "Indica que en la calzada de un carril que se prolonga en la\n" +
                            "dirección de la flecha los vehículos deben circular en el sentido\n" +
                            "indicado por esta, y que está prohibida la circulación en sentido\n" +
                            "contrario.",false,"S-11"));
                    repository.insert(new Senal("Túnel", "Indica el principio y eventualmente el nombre de un túnel, de un\n" +
                            "paso inferior o de un tramo de vía equiparado a túnel. Podrá\n" +
                            "llevar en su parte inferior la indicación de la longitud del túnel en\n" +
                            "metros.",false,"S-5"));
                    repository.insert(new Senal("Fin de autovía", "Indica el final de una autovía.",false,"S-2a"));
                    repository.insert(new Senal("Fin de autopista", "Indica el final de una autopista.",false,"S-2"));
                    repository.insert(new Senal("Autovía", "Indica el principio de una autovía y, por tanto, el lugar a partir del\n" +
                            "cual se aplican las reglas especiales de circulación en este tipo de\n" +
                            "vía. Puede indicar también el ramal de un nudo que conduce a\n" +
                            "una autovía.",false,"S-1a"));
                    repository.insert(new Senal("Autopista", "Indica el principio de una autopista y, por tanto, el lugar a partir\n" +
                            "del cual se aplican las reglas especiales de circulación en este tipo\n" +
                            "de vía. Puede indicar también el ramal de un nudo que conduce a\n" +
                            "una autopista",false,"S-1"));

                    SharedPreferences prefs = getApplication().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(KEY_INSERTED_INITIAL_DATA, true);
                    editor.apply();
                    seInsertaronDatosIniciales = true;
                }
            }).start();
        }
    }
    private boolean getInsertedInitialDataFlag() {
        SharedPreferences prefs = getApplication().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_INSERTED_INITIAL_DATA, false);
    }
}
