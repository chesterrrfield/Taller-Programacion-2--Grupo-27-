public class ResponsableTecnico extends Persona {
    // Atributos
    private String especialidad;
    private String numeroLicencia;

    // Constructor
    public ResponsableTecnico (String dni, String nombre, String apellido, String correoElectronico, String celular, String especialidad, String numeroLicencia) {
        super(dni,nombre,apellido,correoElectronico,celular);
        this.especialidad = especialidad;
        this.numeroLicencia = numeroLicencia;
    }

    // Getters
    public String getEspecialidad() {
        return this.especialidad;
    }

    public String getNumeroLicencia() {
        return this.numeroLicencia;
    }
}
