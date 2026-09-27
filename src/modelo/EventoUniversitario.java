package modelo;

import modelo.actividades.Actividad;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventoUniversitario implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private Sala sala; // Relación de Agregación
    private List<Actividad> actividades; // Relación de Composición

    private static int cantidadEventos = 0;

    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        this.actividades = new ArrayList<>();
        cantidadEventos++;
    }

    // Constructor de copia solicitado en el UML
    public EventoUniversitario(EventoUniversitario otro) {
        this.id = otro.id;
        this.titulo = otro.titulo;
        this.costoBase = otro.costoBase;
        this.gratuito = otro.gratuito;
        this.sala = otro.sala;
        this.actividades = new ArrayList<>(otro.actividades);
        cantidadEventos++;
    }

    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    public void agregarActividad(Actividad actividad) {
        this.actividades.add(actividad);
    }

    public double calcularCostoEstimado() {
        if (gratuito) {
            return 0.0;
        }
        double costoMaterialesTotal = 0.0;
        for (Actividad act : actividades) {
            costoMaterialesTotal += act.calcularCostoMateriales();
        }
        return (costoBase + costoMaterialesTotal) * 1.21; // Aplica IVA 21%
    }

    public void mostrarDatos() {
        System.out.println("\n==================================================");
        System.out.println("EVENTO: " + titulo + " (ID: " + id + ")");
        System.out.println("Gratuito: " + (gratuito ? "Sí" : "No"));
        System.out.println("Costo Base: $" + costoBase);
        System.out.println("Costo Estimado con Impuestos y Materiales: $" + calcularCostoEstimado());
        if (sala != null) {
            System.out.print("SALA ASIGNADA: ");
            sala.mostrarDatos();
        } else {
            System.out.println("SALA: No asignada");
        }
        System.out.println("--- ACTIVIDADES DEL EVENTO ---");
        for (Actividad act : actividades) {
            act.mostrarIdentificacion();
            act.mostrarInscripciones();
        }
        System.out.println("==================================================\n");
    }

    /**
     * Persiste el objeto en disco con manejo granular de excepciones.
     */
    public boolean persistirEvento() {
        String archivo = "evento_" + this.id + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(this);
            System.out.println("[PERSISTENCIA] Evento '" + id + "' persistido exitosamente en '" + archivo + "'.");
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("[ERROR PERSISTENCIA] No se pudo crear o acceder al archivo '" + archivo + "': " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[ERROR PERSISTENCIA] Error de entrada/salida al guardar el evento '" + id + "': " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[ERROR PERSISTENCIA] Error inesperado al guardar: " + e.getMessage());
        }
        return false;
    }

    /**
     * Recupera un evento desde disco de forma estática con manejo granular de excepciones.
     */
    public static EventoUniversitario recuperarEvento(String id) {
        String archivo = "evento_" + id + ".dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            EventoUniversitario evento = (EventoUniversitario) ois.readObject();
            System.out.println("[PERSISTENCIA] Evento '" + id + "' deserializado exitosamente desde '" + archivo + "'.");
            return evento;
        } catch (FileNotFoundException e) {
            System.err.println("[ERROR PERSISTENCIA] Archivo '" + archivo + "' no encontrado para deserialización.");
        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR PERSISTENCIA] Estructura de clase incompatible al deserializar el evento.");
        } catch (IOException e) {
            System.err.println("[ERROR PERSISTENCIA] Error de E/S al leer el archivo '" + archivo + "': " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[ERROR PERSISTENCIA] Error no esperado durante la lectura: " + e.getMessage());
        }
        return null;
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public List<Actividad> getActividades() { return actividades; }

    public static int getCantidadEventos() {
        return cantidadEventos;
    }
    // a. Método parametrizado acotado para filtrar actividades por tipo y devolver una lista fuertemente tipada
    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> listaFiltrada = new ArrayList<>();
        for (Actividad act : this.actividades) {
            if (tipo.isInstance(act)) {
                listaFiltrada.add(tipo.cast(act));
            }
        }
        return listaFiltrada;
    }

    // b. Método que utiliza wildcards (? extends Actividad) para calcular costos de materiales de cualquier sublista
    public double calcularCostoMateriales(List<? extends Actividad> listaActividades) {
        double costoTotal = 0.0;
        for (Actividad act : listaActividades) {
            costoTotal += act.calcularCostoMateriales();
        }
        return costoTotal;
    }
}