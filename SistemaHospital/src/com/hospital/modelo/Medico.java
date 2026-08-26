package com.hospital.modelo;
import java.util.ArrayList;
import com.hospital.excepciones.CitaInvalidaException;

public class Medico extends Personal {

    private ArrayList<Cita> citas;

    public Medico(String nombre, String id, String especialidad) {
        super(nombre, id, especialidad);
        this.citas = new ArrayList<>();
    }
    public void agendarCita(String fecha) throws CitaInvalidaException {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new CitaInvalidaException("La fecha no puede estar vacía.");
        }
        Cita nuevaCita = new Cita(fecha, "Consulta general", null, this);
        citas.add(nuevaCita);
        System.out.println("Cita agendada para " + fecha);
    }
    public void agendarCita(String fecha, String motivo) throws CitaInvalidaException {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new CitaInvalidaException("La fecha no puede estar vacía.");
        }
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new CitaInvalidaException("El motivo no puede estar vacío.");
        }
        Cita nuevaCita = new Cita(fecha, motivo, null, this);
        citas.add(nuevaCita);
        System.out.println("Cita agendada para " + fecha + " por: " + motivo);
    }
    @Override
    public String generarReporte() {
        return "=== MÉDICO ===" +
               "\nNombre: " + getNombre() +
               "\nID: " + getId() +
               "\nEspecialidad: " + getEspecialidad() +
               "\nCitas agendadas: " + citas.size();
    }

    public ArrayList<Cita> getCitas() {
        return citas;
    }
}
