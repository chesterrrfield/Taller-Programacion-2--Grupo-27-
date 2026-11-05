package reportes;

import alertas.Alerta;
import enums.NivelAlerta;
import java.time.LocalDateTime;
import java.util.List;

public class ReporteHistorialAlertas implements GeneradorReporte {

    private List<Alerta> alertas;
    private NivelAlerta nivelFiltro;
    private LocalDateTime desde;
    private LocalDateTime hasta;
    private String idZonaFiltro;

    //constructor que recibe todos los parametros de filtrado
    public ReporteHistorialAlertas(List<Alerta> alertas, NivelAlerta nivelFiltro, LocalDateTime desde, LocalDateTime hasta, String idZonaFiltro) {
        this.alertas = alertas;
        this.nivelFiltro = nivelFiltro;
        this.desde = desde;
        this.hasta = hasta;
        this.idZonaFiltro = idZonaFiltro;
    }

    @Override
    public void generar() {
        if (alertas == null || alertas.isEmpty()) {
            System.out.println("No hay alertas emitidas registradas en el sistema.");
            return;
        }

        System.out.println("==================================================");
        System.out.println("           HISTORIAL DE ALERTAS EMITIDAS          ");
        System.out.println("==================================================");

        int contador = 0;
        
        for (Alerta a : alertas) {
            //validaciones de los filtros opcionales
            boolean coincideNivel = (nivelFiltro == null || a.getNivel() == nivelFiltro);
            
            boolean coincideFecha = (desde == null || !a.getFechaHoraEmision().isBefore(desde)) &&
                                    (hasta == null || !a.getFechaHoraEmision().isAfter(hasta));
                                    
            boolean coincideZona = (idZonaFiltro == null || (a.getZona() != null && a.getZona().getIdZona().equalsIgnoreCase(idZonaFiltro)));

            //solo se imprime si cumple con todos los filtros aplicados
            if (coincideNivel && coincideFecha && coincideZona) {
                contador++;
                String equipoStr = a.getEquipoAsignado() != null ? a.getEquipoAsignado().getNombreEquipo() : "Sin asignar";
                String zonaNombre = a.getZona() != null ? a.getZona().getNombre() : "N/A";
                
                System.out.printf("[%s] Emisión: %s | Nivel: %-7s | Evento: %-15s | Zona: %-15s | Atendida: %s (%s)\n",
                        a.getIdAlerta(),
                        a.getFechaHoraEmision().toString().substring(0, 16),
                        a.getNivel(),
                        a.getEvento(),
                        zonaNombre,
                        (a.isAtendida() ? "SÍ" : "NO"),
                        equipoStr);
            }
        }

        if (contador == 0) {
            System.out.println("No se encontraron alertas que coincidan con los filtros aplicados.");
        }
        
        System.out.println("==================================================\n");
    }
}