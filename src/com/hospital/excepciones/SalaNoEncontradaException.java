package com.hospital.excepciones;

/**
 * EXCEPCIÓN EXTRA: se lanza cuando el usuario escribe el nombre de
 * una sala que no existe en el hospital (por ejemplo con una falta
 * de ortografía).
 */
public class SalaNoEncontradaException extends Exception {

    public SalaNoEncontradaException(String nombre) {
        super("Sala \"" + nombre + "\" no encontrada.");
    }
}
