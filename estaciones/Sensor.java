package estaciones;

import enums.EstadoOperativo;
import excepciones.LecturaInvalidaException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Sensor{
  //Atributos
  private String idSensor;
  private String marca;
  private String modelo;
  private double precision;
  private EstadoOperativo estado;
  private EstacionMeteorologica estacion;
  private LocalDate ultimaCalibracion;
  private List<Medicion> mediciones;


  //Constructor
  public Sensor(String idSensor, String marca, String modelo, double precision, LocalDate ultimaCalibracion){
    this.idSensor = idSensor;
    this.marca = marca;
    this.modelo = modelo;
    this.precision = precision;
    this.estado = EstadoOperativo.OPERATIVO; //por defecto
      this.estacion = estacion;
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
        return Period.between(this.ultimaCalibracion, LocalDate.now()).toTotalMonths() >=12;

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
    public void setEstacion(EstacionMeteorologica estacion){
        this.estacion = estacion;
    }
    public EstacionMeteorologica getEstacion(){
      return estacion;
    }

  
  public void setEstado(EstadoOperativo estado){
    this.estado = estado;
  }
  public void setUltimaCalibracion(LocalDate ultimaCalibracion){
    this.ultimaCalibracion = ultimaCalibracion;
  }
}
