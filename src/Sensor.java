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
  public Sensor()
