package alertas;
import enums.NivelAlerta;
import enums.TipoEventoClimatico;
import geografia.ZonaGeografica;
import java.time.LocalDateTime;
import personas.EquipoRespuesta;


/**
 *Representa una alerta emitida ante situaciones climaticas criticas o superacion de umbrales
 */
public class Alerta {

    private String idAlerta;
    private LocalDateTime fechaHoraEmision;
    private NivelAlerta nivel;
    private TipoEventoClimatico evento;
    private String descripcion;
    private boolean atendida;
    private EquipoRespuesta equipoAsignado;
    private ZonaGeografica zona;

    /**
     * Constructor completo para registrar una nueva alerta
     */
    public Alerta(String idAlerta, LocalDateTime fechaHoraEmision, NivelAlerta nivel, 
                  TipoEventoClimatico evento, String descripcion, ZonaGeografica zona) {
        this.idAlerta = idAlerta;
        this.fechaHoraEmision = fechaHoraEmision;
        this.nivel = nivel;
        this.evento = evento;
        this.descripcion = descripcion;
        this.atendida = false;
        this.equipoAsignado = null;
        this.zona = zona;
    }

    /**
     * indica si el nivel de severidad de la alerta requiere un equipo de respuesta (niveles NARANJA o ROJA)
     */
    public boolean requiereEquipo() {
        return this.nivel != null && this.nivel.requiereEquipo();
    }

    /**
     * asigna un equipo de respuesta tecnica a la alerta para resolver la emergencia
     *
     * @param e equipo de respuesta asignado
     */
    public void asignarEquipo(EquipoRespuesta e) {
        if (e != null) {
            this.equipoAsignado = e;
            this.atendida = true;
            e.atenderAlerta(this); //registra la alerta en el equipo y lo marca como no disponible
        }
    }

    //getters y setters
    public String getIdAlerta() {
        return idAlerta;
    }

    public LocalDateTime getFechaHoraEmision() {
        return fechaHoraEmision;
    }

    public NivelAlerta getNivel() {
        return nivel;
    }

    public TipoEventoClimatico getEvento() {
        return evento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isAtendida() {
        return atendida;
    }

    public void setAtendida(boolean atendida) {
        this.atendida = atendida;
    }

    public EquipoRespuesta getEquipoAsignado() {
        return equipoAsignado;
    }
    
    public ZonaGeografica getZona() {
        return zona;
    }
    
    public void setZona(ZonaGeografica zona) {
            this.zona = zona;
    }


    @Override
    public String toString() {
        return "Alerta{" +
                "idAlerta='" + idAlerta + '\'' +
                ", fechaHoraEmision=" + fechaHoraEmision +
                ", nivel=" + nivel +
                ", evento=" + evento +
                ", atendida=" + atendida +
                ", equipo=" + (equipoAsignado != null ? equipoAsignado.getNombreEquipo() : "Sin asignar") +
                '}';
    }
}
