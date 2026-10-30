import java.util.ArrayList;
import java.util.List;

public class EquipoRespuesta {
    // Atributos
    private String idEquipo;
    private String nombreEquipo;
    private boolean disponible;
    private List<ResponsableTecnico> integrantes = new ArrayList<>();
    private List<Alerta> alertasAtendidas = new ArrayList<>();

    // Constructor
    public EquipoRespuesta(String idEquipo, String nombreEquipo, boolean disponible) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.disponible = disponible;
    }

    // Getters y setters
    public String getIdEquipo() { return this.idEquipo; }
    public String getNombreEquipo() { return this.nombreEquipo; }
    public boolean getDisponible() { return this.disponible; }

    public void setDisponible(boolean disponible) { 
        this.disponible = disponible; 
    }


    // Metodo agregarIntegrante (r:ResponsableTecnico): void
    public void agregarIntegrante(ResponsableTecnico r) {
        this.integrantes.add(r);
    }
}
