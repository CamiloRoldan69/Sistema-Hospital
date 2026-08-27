package com.hospital.modelo;

/**
 * Clase ABSTRACTA que representa a cualquier paciente. Igual que
 * Personal, nadie "es" un Paciente genérico: siempre es Ambulatorio
 * u Hospitalizado.
 */
public abstract class Paciente {

    private String nombre;
    private String codigo;
    private int edad;

    /**
     * El constructor valida la edad ANTES de guardarla. Si es
     * inválida, lanza IllegalArgumentException: una excepción
     * "unchecked" (NO obliga a poner try/catch en quien la llama).
     * Se usa así en Java cuando el error es culpa de un argumento
     * incorrecto pasado al método/constructor, algo que en teoría
     * el programador que llama debería haber evitado.
     */
    public Paciente(String nombre, String codigo, int edad) {
        if (edad < 0 || edad > 120) {
            throw new IllegalArgumentException("Edad inválida: " + edad);
        }
        this.nombre = nombre;
        this.codigo = codigo;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getEdad() {
        return edad;
    }

    // Cada subclase decide cómo mostrar su propia información.
    public abstract String obtenerInfo();
}
