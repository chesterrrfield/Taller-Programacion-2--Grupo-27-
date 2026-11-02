import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 *representa una zona geografica o cuenca hidrografica
 *contiene los umbrales de precipitación para la evaluación de riesgo y la emisión de alertas
 */
public class ZonaGeografica {

    private String idZona;
    private String nombre;
    private NivelVulnerabilidad nivelVulnerabilidad;
    private NivelAlerta nivelAlertaActual;

    //umbrales de lluvia acumulada en 24h
    private double umbralAmarilla;
    private double umbralNaranja;
    private double umbralRoja;

    //relacioness
    private List<EstacionMeteorologica> estaciones;
    private List<ValorHistorico> valoresHistoricos;
    private List<Alerta> alertasEmitidas;

    // Constructor parametrizado de ZonaGeografica
    public ZonaGeografica(String idZona, String nombre, NivelVulnerabilidad nivelVulnerabilidad,
                          double umbralAmarilla, double umbralNaranja, double umbralRoja) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.nivelVulnerabilidad = nivelVulnerabilidad;
        this.nivelAlertaActual = NivelAlerta.VERDE;
        this.umbralAmarilla = umbralAmarilla;
        this.umbralNaranja = umbralNaranja;
        this.umbralRoja = umbralRoja;

        this.estaciones = new ArrayList<>();
        this.valoresHistoricos = new ArrayList<>();
        this.alertasEmitidas = new ArrayList<>();
    }

    //calcula la cantidad total de lluvia acumulada en las ultimas 24 horas  sumando las mediciones de todos los sensores de las estaciones operativas de la zona
    public double lluviaAcumulada24h() {
        double total = 0.0;
        LocalDateTime limite24h = LocalDateTime.now().minusHours(24);

        for (EstacionMeteorologica estacion : estaciones) {
            if (estacion.estaOperativa()) {
                for (Sensor sensor : estacion.getSensores()) {
                    if (sensor.getEstado() == EstadoOperativo.OPERATIVO) {
                        for (Medicion medicion : sensor.getMediciones()) {
                            if (!medicion.getFechaHora().isBefore(limite24h)) {
                                total += medicion.getMilimetros();
                            }
                        }
                    }
                }
            }
        }
        return total;
    }

   //evalua la lluvia acumulada en 24h comparándola con los tres umbrales de la zona y determina el nivel de alerta correspondiente
    public NivelAlerta evaluarRiesgo() {
        double acumulado24h = lluviaAcumulada24h();
        if (acumulado24h >= umbralRoja) {
            return NivelAlerta.ROJA;
        } else if (acumulado24h >= umbralNaranja) {
            return NivelAlerta.NARANJA;
        } else if (acumulado24h >= umbralAmarilla) {
            return NivelAlerta.AMARILLA;
        } else {
            return NivelAlerta.VERDE;
        }
    }

    // Emite una alerta evaluando los umbrales de lluvia actualizados y registra la alerta en el sistema.
    // @param ev Tipo de evento climatico ocasionante
    // @return Objeto Alerta generado
    public Alerta emitirAlerta(TipoEventoClimatico ev) {
        NivelAlerta nivelEvaluado = evaluarRiesgo();
        this.nivelAlertaActual = nivelEvaluado;

        String idAlerta = "ALT-" + UUID.randomUUID().toString().substring(0, 8);
        String desc = "Alerta automatica por " + ev + " en zona " + nombre + " con nivel " + nivelEvaluado;

        Alerta nuevaAlerta = new Alerta(idAlerta, LocalDateTime.now(), nivelEvaluado, ev, desc);
        this.alertasEmitidas.add(nuevaAlerta);

        return nuevaAlerta;
    }

    //permite emitir manualmente una alerta especifica ingresada por un operador
    public Alerta emitirAlertaManual(TipoEventoClimatico ev, NivelAlerta nivel, String descripcion) {
        this.nivelAlertaActual = nivel;
        String idAlerta = "ALT-" + UUID.randomUUID().toString().substring(0, 8);

        Alerta nuevaAlerta = new Alerta(idAlerta, LocalDateTime.now(), nivel, ev, descripcion);
        this.alertasEmitidas.add(nuevaAlerta);

        return nuevaAlerta;
    }

    //metodos para asociar colecciones
    public void agregarEstacion(EstacionMeteorologica e) {
        if (e != null && !estaciones.contains(e)) {
            estaciones.add(e);
        }
    }

    public void agregarValorHistorico(int mes, double acumuladoMedio) {
        ValorHistorico nuevoValor = new ValorHistorico(mes, acumuladoMedio);
        valoresHistoricos.add(nuevoValor);
    }

    //getters y setters
    public String getIdZona() {
        return idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public NivelVulnerabilidad getNivelVulnerabilidad() {
        return nivelVulnerabilidad;
    }

    public NivelAlerta getNivelAlertaActual() {
        return nivelAlertaActual;
    }

    public double getUmbralAmarilla() {
        return umbralAmarilla;
    }

    public double getUmbralNaranja() {
        return umbralNaranja;
    }

    public double getUmbralRoja() {
        return umbralRoja;
    }

    public List<EstacionMeteorologica> getEstaciones() {
        return Collections.unmodifiableList(estaciones);
    }

    public List<ValorHistorico> getValoresHistoricos() {
        return Collections.unmodifiableList(valoresHistoricos);
    }

    public List<Alerta> getAlertasEmitidas() {
        return Collections.unmodifiableList(alertasEmitidas);
    }
}
