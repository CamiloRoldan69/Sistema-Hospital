package com.hospital.modelo;

/**
 * Representa la ASOCIACIÓN entre un Médico y un Paciente en una
 * fecha concreta. Una asociación es la relación más "suelta" entre
 * clases: Cita solo guarda una referencia a un Medico y a un
 * Paciente, pero ninguno de los dos depende de que Cita exista, ni
 * Cita "posee" a ninguno de los dos (a diferencia de la composición
 * que ves en Hospital -> Sala).
 */
public class Cita {

    private String fecha;
    private String motivo;
    private Paciente paciente;
    private Medico medico;

    public Cita(String fecha, String motivo, Paciente paciente, Medico medico) {
        this.fecha = fecha;
        this.motivo = motivo;
        this.paciente = paciente;
        this.medico = medico;
    }

    public String getInfo() {
        return "Cita: " + fecha +
               " | Motivo: " + motivo +
               " | Médico: " + medico.getNombre() +
               " | Paciente: " + (paciente != null ? paciente.getNombre() : "sin asignar");
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public String getFecha() {
        return fecha;
    }
}
