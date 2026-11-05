package reportes;

import estaciones.EstacionMeteorologica;
import estaciones.Medicion;
import estaciones.Sensor;
import geografia.ValorHistorico;
import geografia.ZonaGeografica;

public class ReporteAnomaliaClimatica implements GeneradorReporte {

    private ZonaGeografica zona;
    private int mes;

    //recibe la zona a evaluar y el mes de referencia (1 al 12)
    public ReporteAnomaliaClimatica(ZonaGeografica zona, int mes) {
        this.zona = zona;
        this.mes = mes;
    }

    @Override
    public void generar() {
        if (zona == null) {
            System.out.println("Error: Zona geográfica no especificada para el reporte de anomalía climática.");
            return;
        }

        //obtener el valor histórico para el mes indicado
        double mediaHistorica = 0.0;
        for (ValorHistorico vh : zona.getValoresHistoricos()) {
            if (vh.getMes() == mes) {
                mediaHistorica = vh.getAcumuladoMedio();
                break;
            }
        }

        if (mediaHistorica == 0.0) {
            System.out.println("No se dispone de un valor histórico de referencia para el mes " + mes + " en la zona " + zona.getNombre());
            return;
        }

        //calcular el acumulado real registrado en ese mes para toda la zona
        double acumuladoRealMes = 0.0;
        for (EstacionMeteorologica e : zona.getEstaciones()) {
            for (Sensor s : e.getSensores()) {
                for (Medicion m : s.getMediciones()) {
                    if (m.getFechaHora() != null && m.getFechaHora().getMonthValue() == mes) {
                        acumuladoRealMes += m.getMilimetros();
                    }
                }
            }
        }

        //calcular el porcentaje de desviación
        double desviacionPorcentaje = ((acumuladoRealMes - mediaHistorica) / mediaHistorica) * 100.0;

        //imprimir el reporte
        System.out.println("==================================================");
        System.out.println("     ESTADÍSTICAS DE ANOMALÍA CLIMÁTICA          ");
        System.out.println("==================================================");
        System.out.println("Zona Geográfica  : " + zona.getNombre() + " (" + zona.getIdZona() + ")");
        System.out.println("Mes evaluado     : " + mes);
        System.out.printf("Acumulado Real   : %.2f mm\n", acumuladoRealMes);
        System.out.printf("Media Histórica  : %.2f mm\n", mediaHistorica);
        System.out.printf("Desviación       : %+.2f %%\n", desviacionPorcentaje);
        
        if (desviacionPorcentaje > 0) {
            System.out.println("Diagnóstico      : Superávit pluviométrico (Lluvias superiores al promedio histórico)");
        } else if (desviacionPorcentaje < 0) {
            System.out.println("Diagnóstico      : Déficit pluviométrico (Lluvias inferiores al promedio histórico)");
        } else {
            System.out.println("Diagnóstico      : Precipitación en norma alineada con la media histórica");
        }
        
        System.out.println("==================================================\n");
    }
}