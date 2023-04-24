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
    private boolean seInsertaronDatosIniciales;
    private static final String PREFS_NAME = "com.example.educavial.prefs";
    private static final String KEY_INSERTED_INITIAL_DATA = "inserted_initial_data";

    public Senalviewmodel(Application application){
        super(application);
        repository=new SenalRepository(application);
        seInsertaronDatosIniciales = getInsertedInitialDataFlag();
        insertarDatosIniciales();
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

    public Senal getSenalBycodigo(String codigo) {
        return repository.getSenalBycodigo(codigo);
    }
    public void updateValorBooleanoById(String codigo, boolean newValue) {
        repository.updateValorBooleanoById(codigo,newValue);
    }
    private void insertarDatosIniciales() {
        if(!seInsertaronDatosIniciales) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Insertar datos iniciales en la base de datos
                    repository.insert(new Senal("Ceda el paso", "Obligación para todo conductor de ceder el paso en la próxima intersección a los vehículos que circulen por la vía a la que se aproxime o al carril al que pretende incorporarse.",false,"R-1","rojo","triangulo"));
                    repository.insert(new Senal("STOP", "Obligación para todo conductor de detener su vehículo ante la  próxima línea de detención o,  si no existe, inmediatamente antes de la intersección,  y ceder el paso en ella a los vehículos que circulen por la vía a la que se aproxime.  Si, por circunstancias excepcionales,  desde el lugar donde se ha efectuado la detención no existe visibilidad suficiente,  el conductor deberá detenerse de nuevo en el lugar desde donde tenga visibilidad,  sin poner en peligro a ningún usuario de la vía.",false,"R-2","rojo","octagono"));
                    repository.insert(new Senal("Circulación prohibida", "Prohibida la circulación de toda clase de vehículos en ambos sentidos",false,"R-100","rojo","circulo"));
                    repository.insert(new Senal("Entrada prohibida", "prohíbe el acceso a toda clase de vehículos en una dirección determinada",false,"R-101","rojo","circulo"));
                    repository.insert(new Senal("Entrada Prohibida a Ciclomotores", "Señal de tráfico R-105 que prohíbe el acceso de ciclomotores a una vía determinada",false,"R-105","rojo","circulo"));
                    repository.insert(new Senal("Entrada prohibida a vehículos destinados al transporte de mercancías", "prohíbe el paso de vehículos destinados al transporte de mercancías a una vía determinada",false,"R-106","rojo","circulo"));
                    repository.insert(new Senal("Entrada prohibida a vehículos destinados al transporte", "Entrada prohibida a vehículos destinados al transporte de mercancías con mayor masa autorizada que la indicada",false,"R-107","rojo","circulo"));
                    repository.insert(new Senal("Entrada prohibida a vehículos que transporten mercancías peligrosas", "Señal de tráfico R-108 que prohíbe el paso de vehículos destinados al transporte de mercancías peligrosas",false,"R-108","rojo","circulo"));
                    repository.insert(new Senal("Entrada prohibida a vehículos agrícolas de motor", "Señal de tráfico R-111 que prohíbe la entrada de vehículos a motor dedicados a labores de agricultura",false,"R-111","rojo","circulo"));
                    repository.insert(new Senal("Entrada prohibida a vehículos de tracción animal", "Prohibición de acceso a vehículos de tracción animal.",false,"R-113","rojo","circulo"));
                    repository.insert(new Senal("Entrada prohibida a ciclos", "Prohibición de acceso a ciclos. ",false,"R-114","rojo","circulo"));
                    repository.insert(new Senal("Entrada prohibida a peatones ", "Prohibición de acceso a peatones. ",false,"R-116","rojo","circulo"));
                    repository.insert(new Senal("Entrada prohibida a animales de montura", "Prohibición de acceso a animales de montura. ",false,"R-117","rojo","circulo"));
                    repository.insert(new Senal("Prohibición de pasar sin detenerse", "Indica el lugar donde es obligatoria la detención por la \n proximidad, según la inscripción que contenga, de un puesto \n de aduana, de policía, de peaje u otro, tras los cuales pueden \n estar instalados medios mecánicos de detención. En todo \n caso, el conductor así detenido no podrá reanudar su marcha \n hasta haber cumplido la prescripción que la señal establece.  ",false,"R-200","rojo","circulo"));
                    repository.insert(new Senal("Limitación de masa ", "Prohibición de paso a los vehículos cuya masa en carga \n supere la indicada en toneladas.  \n",false,"R-201","rojo","circulo"));
                    repository.insert(new Senal("Limitación de altura", "Prohibición de paso a los vehículos cuya altura máxima, \n incluida la carga, supere la indicada.",false,"R-205","rojo","circulo"));
                    repository.insert(new Senal("Separación mínima", "Prohibición de circular sin mantener con el vehículo precedente\n una separación igual o mayor a la indicada en la señal, excepto\n cuando se vaya a efectuar la maniobra de adelantamiento. Si no \n aparece  ninguna  cifra  recuerda  de  forma  genérica  que  debe \n guardare  la  distancia  de  seguridad  entre  vehículos  establecida \n reglamentariamente. ",false,"R-300","rojo","circulo"));
                    repository.insert(new Senal("Velocidad máxima 30", "Prohibición de circular a velocidad superior, expresada en \n kilómetros por hora, a la indicada en la señal. Obliga desde el\n lugar en que esté situada hasta la próxima señal «Fin de \n limitación de velocidad», de «Fin de prohibiciones» u otra de \n «Velocidad máxima», salvo que esté colocada en el mismo poste \n que una señal de advertencia de peligro o en el mismo panel que\n esta, en cuyo caso la prohibición finaliza cuando termine el \n peligro señalado. Situada en una vía sin prioridad, deja de tener \n vigencia al salir de una intersección con una vía con prioridad. Si \n el límite indicado por la señal coincide con la velocidad máxima \n permitida para el tipo de vía, recuerda de forma genérica la \n prohibición de superarla",false,"R-301-30","rojo","circulo"));
                    repository.insert(new Senal("Velocidad máxima 50", "Prohibición de circular a velocidad superior, expresada en \n kilómetros por hora, a la indicada en la señal. Obliga desde el\n lugar en que esté situada hasta la próxima señal «Fin de \n limitación de velocidad», de «Fin de prohibiciones» u otra de \n «Velocidad máxima», salvo que esté colocada en el mismo poste \n que una señal de advertencia de peligro o en el mismo panel que\n esta, en cuyo caso la prohibición finaliza cuando termine el \n peligro señalado. Situada en una vía sin prioridad, deja de tener \n vigencia al salir de una intersección con una vía con prioridad. Si \n el límite indicado por la señal coincide con la velocidad máxima \n permitida para el tipo de vía, recuerda de forma genérica la \n prohibición de superarla",false,"R-301-50","rojo","circulo"));
                    repository.insert(new Senal("Velocidad máxima 70", "Prohibición de circular a velocidad superior, expresada en \n kilómetros por hora, a la indicada en la señal. Obliga desde el\n lugar en que esté situada hasta la próxima señal «Fin de \n limitación de velocidad», de «Fin de prohibiciones» u otra de \n «Velocidad máxima», salvo que esté colocada en el mismo poste \n que una señal de advertencia de peligro o en el mismo panel que\n esta, en cuyo caso la prohibición finaliza cuando termine el \n peligro señalado. Situada en una vía sin prioridad, deja de tener \n vigencia al salir de una intersección con una vía con prioridad. Si \n el límite indicado por la señal coincide con la velocidad máxima \n permitida para el tipo de vía, recuerda de forma genérica la \n prohibición de superarla",false,"R-301-70","rojo","circulo"));
                    repository.insert(new Senal("Velocidad máxima 70", "Prohibición de circular a velocidad superior, expresada en \n kilómetros por hora, a la indicada en la señal. Obliga desde el\n lugar en que esté situada hasta la próxima señal «Fin de \n limitación de velocidad», de «Fin de prohibiciones» u otra de \n «Velocidad máxima», salvo que esté colocada en el mismo poste \n que una señal de advertencia de peligro o en el mismo panel que\n esta, en cuyo caso la prohibición finaliza cuando termine el \n peligro señalado. Situada en una vía sin prioridad, deja de tener \n vigencia al salir de una intersección con una vía con prioridad. Si \n el límite indicado por la señal coincide con la velocidad máxima \n permitida para el tipo de vía, recuerda de forma genérica la \n prohibición de superarla. ",false,"R-301-70","rojo","circulo"));
                    repository.insert(new Senal("Velocidad máxima 90", "Prohibición de circular a velocidad superior, expresada en \n kilómetros por hora, a la indicada en la señal. Obliga desde el\n lugar en que esté situada hasta la próxima señal «Fin de \n limitación de velocidad», de «Fin de prohibiciones» u otra de \n «Velocidad máxima», salvo que esté colocada en el mismo poste \n que una señal de advertencia de peligro o en el mismo panel que\n esta, en cuyo caso la prohibición finaliza cuando termine el \n peligro señalado. Situada en una vía sin prioridad, deja de tener \n vigencia al salir de una intersección con una vía con prioridad. Si \n el límite indicado por la señal coincide con la velocidad máxima \n permitida para el tipo de vía, recuerda de forma genérica la \n prohibición de superarla. ",false,"R-301-90","rojo","circulo"));
                    repository.insert(new Senal("Velocidad máxima 100", "Prohibición de circular a velocidad superior, expresada en \n kilómetros por hora, a la indicada en la señal. Obliga desde el\n lugar en que esté situada hasta la próxima señal «Fin de \n limitación de velocidad», de «Fin de prohibiciones» u otra de \n «Velocidad máxima», salvo que esté colocada en el mismo poste \n que una señal de advertencia de peligro o en el mismo panel que\n esta, en cuyo caso la prohibición finaliza cuando termine el \n peligro señalado. Situada en una vía sin prioridad, deja de tener \n vigencia al salir de una intersección con una vía con prioridad. Si \n el límite indicado por la señal coincide con la velocidad máxima \n permitida para el tipo de vía, recuerda de forma genérica la \n prohibición de superarla. ",false,"R-301-100","rojo","circulo"));
                    repository.insert(new Senal("Velocidad máxima 120", "Prohibición de circular a velocidad superior, expresada en \n kilómetros por hora, a la indicada en la señal. Obliga desde el\n lugar en que esté situada hasta la próxima señal «Fin de \n limitación de velocidad», de «Fin de prohibiciones» u otra de \n «Velocidad máxima», salvo que esté colocada en el mismo poste \n que una señal de advertencia de peligro o en el mismo panel que\n esta, en cuyo caso la prohibición finaliza cuando termine el \n peligro señalado. Situada en una vía sin prioridad, deja de tener \n vigencia al salir de una intersección con una vía con prioridad. Si \n el límite indicado por la señal coincide con la velocidad máxima \n permitida para el tipo de vía, recuerda de forma genérica la \n prohibición de superarla. ",false,"R-301-120","rojo","circulo"));
                    repository.insert(new Senal("Giro a la derecha prohibido", "Prohibición de girar a la derecha",false,"R-302","rojo","circulo"));
                    repository.insert(new Senal("Giro a la izquierda prohibido", "Prohibición de girar a la izquierda. Incluye, también, la \n prohibición del cambio de sentido de marcha.",false,"R-303","rojo","circulo"));
                    repository.insert(new Senal("Cambio de sentido prohibido", "Prohibición de efectuar la maniobra de cambio de sentido de la \n marcha. ",false,"R-304","rojo","circulo"));
                    repository.insert(new Senal("Adelantamiento prohibido", "Por  añadidura  a  los  principios  generales  sobre  adelantamiento, \n indica  la  prohibición  a  todos  los  vehículos  de  adelantar  a  los \n vehículos a motor que circulen por la calzada cuando dicha maniobra \n implique  invadir  la  zona  reservada  al  sentido  contrario  de \n circulación",false,"R-305","rojo","circulo"));
                    repository.insert(new Senal("Parada y estacionamiento prohibido", "Prohibición de parada y estacionamiento en el lado de la calzada en \n que esté situada la señal. Salvo indicación en contrario, la \n prohibición comienza en la vertical de la señal y termina en la\n intersección más próxima en el sentido de la marcha. ",false,"R-307","azul","circulo"));
                    repository.insert(new Senal("Estacionamiento prohibido", "Prohibición de estacionamiento en el lado de la calzada en que esté \n situada la señal. Salvo indicación en contrario, la prohibición\n comienza en la vertical de la señal y termina en la intersección más \n próxima en el sentido de la marcha. No prohíbe la parada.",false,"R-308","azul","circulo"));
                    repository.insert(new Senal("Sentido obligatorio derecha", "La flecha señala la dirección y sentido que los vehículos \n tienen la obligación de seguir.",false,"R-400a","azul","circulo"));
                    repository.insert(new Senal("Sentido obligatorio izquierda", "La flecha señala la dirección y sentido que los vehículos \n tienen la obligación de seguir.",false,"R-400b","azul","circulo"));
                    repository.insert(new Senal("Sentido obligatorio recto", "La flecha señala la dirección y sentido que los vehículos \n tienen la obligación de seguir",false,"R-400c","azul","circulo"));
                    repository.insert(new Senal("Sentido obligatorio giro derecha", "La flecha señala la dirección y sentido que los vehículos \n tienen la obligación de seguir",false,"R-400d","azul","circulo"));
                    repository.insert(new Senal("Sentido obligatorio en glorieta", "Las flechas señalan la dirección y sentido del movimiento \n giratorio que los vehículos deben seguir",false,"R-402","azul","circulo"));
                    repository.insert(new Senal("Vía reservada y obligatoria para ciclos ", "Obligación para los conductores de ciclos de circular por la vía a \n cuya entrada esté situada y prohibición a los demás usuarios de la \n vía de utilizarla.",false,"R-407a","azul","circulo"));
                    repository.insert(new Senal("Velocidad mínima", "Obligación para los conductores de vehículos de circular, por lo \n menos, a la velocidad indicada por la cifra, en kilómetros por hora, \n que figure en la señal, desde el lugar en que esté situada hasta otra \n de velocidad mínima diferente, o de fin de velocidad mínima o de \n velocidad máxima de valor igual o inferior. ",false,"R-411","azul","circulo"));
                    repository.insert(new Senal("Alumbrado de corto alcance ", "Obligación para los conductores de circular con el alumbrado al\n menos de corto alcance, con independencia de las condiciones de\n visibilidad e iluminación de la vía, desde el lugar en que esté situada \n la señal hasta otra de fin de esta obligación. ",false,"R-413","azul","circulo"));
                    repository.insert(new Senal("Intersección con prioridad ", "Peligro por la proximidad de una intersección con una vía, cuyos \n usuarios deben ceder el paso. ",false,"P-1","rojo","triangulo"));
                    repository.insert(new Senal("Intersección con prioridad sobre vía a la derecha", "Peligro por la proximidad de una intersección con una vía a la \n derecha, cuyos usuarios deben ceder el paso. ",false,"P-1a","rojo","triangulo"));
                    repository.insert(new Senal("Intersección con prioridad sobre vía a la izquierda  ", "Peligro por la proximidad de una intersección con una vía a la \n izquierda, cuyos usuarios deben ceder el paso. ",false,"P-1b","rojo","triangulo"));
                    repository.insert(new Senal("Intersección con prioridad sobre incorporación por \n la derecha", "Peligro por la proximidad de una incorporación por la derecha de \n una vía, cuyos usuarios deben ceder el paso.",false,"P-1c","rojo","triangulo"));
                    repository.insert(new Senal("Semáforos", "Peligro por la proximidad de una intersección aislada o tramo con \n la circulación regulada por semáforos. ",false,"P-3","rojo","triangulo"));
                    repository.insert(new Senal("Glorieta", "Peligro por la proximidad de una glorieta con circulación en el\n sentido indicado por las flechas. ",false,"P-4","rojo","triangulo"));
                    repository.insert(new Senal("Curva peligrosa hacia la derecha ", "Peligro por la proximidad de una curva peligrosa hacia la derecha.",false,"P-13a","rojo","triangulo"));
                    repository.insert(new Senal("Curva peligrosa hacia la izquierda ", "Peligro por la proximidad de una curva peligrosa hacia la \n izquierda. ",false,"P-13b","rojo","triangulo"));
                    repository.insert(new Senal("Curvas peligrosas hacia la derecha", "Peligro por la proximidad de una sucesión de curvas peligrosas \n próximas entre sí, siendo la primera de ellas hacia la derecha.",false,"P-14a","rojo","triangulo"));
                    repository.insert(new Senal("Curvas peligrosas hacia la izquierda", "Peligro por la proximidad de una sucesión de curvas peligrosas \n próximas entre sí, siendo la primera de ellas hacia la izquierda. ",false,"P-14b","rojo","triangulo"));
                    repository.insert(new Senal("Perfil irregular ", "Peligro por la proximidad de un resalte o badén en la vía o \n pavimento en mal estado",false,"P-15","rojo","triangulo"));
                    repository.insert(new Senal("Resalte", "Peligro por la proximidad de un resalte en la vía. ",false,"P-15a","rojo","triangulo"));
                    repository.insert(new Senal("Estrechamiento de calzada ", "Peligro por la proximidad de una zona de la vía en la que se \n estrecha la calzada. También puede ser utilizada cuando se \n reduzca la anchura de los arcenes de la calzada. No se utilizará \n cuando, tras una reducción del número de carriles de la calzada, \n la anchura de los carriles restantes y de los arcenes no haya \n variado.  ",false,"P-17"));
                    repository.insert(new Senal("Pavimento deslizante ", "Peligro por la proximidad de una zona de la calzada cuyo \n pavimento puede resultar deslizante",false,"P-19"));
                    repository.insert(new Senal("Paso para peatones", "Peligro por la proximidad de uno o varios pasos para peatones ",false,"P-20"));
                    repository.insert(new Senal("Niños", "Peligro por la proximidad de un lugar frecuentado por niños, \n como una escuela, una zona de juegos, etc.",false,"P-21"));
                    repository.insert(new Senal("Paso de animales domésticos o ganado ", "Peligro por la proximidad de un lugar donde frecuentemente la \n vía puede ser atravesada por animales domésticos o ganado.",false,"P-23"));
                    repository.insert(new Senal("Paso de animales en libertad ", "Peligro por la proximidad de un lugar donde frecuentemente la \n vía puede ser atravesada por animales en libertad. ",false,"P-24"));
                    repository.insert(new Senal("Circulación en los dos sentidos ", "Peligro por la proximidad de un tramo con circulación en ambos \n sentidos",false,"P-25"));
                    repository.insert(new Senal("Desprendimiento ", "Peligro por la proximidad a una zona con desprendimientos \n frecuentes y la consiguiente posible presencia de obstáculos en la \n calzada. ",false,"P-26"));
                    repository.insert(new Senal("Muelle ", "Peligro debido a que la vía desemboca en un muelle o en una \n corriente de agua.",false,"P-27"));
                    repository.insert(new Senal("Presencia de hielo o nieve ", "Peligro por la proximidad de un tramo en el que frecuentemente,\n durante  la  época  invernal,  hay  presencia  de  hielo  o  nieve  y  los\n consiguientes peligros asociados. ",false,"P-34"));
                    repository.insert(new Senal("Otros peligros ", "Indica la proximidad de un peligro distinto de los advertidos por \n otras señales. ",false,"P-50"));
                    repository.insert(new Senal("Entrada prohibida a vehículos que transporten mercancías peligrosas", "Las flechas señalan la única dirección y sentidos que los\n vehículos pueden tomar",false,"R-403c"));
                    repository.insert(new Senal("Paso obligatorio", "La flecha señala el lado del refugio, de la isleta o del\n obstáculo por el que los vehículos han de pasar\n obligatoriamente.",false,"R-401a"));
                    repository.insert(new Senal("Únicas direcciones y sentidos permitidos", "Las flechas señalan las únicas direcciones y sentidos que los\n vehículos pueden tomar.",false,"R-403a"));
                    repository.insert(new Senal("Velocidad máxima - 20", "Prohibición de circular a velocidad superior, expresada en\n kilómetros por hora, a la indicada en la señal.",false,"R-301-20"));
                    repository.insert(new Senal("Velocidad máxima - 40", "Prohibición de circular a velocidad superior, expresada en\n kilómetros por hora, a la indicada en la señal.",false,"R-301-40"));
                    repository.insert(new Senal("Velocidad máxima - 60", "Prohibición de circular a velocidad superior, expresada en\n kilómetros por hora, a la indicada en la señal.",false,"R-301-60"));
                    repository.insert(new Senal("Velocidad máxima - 80", "Prohibición de circular a velocidad superior, expresada en\n kilómetros por hora, a la indicada en la señal.",false,"R-301-80"));
                    repository.insert(new Senal("Estacionamiento para minusválidos", "Indica el lugar donde está autorizado el estacionamiento de\n vehículos.",false,"S-17"));
                    repository.insert(new Senal("Velocidad máxima aconsejada", "Recomienda una velocidad de circulación, en kilómetros por hora,\n que se aconseja no sobrepasar aunque las condiciones\n meteorológicas y ambientales de la vía y de la circulación sean\n favorables. ",false,"S-7-80"));
                    repository.insert(new Senal("Velocidad máxima aconsejada", "Recomienda una velocidad de circulación, en kilómetros por hora,\n que se aconseja no sobrepasar aunque las condiciones\n meteorológicas y ambientales de la vía y de la circulación sean\n favorables. ",false,"S-7-40"));
                    repository.insert(new Senal("Área de descanso", "Indica la situación de un área de descanso.",false,"S-123"));
                    repository.insert(new Senal("Otros servicios", "Señal genérica para cualquier otro servicio, que se inscribirá en el\n recuadro blanco.",false,"S-122"));
                    repository.insert(new Senal("Campamento", "Indica la situación de un lugar donde está permitida la acampada.",false,"S-107"));
                    repository.insert(new Senal("Surtidor de carburante", "Indica la situación de un surtidor o estación de servicio de\n carburante.",false,"S-105"));
                    repository.insert(new Senal("Servicio de inspección técnica de vehículos", "Indica la situación de una estación de inspección técnica de\n vehículos.",false,"S-102"));
                    repository.insert(new Senal("Bifurcación hacia la izquierda en calzada de tres\n carriles", "Indica, en una calzada con tres carriles de circulación en el mismo\n sentido, que se producirá una bifurcación con cambio de dirección\n en el carril izquierdo.",false,"S-61a"));
                    repository.insert(new Senal("Bifurcación hacia la derecha en calzada de dos carriles", "Indica, en una calzada con dos carriles de circulación en el mismo\n sentido, que se producirá una bifurcación con cambio de dirección\n en el carril derecho",false,"S-60b"));
                    repository.insert(new Senal("Final de carril destinado a la circulación", "ndica, en una calzada de doble sentido de circulación con dos\n carriles de circulación en el sentido de la marcha, la desaparición del\n carril izquierdo y el cambio de carril preciso.",false,"S-52b"));
                    repository.insert(new Senal("Carriles reservados en función de la velocidad\n señalizada", "ndica, en una calzada de doble sentido de circulación, la ampliación\n de un carril por la derecha. El carril sobre el que está situada la señal\n de velocidad mínima solo podrá ser utilizado por los vehículos que\n circulen a velocidad igual o superior a la indicada, aunque si las\n circunstancias lo permiten deben circular por el carril de la derecha.",false,"S-50b"));
                    repository.insert(new Senal("Carriles reservados en función de la velocidad\n señalizada", "Indica, en una calzada de doble sentido de circulación, la ampliación\n de un carril por la izquierda que solo puede ser utilizado por los\n vehículos que circulen a velocidad igual o superior a la indicada,\n aunque si las circunstancias lo permiten deben circular por el carril de\n la derecha",false,"S-50a"));
                    repository.insert(new Senal("Panel de aproximación a salida (300 m)", "Indica, en una autopista o en una autovía, que la próxima salida está\n situada aproximadamente a 300 m. Si la salida fuera por la izquierda\n la diagonal sería descendente de izquierda a derecha y la señal se\n situaría a la izquierda de la calzada",false,"S-26a"));
                    repository.insert(new Senal("Panel de aproximación a salida (200 m)", "Indica, en una autopista o en una autovía, que la próxima salida está\n situada aproximadamente a 200 m. Si la salida fuera por la izquierda\n la diagonal sería descendente de izquierda a derecha y la señal se\n situaría a la izquierda de la calzada",false,"S-26b"));
                    repository.insert(new Senal("Panel de aproximación a salida (100 m)", "Indica, en una autopista o en una autovía, que la próxima salida está\n situada aproximadamente a 100 m. Si la salida fuera por la izquierda\n la diagonal sería descendente de izquierda a derecha y la señal se\n situaría a la izquierda de la calzada",false,"S-26c"));
                    repository.insert(new Senal("Cambio de sentido a distinto nivel", "Indica la proximidad de una salida a través de la cual se puede\n efectuar un cambio de sentido a distinto nivel.",false,"S-25"));
                    repository.insert(new Senal("Fin de obligación de alumbrado de corto alcance", "Indica el final de un tramo en el que es obligatorio el alumbrado al\n menos de cruce o corto alcance y recuerda la posibilidad de\n prescindir de este, siempre que no venga impuesto por\n circunstancias de visibilidad, horario o iluminación de la vía.",false,"S-24"));
                    repository.insert(new Senal("Cambio de sentido al mismo nivel", "Indica la proximidad de un lugar en el que se puede efectuar un\n cambio de sentido al mismo nivel.",false,"S-22"));
                    repository.insert(new Senal("Parada de autobuses", "Indica el lugar reservado para parada de autobuses.",false,"S-19"));
                    repository.insert(new Senal("Lugar reservado para taxis", "Indica el lugar reservado a la parada y al estacionamiento de taxis\n libres y en servicio. La inscripción de un número indica el número\n total de espacios reservados a este fin.",false,"S-18"));
                    repository.insert(new Senal("Estacionamiento", "Indica el lugar donde está autorizado el estacionamiento de\n vehículos. Una inscripción o un símbolo representando ciertas\n clases de vehículos indica que el estacionamiento está reservado a\n esas clases. Una inscripción con indicaciones de tiempo limita la\n duración del estacionamiento al señalado.",false,"S-17"));
                    repository.insert(new Senal("Calzada sin salida", "Indica que la calzada que figura en la señal con un recuadro en rojo\n no tiene salida.",false,"S-15a"));
                    repository.insert(new Senal("Situación de un paso para peatones", "Indica la situación de un paso para peatones.",false,"S-13"));
                    repository.insert(new Senal("Calzada de dos carriles de sentido único", "Indica que en la calzada de dos carriles que se prolonga en la\n dirección de las flechas los vehículos deben circular en el sentido\n indicado por estas, y que está prohibida la circulación en sentido\n contrario.",false,"S-11a"));
                    repository.insert(new Senal("Calzada de un carril de sentido único", "Indica que en la calzada de un carril que se prolonga en la\n dirección de la flecha los vehículos deben circular en el sentido\n indicado por esta, y que está prohibida la circulación en sentido\n contrario.",false,"S-11"));
                    repository.insert(new Senal("Túnel", "Indica el principio y eventualmente el nombre de un túnel, de un\n paso inferior o de un tramo de vía equiparado a túnel. Podrá\n llevar en su parte inferior la indicación de la longitud del túnel en\n metros.",false,"S-5"));
                    repository.insert(new Senal("Fin de autovía", "Indica el final de una autovía.",false,"S-2a"));
                    repository.insert(new Senal("Fin de autopista", "Indica el final de una autopista.",false,"S-2"));
                    repository.insert(new Senal("Autovía", "Indica el principio de una autovía y, por tanto, el lugar a partir del\n cual se aplican las reglas especiales de circulación en este tipo de\n vía. Puede indicar también el ramal de un nudo que conduce a\n una autovía.",false,"S-1a"));
                    repository.insert(new Senal("Autopista", "Indica el principio de una autopista y, por tanto, el lugar a partir\n del cual se aplican las reglas especiales de circulación en este tipo\n de vía. Puede indicar también el ramal de un nudo que conduce a\n una autopista",false,"S-1"));

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
