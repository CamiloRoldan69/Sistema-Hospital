package com.hospital.modelo;

import java.util.ArrayList;
import com.hospital.excepciones.CitaInvalidaException;

/**
 * Representa a un médico. Hereda de Personal ("un Médico ES-UN
 * Personal") y añade su propia lista de citas.
 */
public class Medico extends Personal {

    private ArrayList<Cita> citas;

    public Medico(String nombre, String id, String especialidad) {
        // super(...) llama al constructor de la clase padre (Personal)
        // para inicializar nombre, id y especialidad. SIEMPRE debe
        // ser la primera línea del constructor de una subclase.
        super(nombre, id, especialidad);
        this.citas = new ArrayList<>();
    }

    /**
     * SOBRECARGA (polimorfismo ESTÁTICO / en tiempo de compilación):
     * este método y el de abajo se llaman igual ("agendarCita") pero
     * reciben una cantidad distinta de parámetros. Java decide cuál
     * de los dos usar según los argumentos que le pases al llamarlo,
     * ANTES de ejecutar el programa (por eso es "estático").
     *
     * "throws CitaInvalidaException" en la firma avisa al que llame
     * a este método que DEBE manejar ese posible error (con try/catch
     * o reenviándolo con su propio "throws").
     */
    public void agendarCita(String fecha) throws CitaInvalidaException {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new CitaInvalidaException("La fecha no puede estar vacía.");
        }
        Cita nuevaCita = new Cita(fecha, "Consulta general", null, this);
        citas.add(nuevaCita);
        System.out.println("Cita agendada para " + fecha);
    }

    // Segunda versión sobrecargada: además recibe el motivo de la cita.
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

    /**
     * SOBREESCRITURA (@Override) del método abstracto de Personal.
     * Esto es polimorfismo DINÁMICO: si tienes una ArrayList<Personal>
     * que mezcla Medico y Enfermero, y llamas p.generarReporte() para
     * cada uno, Java ejecuta automáticamente ESTA versión para los
     * médicos, sin que tengas que preguntar "if (p instanceof Medico)".
     */
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
