package estaciones;

import java.time.LocalDateTime;

public class Medicion {

    //Difinicion de atributos
    private String idMedicion;
    private LocalDateTime fechaHora;
    private double milimetros;
    private boolean anomala;

    /**
     * Constructor completo para la clase Medicion
     *
     * @param idMedicion identificador unico de la medicion
     * @param fechaHora  fecha y hora en la que se registró la lectura
     * @param milimetros cantidad de precipitaciones en milimetros (mm)
     * @param anomala    indica si difiere en mas de 100 mm respecto a la lectura anterior
     */
    public Medicion(String idMedicion, LocalDateTime fechaHora, double milimetros, boolean anomala) {
        this.idMedicion = idMedicion;
        this.fechaHora = fechaHora;
        this.milimetros = milimetros;
        this.anomala = anomala;
    }

    //getters y setters
    public String getIdMedicion() {
        return idMedicion;
    }

    public void setIdMedicion(String idMedicion) {
        this.idMedicion = idMedicion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getMilimetros() {
        return milimetros;
    }

    public void setMilimetros(double milimetros) {
        this.milimetros = milimetros;
    }

    public boolean isAnomala() {
        return anomala;
    }

    public void setAnomala(boolean anomala) {
        this.anomala = anomala;
    }

    @Override
    public String toString() {
        return "Medicion{" +
                "idMedicion='" + idMedicion + '\'' +
                ", fechaHora=" + fechaHora +
                ", milimetros=" + milimetros +
                ", anomala=" + anomala +
                '}';
    }
}
