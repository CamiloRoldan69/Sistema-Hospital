package com.hospital.modelo;

/**
 * "final" en una clase significa que NADIE puede extenderla (no
 * puedes escribir "class X extends PacienteHospitalizado"). Se usa
 * cuando, por diseño, esta clase es el último nivel de la jerarquía
 * y no tiene sentido crear subtipos de ella.
 *
 * Un paciente hospitalizado ocupa una cama y acumula días de
 * estadía.
 */
public final class PacienteHospitalizado extends Paciente {

    private int numeroCama;
    private int diasHospitalizado;

    public PacienteHospitalizado(String nombre, String codigo, int edad,
                                  int numeroCama, int diasHospitalizado) {
        super(nombre, codigo, edad);
        if (numeroCama <= 0) {
            throw new IllegalArgumentException("Número de cama inválido.");
        }
        // FIX: no había ninguna validación de este campo. Un valor
        // negativo (-10) se guardaba sin protesta y se mostraba tal
        // cual en el reporte ("Días: -10"), algo sin sentido en la
        // vida real.
        if (diasHospitalizado < 0) {
            throw new IllegalArgumentException("Los días de hospitalización no pueden ser negativos.");
        }
        this.numeroCama = numeroCama;
        this.diasHospitalizado = diasHospitalizado;
    }

    @Override
    public String obtenerInfo() {
        return "Paciente hospitalizado: " + getNombre() +
               " | Código: " + getCodigo() +
               " | Edad: " + getEdad() +
               " | Cama: " + numeroCama +
               " | Días: " + diasHospitalizado;
    }

    public int getDiasHospitalizado() {
        return diasHospitalizado;
    }
}
