package com.hospital.modelo;
public abstract class Paciente {

    private String nombre;
    private String codigo;
    private int edad;

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
    public abstract String obtenerInfo();
}
