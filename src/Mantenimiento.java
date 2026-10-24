import java.time.LocalDate;

public class Mantenimiento {
    // Atributos
    private String idMantenimiento;
    private LocalDate fecha;
    private TipoMantenimiento tipo;
    private String descripcion;
    // Relacion con ResponsableTecnico
    private List<ResponsableTecnico> responsablesTecnicos;
    // Relacion con EstacionMeteorologica
    private List<EstacionMeteorologica> estacionesMeteorologicas;
    // Relacion con Sensor
    private List<Sensor> sensores;


    // Constructor
    public Mantenimiento (String idMantenimiento, LocalDate fecha, TipoMantenimiento tipo, String descripcion) {
        this.idMantenimiento = idMantenimiento;
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }


    // Getters
    public String getIdMantenimiento() {
        return this.idMantenimiento;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public TipoMantenimiento getTipo() {
        return this.tipo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }
}
