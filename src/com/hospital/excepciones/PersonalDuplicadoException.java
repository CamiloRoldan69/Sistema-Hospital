package com.hospital.excepciones;

/**
 * EXCEPCIÓN EXTRA: se lanza al registrar un médico o enfermero si el
 * ID que se quiere usar ya pertenece a otro miembro del personal.
 * Evita tener dos médicos/enfermeros distintos con el mismo ID
 * (lo que rompería, por ejemplo, la búsqueda por ID en
 * Hospital.buscarMedico()).
 */
public class PersonalDuplicadoException extends Exception {

    public PersonalDuplicadoException(String id) {
        super("Ya existe personal registrado con el ID \"" + id + "\".");
    }
}
