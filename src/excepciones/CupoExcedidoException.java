package excepciones;

/**
 * Excepción personalizada chequeada para controlar cuando se supera
 * el cupo máximo de una actividad.
 */
public class CupoExcedidoException extends Exception {
    public CupoExcedidoException(String mensaje) {
        super(mensaje);
    }
}