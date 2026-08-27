package com.hospital.excepciones;

/**
 * EXCEPCIÓN EXTRA: se lanza al crear una sala nueva si ya existe una
 * sala con ese mismo nombre en el hospital.
 */
public class SalaDuplicadaException extends Exception {

    public SalaDuplicadaException(String nombre) {
        super("Ya existe una sala llamada \"" + nombre + "\".");
    }
}
