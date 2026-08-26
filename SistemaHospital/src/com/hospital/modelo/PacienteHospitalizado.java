package com.hospital.modelo;

public final class PacienteHospitalizado extends Paciente {

    private int numeroCama;
    private int diasHospitalizado;

    public PacienteHospitalizado(String nombre, String codigo, int edad,
                                  int numeroCama, int diasHospitalizado) {
        super(nombre, codigo, edad);
        if (numeroCama <= 0) {
            throw new IllegalArgumentException("Número de cama inválido.");
        }
      
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
