package modelo.actividades;

import certificacion.Certificable;
import modelo.Estudiante;

public class Taller extends Actividad implements Certificable {
    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook) {
        super(id, titulo, cupoMaximo);
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales() {
        return requiereNotebook ? 300.0 : 100.0;
    }

    @Override
    public String getTipo() {
        return "Taller";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "--------------------------------------------------\n" +
                "CERTIFICADO DE PARTICIPACIÓN - " + ENTIDAD_EMISORA + "\n" +
                "Se certifica que el/la estudiante: " + estudiante.getNombre() + " (Legajo: " + estudiante.getLegajo() + ")\n" +
                "ha completado exitosamente el Taller: " + getTitulo() + "\n" +
                "--------------------------------------------------";
    }
}