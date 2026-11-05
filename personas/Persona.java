package personas;

public abstract class Persona {
    // Atributos
    protected String dni;
    protected String nombre;
    protected String apellido;
    protected String correoElectronico;
    protected String celular;

    // Constructor parametrizado
    public Persona (String dni, String nombre, String apellido, String correoElectronico, String celular) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.celular = celular;
    }

    // Metodo de la clase
    public String getNombreCompleto() {
        return "Nombre Completo: "+ nombre + " "+apellido;
    }

    // Getters
    public String getDni() {
        return this.dni;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public String getCelular() {
        return this.celular;
    }
}
