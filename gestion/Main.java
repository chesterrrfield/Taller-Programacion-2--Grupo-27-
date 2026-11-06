//Clase main temporal para controlar y verificar la gestion del programa
package gestion;

import alertas.Alerta;
import enums.NivelVulnerabilidad;
import enums.TipoEstacion;
import estaciones.EstacionMeteorologica;
import estaciones.Sensor;
import excepciones.LecturaInvalidaException;
import geografia.ZonaGeografica;
import personas.EquipoRespuesta;
import personas.ResponsableTecnico;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestorMeteorologico gestor = new GestorMeteorologico();
        inicializarDatosPrueba(gestor);

        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        System.out.println("Bienvenido al Sistema de Gestión Meteorológica");

        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Registrar lectura de precipitación");
            System.out.println("2. Ver listado de Sensores y Estaciones");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Ingrese el ID del Sensor (ej. SENS-1): ");
                    String idSensor = scanner.nextLine();
                    System.out.print("Ingrese la lectura en milímetros (mm): ");

                    try {
                        double mm = Double.parseDouble(scanner.nextLine());

                        // Llamamos al gestor. Si devuelve una alerta, la procesamos visualmente
                        Alerta alertaGenerada = gestor.registrarLectura(idSensor, mm);

                        System.out.println("✅ Lectura registrada con éxito.");

                        if (alertaGenerada != null) {
                            System.out.println("\n⚠️ ATENCIÓN: El nivel actual de la zona es " + alertaGenerada.getNivel());
                            if (alertaGenerada.getEquipoAsignado() != null) {
                                System.out.println("🚨 EMERGENCIA: Se ha asignado el equipo '" +
                                        alertaGenerada.getEquipoAsignado().getNombreEquipo() + "' a la alerta.");
                            }
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("❌ Error: Ingrese un valor numérico válido.");
                    } catch (LecturaInvalidaException | IllegalStateException | IllegalArgumentException e) {
                        // Aquí capturamos los errores del gestor (como la falta de equipos) y los imprimimos
                        System.out.println("❌ " + e.getMessage());
                    }
                    break;

                case "2":
                    for (EstacionMeteorologica est : gestor.getEstaciones()) {
                        System.out.println("\nEstación: " + est.getNombre() + " (Operativa: " + est.estaOperativa() + ")");
                        for (Sensor s : est.getSensores()) {
                            System.out.println("  -> Sensor ID: " + s.getIdSensor() + " | Mediciones: " + s.getMediciones().size());
                        }
                    }
                    break;

                case "3":
                    salir = true;
                    System.out.println("Cerrando el sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }

    private static void inicializarDatosPrueba(GestorMeteorologico gestor) {
        ZonaGeografica zonaSur = new ZonaGeografica("Z-01", "Valle Sur", NivelVulnerabilidad.ALTO, 50.0, 100.0, 150.0);
        gestor.agregarZona(zonaSur);

        EquipoRespuesta equipoAlpha = new EquipoRespuesta("EQ-01", "Escuadrón Alpha", true);
        gestor.agregarEquipo(equipoAlpha);

        ResponsableTecnico tecnico1 = new ResponsableTecnico("12345678", "Juan", "Pérez", "juan@mail.com", "11223344", "Hidrología", "LIC-998", equipoAlpha);
        equipoAlpha.agregarIntegrante(tecnico1);

        EstacionMeteorologica estacionA = new EstacionMeteorologica("EST-01", "Estación Central", -34.6, -58.3, 25.0, TipoEstacion.AUTOMATICA, zonaSur, tecnico1);
        zonaSur.agregarEstacion(estacionA);
        gestor.agregarEstacion(estacionA);

        Sensor sensor1 = new Sensor("SENS-1", "PluviTech", "X-100", 0.5, LocalDate.now().minusMonths(6));
        gestor.instalarSensorEnEstacion(sensor1, estacionA);

        System.out.println("Datos de prueba cargados correctamente en memoria.\n");
    }
}