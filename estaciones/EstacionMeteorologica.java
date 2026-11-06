package estaciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import enums.EstadoOperativo;
import enums.TipoEstacion;
import geografia.ZonaGeografica;
import personas.ResponsableTecnico;


public class EstacionMeteorologica {
    //atributos
    private String idEstacion;
    private String nombre;
    private double latitud;
    private double longitud;
    private double altitud;
    private EstadoOperativo estado;
    private TipoEstacion tipo;
    
    //relaciones
    private ZonaGeografica zona;
    private ResponsableTecnico responsable;
    private List<Sensor> sensores;
    private List<Mantenimiento> mantenimientos;

    //constructor
    public EstacionMeteorologica(String idEstacion, String nombre, double latitud, double longitud, double altitud, TipoEstacion tipo, ZonaGeografica zona, ResponsableTecnico responsable) {
        this.idEstacion = idEstacion;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
        this.tipo = tipo;
        this.estado = EstadoOperativo.OPERATIVO; //estado por defecto
        this.zona = zona;
        this.responsable = responsable;
        this.sensores = new ArrayList<>();
        this.mantenimientos = new ArrayList<>();
    }

    // Metodos de comportamiento
    public void instalarSensor(Sensor s) {
        if (s != null && !sensores.contains(s)) {
            sensores.add(s);
        }
    }

    public void retirarSensor(Sensor s) {
        if (s != null) {
            sensores.remove(s);
        }
    }

    public boolean estaOperativa() {
        return this.estado == EstadoOperativo.OPERATIVO;
    }

    public void agregarMantenimiento(Mantenimiento m) {
        if (m != null && !mantenimientos.contains(m)) {
            mantenimientos.add(m);
        }
    }

    public double obtenerPromedioPrecipitaciones() {
        if (sensores == null || sensores.isEmpty()) {
            return 0.0;
        }
        double sumaTotal = 0.0;
        int cantidadMediciones = 0;

        for (Sensor sensor : sensores) {
            for (Medicion m : sensor.getMediciones()) {
                sumaTotal += m.getMilimetros();
                cantidadMediciones++;
            }
        }
        return cantidadMediciones == 0 ? 0.0 : sumaTotal / cantidadMediciones;
    }

    public Medicion obtenerUltimaMedicion() {
        Medicion ultima = null;
        for (Sensor sensor : sensores) {
            for (Medicion m : sensor.getMediciones()) {
                if (ultima == null || m.getFechaHora().isAfter(ultima.getFechaHora())) {
                    ultima = m;
                }
            }
        }
        return ultima;
    }

    public int contarSensoresDefectuosos() {
        int contador = 0;
        for (Sensor sensor : sensores) {
            if (sensor.getEstado() == EstadoOperativo.FUERA_DE_SERVICIO || sensor.requiereCalibracion()) {
                contador++;
            }
        }
        return contador;
    }

    public void cambiarEstado(EstadoOperativo nuevoEstado) {
        if (nuevoEstado != null) {
            this.estado = nuevoEstado;
        }
    }

    //getters y setters
    public String getIdEstacion() {
        return idEstacion;
    }

    public String getNombre() {
        return nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public double getAltitud() {
        return altitud;
    }

    public EstadoOperativo getEstado() {
        return estado;
    }

    public void setEstado(EstadoOperativo estado) {
        this.estado = estado;
    }

    public TipoEstacion getTipo() {
        return tipo;
    }

    public ZonaGeografica getZona() {
        return zona;
    }

    public void setZona(ZonaGeografica zona) {
        this.zona = zona;
    }

    public ResponsableTecnico getResponsable() {
        return responsable;
    }

    public void setResponsable(ResponsableTecnico responsable) {
        this.responsable = responsable;
    }

    public List<Sensor> getSensores() {
        return Collections.unmodifiableList(sensores);
    }
    
    public List<Mantenimiento> getMantenimientos() {
        return Collections.unmodifiableList(mantenimientos);
    }
    
    @Override
    public String toString() {
        return "Estacion: " + nombre +
                " | Estado: " + estado +
                " | Sensores defectuosos: " + contarSensoresDefectuosos() +
                " | Promedio Lluvia: " + obtenerPromedioPrecipitaciones() + " mm";
    }

}

