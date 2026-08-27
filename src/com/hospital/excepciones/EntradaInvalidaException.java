package com.hospital.excepciones;

/**
 * EXCEPCIÓN EXTRA: se usa cuando el usuario escribe algo que no es
 * un número en el menú (por ejemplo escribe "hola" en vez de "3").
 * Sin esta excepción, ese error tumbaría el programa con un
 * NumberFormatException sin manejar; con ella, mostramos un mensaje
 * amigable y el menú se vuelve a mostrar.
 */
public class EntradaInvalidaException extends Exception {

    public EntradaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
