package app;

import modelo.actividades.*;
import certificacion.Certificable;
import excepciones.CupoExcedidoException;
import modelo.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   SISTEMA DE GESTIÓN DE EVENTOS - EJERCICIO 2   ");
        System.out.println("==================================================\n");

        // b. Se crean estudiantes y eventos con sala y actividades
        Estudiante est1 = new Estudiante("EST-301", "Luciano Benítez");
        Estudiante est2 = new Estudiante("EST-302", "Valeria Ríos");

        EventoUniversitario evento = new EventoUniversitario("E-03", "Congreso de Ingeniería y Software", 35000.0, false);
        Sala sala = new Sala(3, "Aula Magna");
        evento.asignarSala(sala);

        // Agregamos actividades de distintos tipos
        Charla charla1 = new Charla(401, "Ciberseguridad Básica", 30, "Dr. Roberto Gómez");
        Taller taller1 = new Taller(402, "Git y GitHub Avanzado", 15, true);
        Curso curso1 = new Curso(403, "Desarrollo Backend con Spring Boot", 20, 3);

        evento.agregarActividad(charla1);
        evento.agregarActividad(taller1);
        evento.agregarActividad(curso1);

        // c. Se inscriben estudiantes
        try {
            charla1.inscribir(est1);
            taller1.inscribir(est1);
            curso1.inscribir(est2);
            System.out.println("Inscripciones realizadas correctamente.\n");
        } catch (CupoExcedidoException e) {
            System.err.println("Error: " + e.getMessage());
        }

        // d. y g. Se filtra la lista de actividades devolviendo listas correctamente tipadas
        System.out.println("--- FILTRADO DE ACTIVIDADES POR TIPO (List<T>) ---");
        List<Charla> listaCharlas = evento.filtrarActividadesPorTipo(Charla.class);
        List<Taller> listaTalleres = evento.filtrarActividadesPorTipo(Taller.class);
        List<Curso> listaCursos = evento.filtrarActividadesPorTipo(Curso.class);

        // e. Se muestra por consola la cantidad de actividades de cada tipo creadas
        System.out.println("Cantidad de Charlas: " + listaCharlas.size());
        System.out.println("Cantidad de Talleres: " + listaTalleres.size());
        System.out.println("Cantidad de Cursos: " + listaCursos.size() + "\n");

        // f. Se calcula y muestra el costo de materiales usando el método con wildcards (? extends Actividad)
        System.out.println("--- CÁLCULO DE COSTO DE MATERIALES (Wildcards) ---");
        double costoCharlas = evento.calcularCostoMateriales(listaCharlas);
        double costoTalleres = evento.calcularCostoMateriales(listaTalleres);
        double costoCursos = evento.calcularCostoMateriales(listaCursos);
        double costoTotalGeneral = evento.calcularCostoMateriales(evento.getActividades());

        System.out.println("Costo materiales de Charlas: $" + costoCharlas);
        System.out.println("Costo materiales de Talleres: $" + costoTalleres);
        System.out.println("Costo materiales de Cursos: $" + costoCursos);
        System.out.println("Costo de materiales total del evento: $" + costoTotalGeneral + "\n");

        // h. Se muestran los datos generales del evento
        System.out.println("--- DATOS GENERALES DEL EVENTO ---");
        evento.mostrarDatos();
    }
}