package com.hospital.excepciones;

/**
 * Se lanza cuando se busca un paciente por su código y ese código
 * no existe en ninguna sala del hospital.
 *
 * Fíjate que el constructor NO recibe el mensaje ya armado, sino
 * solo el código: el mensaje final se construye aquí adentro. Así
 * garantizamos que el texto de error sea siempre el mismo formato,
 * sin importar desde qué parte del programa se lance.
 */
public class PacienteNoEncontradoException extends Exception {

    public PacienteNoEncontradoException(String codigo) {
        super("Paciente con código " + codigo + " no encontrado.");
    }
}
