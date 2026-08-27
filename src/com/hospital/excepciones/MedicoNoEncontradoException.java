package com.hospital.excepciones;

/**
 * EXCEPCIÓN EXTRA (no pedida explícitamente en el enunciado, pero
 * suma robustez): se lanza cuando se busca un médico por su ID
 * (por ejemplo para agendar una cita o ver su agenda) y ese ID no
 * pertenece a ningún médico registrado en el hospital.
 */
public class MedicoNoEncontradoException extends Exception {

    public MedicoNoEncontradoException(String id) {
        super("Médico con ID " + id + " no encontrado.");
    }
}
