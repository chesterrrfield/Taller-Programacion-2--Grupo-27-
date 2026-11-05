package reportes;

import estaciones.EstacionMeteorologica;
import estaciones.Medicion;
import estaciones.Sensor;
import enums.EstadoOperativo;
import java.util.List;

public class ReporteFallasMantenimiento implements GeneradorReporte {

    private List<EstacionMeteorologica> estaciones;

    //recibe por parametro la lista de estaciones de la red que se van a auditar
    public ReporteFallasMantenimiento(List<EstacionMeteorologica> estaciones) {
        this.estaciones = estaciones;
    }

    @Override
    public void generar() {
        if (estaciones == null || estaciones.isEmpty()) {
            System.out.println("No hay estaciones para auditar el estado operativo.");
            return;
        }

        System.out.println("==================================================");
        System.out.println("       INFORME DE FALLAS Y MANTENIMIENTO          ");
        System.out.println("==================================================");

        // 1.Detección de sensores inactivos (fuera de servicio o en mantenimiento)
        System.out.println("1. SENSORES FUERA DE SERVICIO O EN MANTENIMIENTO:");
        int inactivos = 0;
        for (EstacionMeteorologica e : estaciones) {
            for (Sensor s : e.getSensores()) {
                if (s.getEstado() != EstadoOperativo.OPERATIVO) {
                    inactivos++;
                    System.out.printf("   • [%s] en Estación '%s' - Estado: %s\n",
                            s.getIdSensor(), 
                            e.getNombre(), 
                            s.getEstado());
                }
            }
        }
        if (inactivos == 0) {
            System.out.println("   (Todos los sensores se encuentran actualmente OPERATIVOS)");
        }

        // 2.Detección de sensores descalibrados
        System.out.println("\n2. SENSORES QUE REQUIEREN CALIBRACIÓN (> 12 meses):");
        int descalibrados = 0;
        for (EstacionMeteorologica e : estaciones) {
            for (Sensor s : e.getSensores()) {
                //utiliza el método requiereCalibracion(), corregido previamente con toTotalMonths()
                if (s.requiereCalibracion()) {
                    descalibrados++;
                    System.out.printf("   • [%s] %s %s en Estación '%s' (Última calib.: %s)\n",
                            s.getIdSensor(), 
                            s.getMarca(), 
                            s.getModelo(), 
                            e.getNombre(),
                            (s.getUltimaCalibracion() != null ? s.getUltimaCalibracion() : "Sin fecha registrada"));
                }
            }
        }
        if (descalibrados == 0) {
            System.out.println("   (Todos los sensores tienen su calibración al día)");
        }

        // 3.auditoría de lecturas anómalas (variación > 100 mm)
        System.out.println("\n3. LECTURAS ANÓMALAS REGISTRADAS:");
        int anomalias = 0;
        for (EstacionMeteorologica e : estaciones) {
            for (Sensor s : e.getSensores()) {
                for (Medicion m : s.getMediciones()) {
                    //consulta la bandera booleana establecida durante la medicion
                    if (m.isAnomala()) {
                        anomalias++;
                        System.out.printf("   • Lectura [%s] en Sensor [%s] (Estación '%s'): %.2f mm el %s\n",
                                m.getIdMedicion(), 
                                s.getIdSensor(), 
                                e.getNombre(), 
                                m.getMilimetros(), 
                                m.getFechaHora().toString().substring(0, 16));
                    }
                }
            }
        }
        if (anomalias == 0) {
            System.out.println("   (No se detectaron mediciones anómalas en el registro histórico)");
        }

        System.out.println("==================================================\n");
    }
}