package com.hospital.excepciones;

/**
 * EXCEPCIÓN EXTRA: se lanza cuando se intenta asignar a una sala un
 * paciente que YA está asignado a alguna sala del hospital (la misma
 * u otra). Sin esta validación, un paciente podía quedar duplicado
 * dentro de la lista de una sala, o "aparecer" en dos salas a la vez.
 */
public class PacienteYaAsignadoException extends Exception {

    public PacienteYaAsignadoException(String codigo) {
        super("El paciente con código " + codigo + " ya está asignado a una sala.");
    }
}
