package com.hospital.modelo;
import java.util.ArrayList;
import com.hospital.excepciones.CamaNoDisponibleException;
import com.hospital.excepciones.PacienteNoEncontradoException;

public class Sala {

    private String nombre;
    private int capacidad;
    private ArrayList<Paciente> pacientes;

    public Sala(String nombre, int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("Capacidad debe ser mayor a 0.");
        }
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.pacientes = new ArrayList<>();
    }

    public void agregarPaciente(Paciente p) throws CamaNoDisponibleException {
        if (pacientes.size() >= capacidad) {
            throw new CamaNoDisponibleException("Sala " + nombre + " sin camas disponibles.");
        }
        pacientes.add(p);
        System.out.println("Paciente " + p.getNombre() + " asignado a sala " + nombre);
    }

    public void eliminarPaciente(String codigo) throws PacienteNoEncontradoException {
        for (Paciente p : pacientes) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                pacientes.remove(p);
                System.out.println("Paciente dado de alta.");
                return;
            }
        }
        throw new PacienteNoEncontradoException(codigo);
    }

    public Paciente buscarPaciente(String codigo) throws PacienteNoEncontradoException {
        for (Paciente p : pacientes) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        throw new PacienteNoEncontradoException(codigo);
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes en esta sala.");
            return;
        }
        for (Paciente p : pacientes) {
            System.out.println(p.obtenerInfo());
        }
    }
}
