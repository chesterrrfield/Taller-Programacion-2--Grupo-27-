import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class EstacionMeteorologica {
    private String idEstacion;
    private String nombre;
    private double latitud;
    private double longitud;
    private double altitud;
    private EstadoOperativo estado;
    private TipoEstacion tipo;

    // Relaciones del diagrama de clases
    private ResponsableTecnico responsable;
    private final List<Sensor> sensores;
    private final List<Mantenimiento> mantenimientos;

    public EstacionMeteorologica(String idEstacion, String nombre, double latitud, double longitud,
                                 double altitud, EstadoOperativo estado, TipoEstacion tipo, ResponsableTecnico responsable) {
        this.idEstacion = idEstacion;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
        this.estado = estado;
        this.tipo = tipo;
        this.responsable = responsable;
        this.sensores = new ArrayList<>();
        this.mantenimientos = new ArrayList<>();
    }

    public String getIdEstacion() { return this.idEstacion; }
    public String getNombre() { return this.nombre; }
    public double getLatitud() { return this.latitud; }
    public double getLongitud() { return this.longitud; }
    public double getAltitud() { return this.altitud; }
    public EstadoOperativo getEstado() { return this.estado; }
    public TipoEstacion getTipo() { return this.tipo; }

    // Getters y Setters de la Relacion
    public ResponsableTecnico getResponsable() { return responsable; }
    public void setResponsable(ResponsableTecnico responsable) { this.responsable = responsable; }

    public List<Sensor> getSensores() { return Collections.unmodifiableList(sensores); }
    public List<Mantenimiento> getMantenimientos() { return Collections.unmodifiableList(mantenimientos); }

    public void agregarMantenimiento(Mantenimiento mantenimiento) {
        if (mantenimiento != null) {
            this.mantenimientos.add(mantenimiento);
        }
    }

    // Metodos de la clase
    public void instalarSensor(Sensor s) {

    }

    public void retirarSensor(Sensor s) {

    }

    public boolean estaOperativa() {
        return this.estado == EstadoOperativo.OPERATIVO;
    }
}
