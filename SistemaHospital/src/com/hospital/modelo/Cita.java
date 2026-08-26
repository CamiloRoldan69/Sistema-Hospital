package com.hospital.modelo;

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
