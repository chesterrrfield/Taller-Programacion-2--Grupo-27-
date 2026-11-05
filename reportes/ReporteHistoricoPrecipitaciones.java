package reportes;

import enums.EstadoOperativo;
import estaciones.EstacionMeteorologica;
import estaciones.Medicion;
import estaciones.Sensor;
import geografia.ValorHistorico;
import geografia.ZonaGeografica;
import java.time.LocalDateTime;

public class ReporteHistoricoPrecipitaciones implements GeneradorReporte {
    
    private ZonaGeografica zona;

    //el constructor recibe la zona a procesar
    public ReporteHistoricoPrecipitaciones(ZonaGeografica zona) {
        this.zona = zona;
    }

    @Override
    public void generar() {
        if (zona == null) {
            System.out.println("Error: Zona geografica no especificada para el reporte.");
            return;
        }

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicioDia = ahora.minusHours(24);
        LocalDateTime inicioSemana = ahora.minusDays(7);
        LocalDateTime inicioMes = ahora.minusDays(30);

        double acumuladoDiario = 0.0;
        double acumuladoSemanal = 0.0;
        double acumuladoMensual = 0.0;

        //recorrer las estaciones y sensores operativos de la zona
        for (EstacionMeteorologica estacion : zona.getEstaciones()) {
            if (estacion.estaOperativa()) {
                for (Sensor sensor : estacion.getSensores()) {
                    if (sensor.getEstado() == EstadoOperativo.OPERATIVO) {
                        for (Medicion m : sensor.getMediciones()) {
                            LocalDateTime fh = m.getFechaHora();
                            if (!fh.isBefore(inicioDia)) {
                                acumuladoDiario += m.getMilimetros();
                            }
                            if (!fh.isBefore(inicioSemana)) {
                                acumuladoSemanal += m.getMilimetros();
                            }
                            if (!fh.isBefore(inicioMes)) {
                                acumuladoMensual += m.getMilimetros();
                            }
                        }
                    }
                }
            }
        }

        //buscar el valor historico correspondiente al mes actual
        int mesActual = ahora.getMonthValue();
        double mediaHistorica = 0.0;
        for (ValorHistorico vh : zona.getValoresHistoricos()) {
            if (vh.getMes() == mesActual) {
                mediaHistorica = vh.getAcumuladoMedio();
                break;
            }
        }

        // Impresión del reporte por consola
        System.out.println("==================================================");
        System.out.println("   HISTÓRICO DE PRECIPITACIONES - ZONA: " + zona.getNombre());
        System.out.println("==================================================");
        System.out.printf("Acumulado Diario (ultimas 24h):   %.2f mm\n", acumuladoDiario);
        System.out.printf("Acumulado Semanal (ultimos 7 días): %.2f mm\n", acumuladoSemanal);
        System.out.printf("Acumulado Mensual (ultimos 30 días):%.2f mm\n", acumuladoMensual);
        
        if (mediaHistorica > 0) {
            System.out.printf("Media Historica del Mes (%d):        %.2f mm\n", mesActual, mediaHistorica);
            double dif = acumuladoMensual - mediaHistorica;
            System.out.printf("Diferencia respecto a la media:      %+.2f mm\n", dif);
        } else {
            System.out.println("Media Historica del Mes:             Sin registros historicos cargados.");
        }
        System.out.println("==================================================\n");
    }
}