package com.hospital.modelo;

/**
 * Un paciente ambulatorio no ocupa cama: viene a consulta y se va.
 */
public class PacienteAmbulatorio extends Paciente {

    private String proximaCita;

    public PacienteAmbulatorio(String nombre, String codigo, int edad, String proximaCita) {
        super(nombre, codigo, edad);
        this.proximaCita = proximaCita;
    }

    @Override
    public String obtenerInfo() {
        return "Paciente ambulatorio: " + getNombre() +
               " | Código: " + getCodigo() +
               " | Edad: " + getEdad() +
               " | Próxima cita: " + proximaCita;
    }
}
