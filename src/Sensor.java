public class Sensor{
  //Atriputos
  private String idSensor;
  private String marca;
  private String modelo;
  private double presicion;
  private EstadoOperativo estado;
  private LocalDate ultimaCalibracion;
  private List<Medicion> mediciones;

  //Constructor
  public Sensor(String idSensor, String marca, String modelo, double presicion, LocalDate ultimaCalibracion){
    this.idSensor = idSensor;
    this.marca = marca;
    this.modelo = modelo;
    this.presicion = presicion;
    this.estado = EstadoOperativo.OPERATIVO; //por defectp
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
    return idSensor;
  }
  public String getModelo(){
    return modelo;
  }
  public double getPresicion(){
    return presicion;
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
  public void setUltimaCalibracion(LocalData ultimaCalibracion){
    this.ultimaCalibracion = ultimaCalibracion;
  }
}
