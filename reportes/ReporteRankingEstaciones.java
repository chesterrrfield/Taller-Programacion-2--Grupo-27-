package reportes;

import estaciones.EstacionMeteorologica;
import estaciones.Medicion;
import estaciones.Sensor;
import enums.EstadoOperativo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReporteRankingEstaciones implements GeneradorReporte {

    private List<EstacionMeteorologica> estaciones;
    private LocalDateTime desde;
    private LocalDateTime hasta;

    //constructor para evaluar todas las estaciones en un rango de fechas opcional
    public ReporteRankingEstaciones(List<EstacionMeteorologica> estaciones, LocalDateTime desde, LocalDateTime hasta) {
        this.estaciones = estaciones;
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    public void generar() {
        if (estaciones == null || estaciones.isEmpty()) {
            System.out.println("No hay estaciones registradas para evaluar el ranking.");
            return;
        }

        //clase interna temporal para facilitar el ordenamiento por volumen
        class EstacionVolumen {
            EstacionMeteorologica estacion;
            double totalMm;

            EstacionVolumen(EstacionMeteorologica e, double total) {
                this.estacion = e;
                this.totalMm = total;
            }
        }

        List<EstacionVolumen> ranking = new ArrayList<>();

        //calcular la volumetría de cada estación en el rango de fechas
        for (EstacionMeteorologica e : estaciones) {
            double totalEstacion = 0.0;
            
            for (Sensor s : e.getSensores()) {
                for (Medicion m : s.getMediciones()) {
                    LocalDateTime fh = m.getFechaHora();
                    //validar si la medición cae dentro del rango de fechas
                    boolean enRango = (desde == null || !fh.isBefore(desde)) &&
                                      (hasta == null || !fh.isAfter(hasta));
                    
                    if (enRango) {
                        totalEstacion += m.getMilimetros();
                    }
                }
            }
            
            ranking.add(new EstacionVolumen(e, totalEstacion));
        }

        //ordenamiento descendente utilizando expresiones lambda
        ranking.sort((a, b) -> Double.compare(b.totalMm, a.totalMm));

        //imprimir el reporte
        System.out.println("==================================================");
        System.out.println("   RANKING DE ESTACIONES POR VOLUMETRÍA DE LLUVIA");
        System.out.println("==================================================");
        
        int pos = 1;
        for (EstacionVolumen ev : ranking) {
            System.out.printf("%2d. [%s] %-25s | Total: %.2f mm | Estado: %s\n",
                    pos++, 
                    ev.estacion.getIdEstacion(), 
                    ev.estacion.getNombre(), 
                    ev.totalMm, 
                    ev.estacion.getEstado());
        }
        
        System.out.println("==================================================\n");
    }
}