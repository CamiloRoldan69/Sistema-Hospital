package com.hospital.excepciones;

public class PacienteNoEncontradoException extends Exception {

    public PacienteNoEncontradoException(String codigo) {
        super("Paciente con código " + codigo + " no encontrado.");
    }
}
