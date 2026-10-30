import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Sensor{
  //Atriputos
  private String idSensor;
  private String marca;
  private String modelo;
  private double precision;
  private EstadoOperativo estado;
  private LocalDate ultimaCalibracion;
  private List<Medicion> mediciones;

  //Constructor
  public Sensor(String idSensor, String marca, String modelo, double precision, LocalDate ultimaCalibracion){
    this.idSensor = idSensor;
    this.marca = marca;
    this.modelo = modelo;
    this.precision = precision;
    this.estado = EstadoOperativo.OPERATIVO; //por defecto
    this.ultimaCalibracion = ultimaCalibracion;
    this.mediciones = new ArrayList<>();
  }

  //validaciones
  public boolean validar(double mm) {
        return mm >= 0.0 && mm <= 500.0;
  }
  
  public boolean requiereCalibracion() {
        if (this.ultimaCalibracion == null) {
            return true;
        }
        Period periodo = Period.between(this.ultimaCalibracion, LocalDate.now());
        return periodo.getYears() >= 1 || periodo.getMonths() >= 12;
  }

  public Medicion registrar(double mm, LocalDateTime fh) throws LecturaInvalidaException {
        if (this.estado != EstadoOperativo.OPERATIVO) {
            throw new IllegalStateException("No se pueden registrar mediciones en un sensor " + this.estado);
        }
        if (!validar(mm)) {
            throw new LecturaInvalidaException("Valor de lectura invalido: " + mm + " mm. Debe estar entre 0 y 500 mm.");
        }
        boolean esAnomala = false;
        if (!mediciones.isEmpty()) {
            Medicion ultimaMedicion = mediciones.get(mediciones.size() - 1);
            double diferencia = Math.abs(mm - ultimaMedicion.getMilimetros());
            if (diferencia > 100.0) {
                esAnomala = true;
            }
        }
        String idMedicion = "MED-" + UUID.randomUUID().toString().substring(0, 8);
        Medicion nuevaMedicion = new Medicion(idMedicion, fh, mm, esAnomala);
        this.mediciones.add(nuevaMedicion);
        return nuevaMedicion;
  }
  
  
  //getters y setters
  public String getIdSensor(){
    return idSensor;
  }
  public String getMarca(){
    return marca;
  }
  public String getModelo(){
    return modelo;
  }
  public double getPrecision(){
    return precision;
  }
  public EstadoOperativo getEstado(){
    return estado;
  }
  public LocalDate getUltimaCalibracion(){
    return ultimaCalibracion;
  }
  public List<Medicion> getMediciones(){
    return Collections.unmodifiableList(mediciones);
  }

  
  public void setEstado(EstadoOperativo estado){
    this.estado = estado;
  }
  public void setUltimaCalibracion(LocalDate ultimaCalibracion){
    this.ultimaCalibracion = ultimaCalibracion;
  }
}
