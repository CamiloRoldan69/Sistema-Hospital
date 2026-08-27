package com.hospital.excepciones;

/**
 * Se lanza cuando una sala ya está llena y se intenta asignar un
 * paciente más.
 *
 * Es una excepción "checked" (extiende Exception, no RuntimeException):
 * el compilador OBLIGA a manejarla con try/catch o a declararla con
 * "throws". Eso es justo lo que evita que el programa se caiga:
 * en vez de un crash, el usuario ve un mensaje de error y el menú
 * sigue funcionando con normalidad.
 */
public class CamaNoDisponibleException extends Exception {

    public CamaNoDisponibleException(String mensaje) {
        // super(mensaje) guarda el texto dentro de la excepción para
        // que luego, al capturarla, podamos leerlo con e.getMessage()
        super(mensaje);
    }
}
