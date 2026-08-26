package com.hospital.excepciones;

public class MedicoNoEncontradoException extends Exception {

    public MedicoNoEncontradoException(String id) {
        super("Médico con ID " + id + " no encontrado.");
    }
}
