package com.hospital.modelo;
import java.util.ArrayList;
import com.hospital.excepciones.PacienteNoEncontradoException;
import com.hospital.excepciones.MedicoNoEncontradoException;


public class Hospital {

    private String nombre;
    private ArrayList<Sala> salas;
    private ArrayList<Personal> personal;

    public Hospital(String nombre) {
        this.nombre = nombre;
        this.salas = new ArrayList<>();
        this.personal = new ArrayList<>();

        salas.add(new Sala("Urgencias", 10));
        salas.add(new Sala("Pediatria", 8));
        salas.add(new Sala("Cirugia", 6));
    }

    public void agregarPersonal(Personal p) {
        personal.add(p);
        System.out.println(p.getNombre() + " registrado en el hospital.");
    }

    public Sala buscarSala(String nombreSala) {
        for (Sala s : salas) {
            if (s.getNombre().equalsIgnoreCase(nombreSala)) {
                return s;
            }
        }
        return null;
    }
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
