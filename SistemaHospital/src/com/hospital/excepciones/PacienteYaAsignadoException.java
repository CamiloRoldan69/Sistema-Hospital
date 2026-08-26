package com.hospital.excepciones;

public class PacienteYaAsignadoException extends Exception {

    public PacienteYaAsignadoException(String codigo) {
        super("El paciente con código " + codigo + " ya está asignado a una sala.");
    }
}
