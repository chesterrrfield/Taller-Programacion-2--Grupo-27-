package gestion;

import alertas.Alerta;
import enums.TipoEventoClimatico;
import estaciones.EstacionMeteorologica;
import estaciones.Sensor;
import excepciones.LecturaInvalidaException;
import geografia.ZonaGeografica;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import personas.EquipoRespuesta;

public class GestorMeteorologico {

    private List<ZonaGeografica> zonas;
    private List<EstacionMeteorologica> estaciones;
    private List<EquipoRespuesta> equiposRespuesta;
    private List<Sensor> sensoresTotales;

    public GestorMeteorologico() {
        this.zonas = new ArrayList<>();
        this.estaciones = new ArrayList<>();
        this.equiposRespuesta = new ArrayList<>();
        this.sensoresTotales = new ArrayList<>();
    }

    public void instalarSensorEnEstacion(Sensor sensor, EstacionMeteorologica estacion) {
        for (EstacionMeteorologica e : estaciones) {
            if (e.getSensores().contains(sensor)) {
                throw new IllegalStateException("El sensor ya está instalado en otra estación.");
            }
        }
        estacion.instalarSensor(sensor);
        if (!sensoresTotales.contains(sensor)) {
            sensoresTotales.add(sensor);
        }
    }

    // Devuelve la alerta si se generó una, o null si no se superaron los umbrales
    public Alerta registrarLectura(String idSensor, double mm) throws LecturaInvalidaException {
        Sensor sensorObjetivo = null;
        EstacionMeteorologica estacionDelSensor = null;

        for (EstacionMeteorologica e : estaciones) {
            for (Sensor s : e.getSensores()) {
                if (s.getIdSensor().equalsIgnoreCase(idSensor)) {
                    sensorObjetivo = s;
                    estacionDelSensor = e;
                    break;
                }
            }
        }

        if (sensorObjetivo == null) {
            throw new IllegalArgumentException("Sensor no encontrado en el sistema.");
        }

        if (!estacionDelSensor.estaOperativa()) {
            throw new IllegalStateException("La estación " + estacionDelSensor.getNombre() + " no está operativa.");
        }

        sensorObjetivo.registrar(mm, LocalDateTime.now());

        ZonaGeografica zona = estacionDelSensor.getZona();
        if (zona != null) {
            Alerta alerta = zona.emitirAlerta(TipoEventoClimatico.LLUVIA_COPIOSA);

            if (alerta.requiereEquipo()) {
                asignarEquipoDisponible(alerta);
            }
            return alerta;
        }

        return null;
    }

    private void asignarEquipoDisponible(Alerta alerta) {
        for (EquipoRespuesta equipo : equiposRespuesta) {
            if (equipo.getDisponible()) {
                alerta.asignarEquipo(equipo);
                return; // Asignación exitosa, termina la ejecución del método
            }
        }
        // Si termina el ciclo y no encontró, lanza excepción (sin usar printeos)
        throw new IllegalStateException("ALERTA CRÍTICA: No hay equipos de respuesta disponibles para asignar a la emergencia.");
    }

    public void agregarZona(ZonaGeografica z) { this.zonas.add(z); }
    public void agregarEstacion(EstacionMeteorologica e) { this.estaciones.add(e); }
    public void agregarEquipo(EquipoRespuesta eq) { this.equiposRespuesta.add(eq); }

    public List<EstacionMeteorologica> getEstaciones() { return estaciones; }
    public List<ZonaGeografica> getZonas() { return zonas; }
}