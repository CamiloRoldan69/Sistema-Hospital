package com.hospital.excepciones;

public class SalaNoEncontradaException extends Exception {

    public SalaNoEncontradaException(String nombre) {
        super("Sala \"" + nombre + "\" no encontrada.");
    }
}
