package reportes;

import estaciones.EstacionMeteorologica;
import estaciones.Medicion;
import estaciones.Sensor;
import personas.ResponsableTecnico;
import java.util.List;

public class ReporteFichaTecnica implements GeneradorReporte {

    private EstacionMeteorologica estacion;

    //recibe por parámetro la estación a la cual se le generará la ficha tecnica
    public ReporteFichaTecnica(EstacionMeteorologica estacion) {
        this.estacion = estacion;
    }

    @Override
    public void generar() {
        if (estacion == null) {
            System.out.println("Error: Estación meteorológica no especificada para generar la ficha técnica.");
            return;
        }

        ResponsableTecnico rt = estacion.getResponsable();

        System.out.println("==================================================");
        System.out.println("     FICHA TÉCNICA OPERATIVA DE ESTACIÓN          ");
        System.out.println("==================================================");
        System.out.println("ID Estación : " + estacion.getIdEstacion());
        System.out.println("Nombre      : " + estacion.getNombre());
        System.out.println("Tipo        : " + estacion.getTipo());
        System.out.println("Estado      : " + estacion.getEstado());
        System.out.printf("Ubicación   : Lat %.4f, Lon %.4f, Alt %.1f m\n",
                estacion.getLatitud(), estacion.getLongitud(), estacion.getAltitud());
        System.out.println("Zona        : " + (estacion.getZona() != null ? estacion.getZona().getNombre() : "Sin asignar"));
        System.out.println("--------------------------------------------------");
        System.out.println("RESPONSABLE TECNICO A CARGO:");
        
        if (rt != null) {
            System.out.println("  Nombre   : " + rt.getNombre() + " " + rt.getApellido());
            System.out.println("  DNI      : " + rt.getDni());
            System.out.println("  Licencia : " + rt.getNumeroLicencia());
            System.out.println("  Especial.: " + rt.getEspecialidad());
        } else {
            System.out.println("  Sin responsable tecnico asignado.");
        }
        
        System.out.println("--------------------------------------------------");
        System.out.println("SENSORES INSTALADOS (" + estacion.getSensores().size() + "):");
        
        for (Sensor s : estacion.getSensores()) {
            System.out.printf("  • Sensor [%s] %s %s | Estado: %s | Requiere Calib.: %s\n",
                    s.getIdSensor(), 
                    s.getMarca(), 
                    s.getModelo(), 
                    s.getEstado(), 
                    (s.requiereCalibracion() ? "SÍ" : "NO"));
            
            List<Medicion> mediciones = s.getMediciones();
            if (!mediciones.isEmpty()) {
                Medicion ultima = mediciones.get(mediciones.size() - 1);
                System.out.printf("   Ultima lectura: %.2f mm el %s (Anómala: %s)\n",
                        ultima.getMilimetros(), 
                        ultima.getFechaHora().toString().substring(0, 16), 
                        (ultima.isAnomala() ? "SÍ" : "NO"));
            } else {
                System.out.println("    Ultima lectura: Sin mediciones registradas.");
            }
        }
        System.out.println("==================================================\n");
    }
}