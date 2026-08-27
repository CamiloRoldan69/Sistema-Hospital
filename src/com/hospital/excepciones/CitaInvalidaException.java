package com.hospital.excepciones;

/**
 * Se lanza cuando se intenta agendar una cita con datos incompletos
 * (fecha vacía o motivo vacío).
 */
public class CitaInvalidaException extends Exception {

    public CitaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
