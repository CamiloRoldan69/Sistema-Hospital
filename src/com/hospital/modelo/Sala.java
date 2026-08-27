package com.hospital.modelo;

import java.util.ArrayList;
import com.hospital.excepciones.CamaNoDisponibleException;
import com.hospital.excepciones.PacienteNoEncontradoException;

/**
 * Representa una sala del hospital. La relación Sala -> Paciente es
 * de AGREGACIÓN: una sala puede existir sin pacientes, y un paciente
 * puede existir en el sistema sin estar todavía asignado a ninguna
 * sala. Ninguno de los dos controla el "ciclo de vida" del otro
 * (a diferencia de Hospital -> Sala, que sí es composición).
 */
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
        // equalsIgnoreCase en vez de equals: así "abc123" y "ABC123"
        // se tratan como el mismo código en TODO el sistema (mismo
        // criterio que ya se usa para comparar nombres de sala).
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
            // Polimorfismo dinámico otra vez: no importa si "p" es un
            // Ambulatorio o un Hospitalizado, Java llama automáticamente
            // a la versión correcta de obtenerInfo().
            System.out.println(p.obtenerInfo());
        }
    }
}
