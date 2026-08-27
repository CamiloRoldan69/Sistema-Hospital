package com.hospital.excepciones;

/**
 * EXCEPCIÓN EXTRA: se lanza al registrar un paciente si el código
 * que se quiere usar ya pertenece a otro paciente. Evita tener dos
 * pacientes distintos con el mismo código en el sistema.
 */
public class CodigoDuplicadoException extends Exception {

    public CodigoDuplicadoException(String codigo) {
        super("Ya existe un paciente registrado con el código \"" + codigo + "\".");
    }
}
