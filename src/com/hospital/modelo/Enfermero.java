package com.hospital.modelo;

/**
 * Representa a un enfermero. Igual que Medico, hereda de Personal
 * pero guarda un dato distinto (el turno) y genera un reporte con
 * otro formato.
 */
public class Enfermero extends Personal {

    private String turno;

    public Enfermero(String nombre, String id, String especialidad, String turno) {
        super(nombre, id, especialidad);
        this.turno = turno;
    }

    // Sobreescritura: la versión de Enfermero para generarReporte().
    @Override
    public String generarReporte() {
        return "=== ENFERMERO ===" +
               "\nNombre: " + getNombre() +
               "\nID: " + getId() +
               "\nEspecialidad: " + getEspecialidad() +
               "\nTurno: " + turno;
    }
}
