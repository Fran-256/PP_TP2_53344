package modelo.actividades;

public class Charla extends Actividad {
    private static final long serialVersionUID = 1L;
    private String disertante;

    public Charla(int id, String titulo, int cupoMaximo, String disertante) {
        super(id, titulo, cupoMaximo);
        this.disertante = disertante;
    }

    public String getDisertante() { return disertante; }
    public void setDisertante(String disertante) { this.disertante = disertante; }

    @Override
    public double calcularCostoMateriales() {
        return 1500.0; // Costo estimado de folletería/material de la charla
    }

    @Override
    public String getTipo() {
        return "Charla";
    }
}