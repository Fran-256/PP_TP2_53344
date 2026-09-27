package modelo.actividades;

import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Actividad implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String titulo;
    private int cupoMaximo;
    public static final int CUPO_MINIMO = 5; // Constante final según UML
    protected List<Inscripcion> inscripciones;

    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;
        this.inscripciones = new ArrayList<>();
    }

    /**
     * Modificado según la consigna: lanza CupoExcedidoException si no hay vacantes.
     */
    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
        if (inscripciones.size() >= cupoMaximo) {
            throw new CupoExcedidoException("No hay cupos disponibles en '" + titulo + "' (Cupo Máximo: " + cupoMaximo + ").");
        }
        Inscripcion nuevaInscripcion = new Inscripcion(estudiante);
        inscripciones.add(nuevaInscripcion);
        System.out.println("  [ÉXITO] Inscripción realizada para " + estudiante.getNombre() + " en '" + titulo + "'.");
        return nuevaInscripcion;
    }

    public void mostrarInscripciones() {
        System.out.println("  -> Inscripciones en " + getTipo() + " '" + titulo + "' (" + inscripciones.size() + "/" + cupoMaximo + "):");
        if (inscripciones.isEmpty()) {
            System.out.println("     (No hay estudiantes inscriptos)");
        } else {
            for (Inscripcion insc : inscripciones) {
                insc.mostrarDatos();
            }
        }
    }

    public final void mostrarIdentificacion() {
        System.out.println("Actividad [" + getTipo() + "] ID: " + id + " | Título: " + titulo + " | Cupo Máx: " + cupoMaximo);
    }

    public abstract double calcularCostoMateriales();
    public abstract String getTipo();

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public int getCupoMaximo() { return cupoMaximo; }
    public List<Inscripcion> getInscripciones() { return inscripciones; }
}