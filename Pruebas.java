import alertas.Alerta;
import enums.NivelAlerta;
import enums.NivelVulnerabilidad;
import enums.TipoEstacion;
import enums.TipoEventoClimatico;
import estaciones.EstacionMeteorologica;
import estaciones.Sensor;
import geografia.ZonaGeografica;
import java.time.LocalDate;
import java.time.LocalDateTime;
import personas.EquipoRespuesta;
import personas.ResponsableTecnico;


public class Pruebas {
    public static void main (String[] args){

        System.out.println("=== INICIANDO JUEGO DE DATOS DE PRUEBA ===");
        
        //Zona Geografica con umbrales en milimetros (24 horas)
        ZonaGeografica zonaNorte = new ZonaGeografica(
            "ZN-01", 
            "Cuenca del Río Norte", 
            NivelVulnerabilidad.ALTO, 
            40.0,  //umbral Amarilla
            80.0,  //umbral Naranja
            120.0  //umbral Roja
        );

        System.out.println("Zona geografica creada: " + zonaNorte.getNombre());

        //creacion de Responsables Tecninos
        ResponsableTecnico tecnico1 = new ResponsableTecnico("25.724.213","Santiago","Silva","tanquesilva9@hotmail.com", "+54 9 11 8876-2405","Mantenimiento de Sensores","LIC-987654", null);

        ResponsableTecnico tecnico2 = new ResponsableTecnico("30.123.456", "David", "Quint", "david1905@email.com", "+54 9 11 2606-5678", "Hidrologia y Emergencias", "LIC-112233",null);

        //crear equipo de respuesta tecnica
        EquipoRespuesta equipoRescate = new EquipoRespuesta("EQ-01", "Brigada de Emergencia Fluvial", true);
        equipoRescate.agregarIntegrante(tecnico2);
        System.out.println("Equipo de respuesta creado: " + equipoRescate.getNombreEquipo());

        //crear estacion meteorologica asociada a la zona y al responsable
        EstacionMeteorologica estacion1 = new EstacionMeteorologica("EST-01", "Estacion Central Norte", -34.6037, -58.3816, 25.5, TipoEstacion.AUTOMATICA, zonaNorte, tecnico1
        );
        zonaNorte.agregarEstacion(estacion1);
        System.out.println("Estacion meteorologica instalada: " + estacion1.getNombre());

        //instalar un Sensor (Pluviometro) en la estacion
        Sensor sensor1 = new Sensor(
            "SEN-01", "Davis", "Vantage Pro 2", 
            0.2, LocalDate.now().minusMonths(6)
        );
        estacion1.instalarSensor(sensor1);
        System.out.println("Sensor instalado en estacion: " + sensor1.getIdSensor());

        // Prueba de metodos de comportamiento de Estacion Meteorologica
        Medicion ultima = estacion1.obtenerUltimaMedicion();
        if (ultima != null) {
            System.out.println("Última lectura: " + ultima.getMilimetros() + " mm el " + ultima.getFechaHora());
        } else {
            System.out.println("No hay mediciones registradas.");
        }
        
        estacion1.cambiarEstado(EstadoOperativo.EN_MANTENIMIENTO);

        //registro mediciones de prueba y verificar reglas de negocio
        try {
            System.out.println("\n--- Registrando lecturas de precipitación ---");
            sensor1.registrar(30.0, LocalDateTime.now().minusHours(5));
            sensor1.registrar(55.0, LocalDateTime.now().minusHours(1)); //acumulado total: 85 mm (supera el umbral Naranja de 80mm)

            double lluvia24h = zonaNorte.lluviaAcumulada24h();
            System.out.println("Lluvia acumulada en 24h en la zona: " + lluvia24h + " mm");

            NivelAlerta nivelRiesgo = zonaNorte.evaluarRiesgo();
            System.out.println("Nivel de riesgo evaluado: " + nivelRiesgo);

            //si el nivel requiere atencion o supera umbrales, emitimos alerta
            if (nivelRiesgo.requiereEquipo()) {
                Alerta alerta = zonaNorte.emitirAlerta(TipoEventoClimatico.LLUVIA_COPIOSA);
                System.out.println("¡Alerta generada!: " + alerta);

                //asignar equipo de respuesta si la alerta es naranja o roja
                alerta.asignarEquipo(equipoRescate);
                System.out.println("Equipo asignado exitosamente a la alerta. Estado del equipo disponible: " + equipoRescate.getDisponible());
            }

        } catch (Exception e) {
            System.out.println("Error durante la prueba de registros: " + e.getMessage());
        }

        // Mostrar todos los datos de la Estacion Meteorolgica
        System.out.println("\n"+estacion1);
    }
}
