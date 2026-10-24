import java.time.LocalDate;

public class Mantenimiento {
    // Atributos
    private String idMantenimiento;
    private LocalDate fecha;
    private TipoMantenimiento tipo;
    private String descripcion;
    // Relacion con ResponsableTecnico
    private ResponsableTecnico responsableTecnico;
    // Relacion con EstacionMeteorologica
    private EstacionMeteorologica estacionMeteorologica;
    // Relacion con Sensor
    private Sensor sensor;


    // Constructor
    public Mantenimiento (String idMantenimiento, LocalDate fecha, TipoMantenimiento tipo, String descripcion) {
        this.idMantenimiento = idMantenimiento;
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }


    // Getters
    public String getIdMantenimiento() { return this.idMantenimiento; }
    public LocalDate getFecha() { return this.fecha; }
    public TipoMantenimiento getTipo() { return this.tipo; }
    public String getDescripcion() { return this.descripcion; }

     private ResponsableTecnico responsableTecnico;
    // Relacion con EstacionMeteorologica
    private EstacionMeteorologica estacionMeteorologica;
    // Relacion con Sensor
    private Sensor sensor;

    // Setter y Getter para ResponsableTecnico
    public ResponsableTecnico getResponsableTecnico() { return responsableTecnico; }
    public void setResponsableTecnico(ResponsableTecnico responsableTecnico) { this.responsableTecnico = responsableTecnico; }

    // Setter y Getter para EstacionMeteorologica
    public EstacionMeteorologica getEstacionMeteorologica() { return estacionMeteorologica; }
    public void setEstacionMeteorologica(EstacionMeteorologica estacionMeteorologica) { this.estacionMeteorologica = estacionMeteorologica; }
   
    // Setter y Getter para Sensor
    public Sensor getSensor() { return sensor; }
    public void setSensor(Sensor sensor) { this.sensor = sensor; }
}
