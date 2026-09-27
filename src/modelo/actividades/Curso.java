package modelo.actividades;

import certificacion.Certificable;
import modelo.Estudiante;

public class Curso extends Actividad implements Certificable {
    private int nivel;

    public Curso(int id, String titulo, int cupoMaximo, int nivel) {
        super(id, titulo, cupoMaximo);
        this.nivel = nivel;
    }

    @Override
    public double calcularCostoMateriales() {
        return 1500.0 * nivel;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "--------------------------------------------------\n" +
                "CERTIFICADO DE APROBACIÓN - " + ENTIDAD_EMISORA + "\n" +
                "Se certifica que el/la estudiante: " + estudiante.getNombre() + " (Legajo: " + estudiante.getLegajo() + ")\n" +
                "ha aprobado satisfactoriamente el Curso: " + getTitulo() + " (Nivel " + nivel + ")\n" +
                "--------------------------------------------------";
    }
}