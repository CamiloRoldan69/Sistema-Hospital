package com.hospital.excepciones;

public class CodigoDuplicadoException extends Exception {

    public CodigoDuplicadoException(String codigo) {
        super("Ya existe un paciente registrado con el código \"" + codigo + "\".");
    }
}
