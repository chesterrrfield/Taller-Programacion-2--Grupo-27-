import java.util.*;

public class ResponsableTecnico extends Persona {
    // Atributos
    private String especialidad;
    private String numeroLicencia;
    // Relacion de agregacion, es una referencia opcional
    private EquipoRespuesta equipo;
    // Relacion de composicion con EstacionMeteorologica
    private List<EstacionMeteorologica> estacionesACargo;
    // Relacion de composicion con EstacionMeteorologica
    private List<Mantenimiento> mantenimientosRealizados;

    // Constructor
    public ResponsableTecnico(String dni, String nombre, String apellido, String correoElectronico, String celular, String especialidad, String numeroLicencia, EquipoRespuesta equipo) {
        super(dni, nombre, apellido, correoElectronico, celular);
        this.especialidad = especialidad;
        this.numeroLicencia = numeroLicencia;
        this.equipo = equipo;
        this.estacionesACargo = new ArrayList<>();
        this.mantenimientosRealizados = new ArrayList<>();
    }

    // Getters
    public String getEspecialidad() {
        return this.especialidad;
    }

    public String getNumeroLicencia() {
        return this.numeroLicencia;
    }

    // Getter y Setter del atributo de la relacion para definir equipo
    public EquipoRespuesta getEquipo() { return equipo; }
    public void setEquipo(EquipoRespuesta equipo) { this.equipo = equipo; }
}
