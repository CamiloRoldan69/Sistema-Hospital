package com.hospital.modelo;

import java.util.ArrayList;
import com.hospital.excepciones.PacienteNoEncontradoException;
import com.hospital.excepciones.MedicoNoEncontradoException;

/**
 * Clase "orquestadora" del sistema. La relación Hospital -> Sala es
 * de COMPOSICIÓN: las salas se crean DENTRO del constructor del
 * hospital, no llegan desde afuera. Si el objeto Hospital desaparece,
 * sus salas desaparecen con él (a diferencia de Sala -> Paciente,
 * que es agregación).
 */
public class Hospital {

    private String nombre;
    private ArrayList<Sala> salas;
    private ArrayList<Personal> personal;

    public Hospital(String nombre) {
        this.nombre = nombre;
        this.salas = new ArrayList<>();
        this.personal = new ArrayList<>();

        // Composición en acción: el propio Hospital crea sus salas.
        salas.add(new Sala("Urgencias", 10));
        salas.add(new Sala("Pediatria", 8));
        salas.add(new Sala("Cirugia", 6));
    }

    public void agregarPersonal(Personal p) {
        personal.add(p);
        System.out.println(p.getNombre() + " registrado en el hospital.");
    }

    /**
     * Agrega una sala nueva al hospital, creada dinámicamente desde
     * el menú (a diferencia de "Urgencias", "Pediatria" y "Cirugia",
     * que se crean por composición dentro del constructor).
     */
    public void agregarSala(Sala s) {
        salas.add(s);
        System.out.println("Sala \"" + s.getNombre() + "\" creada en el hospital.");
    }

    public Sala buscarSala(String nombreSala) {
        for (Sala s : salas) {
            if (s.getNombre().equalsIgnoreCase(nombreSala)) {
                return s;
            }
        }
        return null;
    }

    /**
     * EXTRA: busca un médico por su ID recorriendo TODO el personal.
     * "instanceof" pregunta "¿este objeto Personal es en realidad un
     * Medico?" — necesario porque la lista mezcla Medico y Enfermero.
     * Si no lo encuentra, lanza la excepción extra
     * MedicoNoEncontradoException en vez de devolver null: así quien
     * llama a este método está OBLIGADO a manejar el caso "no existe".
     */
    public Medico buscarMedico(String id) throws MedicoNoEncontradoException {
        for (Personal p : personal) {
            if (p instanceof Medico && p.getId().equalsIgnoreCase(id)) {
                return (Medico) p;
            }
        }
        throw new MedicoNoEncontradoException(id);
    }

    public Paciente buscarPaciente(String codigo) throws PacienteNoEncontradoException {
        for (Sala s : salas) {
            try {
                return s.buscarPaciente(codigo);
            } catch (PacienteNoEncontradoException e) {
                // No estaba en esta sala: seguimos buscando en las
                // demás. Atrapamos la excepción AQUÍ ADENTRO del bucle
                // para que "no encontrarlo en una sala" no detenga la
                // búsqueda en las otras.
            }
        }
        throw new PacienteNoEncontradoException(codigo);
    }

    public void generarReporteGeneral() {
        System.out.println("=== REPORTE GENERAL: " + nombre + " ===");
        for (Sala s : salas) {
            System.out.println("Sala: " + s.getNombre() +
                    " | Ocupación: " + s.getPacientes().size() +
                    "/" + s.getCapacidad());
        }
        System.out.println("Personal registrado: " + personal.size());
        for (Personal p : personal) {
            // Polimorfismo dinámico: aquí NO importa si p es Medico o
            // Enfermero, cada uno imprime su propio reporte.
            System.out.println(p.generarReporte());
        }
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public ArrayList<Personal> getPersonal() {
        return personal;
    }
}
