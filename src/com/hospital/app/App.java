package com.hospital.app;

import java.util.ArrayList;
import java.util.Scanner;

import com.hospital.modelo.*;
import com.hospital.excepciones.*;

/**
 * Clase principal: contiene el menú interactivo por consola.
 *
 * IDEA CLAVE DE ESTA CLASE: casi cada opción del menú está envuelta
 * en un try/catch. Así, si algo sale mal (una sala llena, un código
 * repetido, un texto donde se esperaba un número...) el programa NO
 * SE CAE: se imprime un mensaje de error y el menú se vuelve a
 * mostrar, como si nada hubiera pasado.
 */
public class App {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Hospital hospital = new Hospital("Hospital Regional San Juan");

        Medico m1 = new Medico("Dra. Laura Gomez", "M001", "Cardiologia");
        Medico m2 = new Medico("Dr. Pedro Ruiz", "M002", "Pediatria");
        Enfermero e1 = new Enfermero("Ana Torres", "E001", "Cuidados intensivos", "Mañana");

        hospital.agregarPersonal(m1);
        hospital.agregarPersonal(m2);
        hospital.agregarPersonal(e1);

        // Lista temporal: pacientes ya creados pero que podrían no
        // estar asignados todavía a ninguna sala.
        ArrayList<Paciente> pacientesRegistrados = new ArrayList<>();

        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcionMenu();

            switch (opcion) {
                case 1:
                    registrarPaciente(pacientesRegistrados);
                    break;
                case 2:
                    asignarPacienteASala(hospital, pacientesRegistrados);
                    break;
                case 3:
                    agendarCita(hospital);
                    break;
                case 4:
                    verPacientesDeSala(hospital);
                    break;
                case 5:
                    verAgendaMedico(hospital);
                    break;
                case 6:
                    darDeAltaPaciente(hospital);
                    break;
                case 7:
                    buscarPacientePorCodigo(hospital);
                    break;
                case 8:
                    hospital.generarReporteGeneral();
                    break;
                case 9:
                    registrarPersonal(hospital);
                    break;
                case 10:
                    crearSala(hospital);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }

        } while (opcion != 0);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== MENÚ HOSPITAL =====");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Asignar paciente a sala");
        System.out.println("3. Agendar cita con médico");
        System.out.println("4. Ver pacientes de una sala");
        System.out.println("5. Ver agenda de un médico");
        System.out.println("6. Dar de alta a paciente");
        System.out.println("7. Buscar paciente por código");
        System.out.println("8. Reporte general del hospital");
        System.out.println("9. Registrar médico o enfermero");
        System.out.println("10. Crear nueva sala");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    /**
     * Lee la opción del menú de forma segura. Si el usuario escribe
     * texto en vez de un número (ej. "hola"), Integer.parseInt lanza
     * NumberFormatException. En vez de dejar que esa excepción tumbe
     * el programa, la atrapamos y la traducimos a nuestra propia
     * EntradaInvalidaException solo para mostrar un mensaje
     * coherente con el resto del sistema. Como es "checked", hay que
     * capturarla en el mismo lugar donde se lanza.
     */
    private static int leerOpcionMenu() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            try {
                throw new EntradaInvalidaException("Debes escribir un número.");
            } catch (EntradaInvalidaException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            return -1; // ningún caso del switch usa -1, cae en "default"
        }
    }

    private static void registrarPaciente(ArrayList<Paciente> pacientesRegistrados) {
        try {
            System.out.print("Tipo de paciente (1=Ambulatorio, 2=Hospitalizado): ");
            int tipo = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) {
                throw new EntradaInvalidaException("El nombre no puede estar vacío.");
            }

            // trim() en el código: si el usuario escribe " 123456 " con
            // espacios de más, lo guardamos ya limpio, para que después
            // buscarlo/compararlo funcione siempre igual.
            System.out.print("Código: ");
            String codigo = sc.nextLine().trim();
            if (codigo.isEmpty()) {
                throw new EntradaInvalidaException("El código no puede estar vacío.");
            }

            // Evita registrar dos pacientes con el mismo código.
            // equalsIgnoreCase (en vez de equals) para que "abc123" y
            // "ABC123" se traten como el mismo código, igual que ya
            // se hace con los nombres de sala en Hospital.buscarSala().
            for (Paciente p : pacientesRegistrados) {
                if (p.getCodigo().equalsIgnoreCase(codigo)) {
                    throw new CodigoDuplicadoException(codigo);
                }
            }

            System.out.print("Edad: ");
            int edad = Integer.parseInt(sc.nextLine().trim());

            // FIX: antes este "if" solo comprobaba tipo == 1 y trataba
            // CUALQUIER otro número (2, 9, -3...) como Hospitalizado.
            // Ahora se valida explícitamente contra 1 y 2, y cualquier
            // otro valor lanza una excepción en vez de registrar un
            // paciente con datos que el usuario nunca quiso dar.
            Paciente nuevo;
            if (tipo == 1) {
                System.out.print("Próxima cita: ");
                String proximaCita = sc.nextLine();
                nuevo = new PacienteAmbulatorio(nombre, codigo, edad, proximaCita);
            } else if (tipo == 2) {
                System.out.print("Número de cama: ");
                int cama = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Días hospitalizado: ");
                int dias = Integer.parseInt(sc.nextLine().trim());
                nuevo = new PacienteHospitalizado(nombre, codigo, edad, cama, dias);
            } else {
                throw new EntradaInvalidaException("Tipo de paciente inválido. Usa 1 (Ambulatorio) o 2 (Hospitalizado).");
            }

            pacientesRegistrados.add(nuevo);
            System.out.println("Paciente registrado correctamente.");

        } catch (CodigoDuplicadoException | EntradaInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            // NumberFormatException es subclase de IllegalArgumentException,
            // así que este catch debe ir ANTES del catch(IllegalArgumentException)
            // de abajo, o el compilador marca error ("ya capturada").
            // Sin este catch, e.getMessage() mostraría el mensaje interno
            // de Java (ej. 'For input string: "sss"') en vez de uno claro.
            System.out.println("Error: Escribe solo números en tipo, edad, cama y días.");
        } catch (IllegalArgumentException e) {
            // OJO: NumberFormatException (texto no numérico) es en
            // realidad una SUBCLASE de IllegalArgumentException, así
            // que este único catch atrapa tanto "Edad inválida" y
            // "Número de cama inválido" (que lanzan Paciente y
            // PacienteHospitalizado) como un valor no numérico.
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void asignarPacienteASala(Hospital hospital, ArrayList<Paciente> pacientesRegistrados) {
        try {
            System.out.println("Salas disponibles:");
            for (Sala s : hospital.getSalas()) {
                System.out.println("- " + s.getNombre() + " (" + s.getPacientes().size()
                        + "/" + s.getCapacidad() + ")");
            }
            System.out.print("Nombre de la sala: ");
            String nombreSala = sc.nextLine().trim();
            Sala sala = hospital.buscarSala(nombreSala);
            if (sala == null) {
                throw new SalaNoEncontradaException(nombreSala);
            }

            // trim() para no fallar por espacios de más al escribir el
            // código, y equalsIgnoreCase para tratarlo igual sin
            // importar mayúsculas/minúsculas (mismo criterio usado al
            // registrar pacientes).
            System.out.print("Código del paciente a asignar: ");
            String codigo = sc.nextLine().trim();
            Paciente paciente = null;
            for (Paciente p : pacientesRegistrados) {
                if (p.getCodigo().equalsIgnoreCase(codigo)) {
                    paciente = p;
                    break;
                }
            }
            if (paciente == null) {
                throw new PacienteNoEncontradoException(codigo);
            }

            // FIX: antes se podía asignar el mismo paciente dos veces
            // (a la misma sala o a otra), quedando duplicado. Ahora se
            // revisa TODAS las salas del hospital antes de asignar.
            for (Sala s : hospital.getSalas()) {
                for (Paciente pEnSala : s.getPacientes()) {
                    if (pEnSala.getCodigo().equalsIgnoreCase(codigo)) {
                        throw new PacienteYaAsignadoException(codigo);
                    }
                }
            }

            sala.agregarPaciente(paciente);

        } catch (SalaNoEncontradaException | PacienteNoEncontradoException
                | CamaNoDisponibleException | PacienteYaAsignadoException e) {
            // MULTI-CATCH: un solo bloque para varios tipos de
            // excepción, útil cuando el manejo (aquí, imprimir el
            // mensaje) es idéntico sin importar cuál de ellas ocurrió.
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void agendarCita(Hospital hospital) {
        try {
            System.out.println("Médicos disponibles:");
            for (Personal p : hospital.getPersonal()) {
                if (p instanceof Medico) {
                    System.out.println("- " + p.getId() + " | " + p.getNombre());
                }
            }
            System.out.print("ID del médico: ");
            String id = sc.nextLine().trim();
            Medico medico = hospital.buscarMedico(id);

            System.out.print("¿Agregar motivo? (s/n): ");
            // FIX: sin trim(), si el usuario escribía " s" (con un
            // espacio antes) NO coincidía con "s" y el programa
            // agendaba la cita SIN motivo aunque el usuario sí
            // quería agregarlo. equalsIgnoreCase ya cubría mayúsculas,
            // pero no espacios.
            String conMotivo = sc.nextLine().trim();
            System.out.print("Fecha: ");
            String fecha = sc.nextLine();

            if (conMotivo.equalsIgnoreCase("s")) {
                System.out.print("Motivo: ");
                String motivo = sc.nextLine();
                medico.agendarCita(fecha, motivo); // sobrecarga CON motivo
            } else {
                medico.agendarCita(fecha); // sobrecarga SIN motivo
            }

        } catch (MedicoNoEncontradoException | CitaInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void verPacientesDeSala(Hospital hospital) {
        System.out.print("Nombre de la sala: ");
        String nombreSala = sc.nextLine().trim();
        try {
            Sala sala = hospital.buscarSala(nombreSala);
            if (sala == null) {
                throw new SalaNoEncontradaException(nombreSala);
            }
            sala.listarPacientes();
        } catch (SalaNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void verAgendaMedico(Hospital hospital) {
        System.out.print("ID del médico: ");
        String id = sc.nextLine().trim();
        try {
            Medico medico = hospital.buscarMedico(id);
            if (medico.getCitas().isEmpty()) {
                System.out.println("Este médico no tiene citas agendadas.");
                return;
            }
            for (Cita c : medico.getCitas()) {
                System.out.println(c.getInfo());
            }
        } catch (MedicoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void darDeAltaPaciente(Hospital hospital) {
        System.out.print("Nombre de la sala: ");
        String nombreSala = sc.nextLine().trim();
        System.out.print("Código del paciente: ");
        String codigo = sc.nextLine().trim();
        try {
            Sala sala = hospital.buscarSala(nombreSala);
            if (sala == null) {
                throw new SalaNoEncontradaException(nombreSala);
            }
            sala.eliminarPaciente(codigo);
        } catch (SalaNoEncontradaException | PacienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void buscarPacientePorCodigo(Hospital hospital) {
        System.out.print("Código: ");
        String codigo = sc.nextLine().trim();
        try {
            Paciente p = hospital.buscarPaciente(codigo);
            System.out.println(p.obtenerInfo());
        } catch (PacienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Registra un médico o enfermero nuevo. Sigue el mismo patrón que
     * registrarPaciente(): valida cada campo, y si algo falla lanza
     * una excepción que se captura al final sin tumbar el programa.
     */
    private static void registrarPersonal(Hospital hospital) {
        try {
            System.out.print("Tipo de personal (1=Médico, 2=Enfermero): ");
            int tipo = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) {
                throw new EntradaInvalidaException("El nombre no puede estar vacío.");
            }

            System.out.print("ID: ");
            String id = sc.nextLine().trim();
            if (id.isEmpty()) {
                throw new EntradaInvalidaException("El ID no puede estar vacío.");
            }
            // Evita que dos médicos/enfermeros compartan el mismo ID
            // (rompería la búsqueda de Hospital.buscarMedico()).
            for (Personal p : hospital.getPersonal()) {
                if (p.getId().equalsIgnoreCase(id)) {
                    throw new PersonalDuplicadoException(id);
                }
            }

            System.out.print("Especialidad: ");
            String especialidad = sc.nextLine().trim();
            if (especialidad.isEmpty()) {
                throw new EntradaInvalidaException("La especialidad no puede estar vacía.");
            }

            Personal nuevo;
            if (tipo == 1) {
                nuevo = new Medico(nombre, id, especialidad);
            } else if (tipo == 2) {
                System.out.print("Turno: ");
                String turno = sc.nextLine().trim();
                if (turno.isEmpty()) {
                    throw new EntradaInvalidaException("El turno no puede estar vacío.");
                }
                nuevo = new Enfermero(nombre, id, especialidad, turno);
            } else {
                throw new EntradaInvalidaException("Tipo de personal inválido. Usa 1 (Médico) o 2 (Enfermero).");
            }

            hospital.agregarPersonal(nuevo);

        } catch (EntradaInvalidaException | PersonalDuplicadoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Escribe solo números en el tipo de personal.");
        } catch (IllegalArgumentException e) {
            // Cubre, por ejemplo, escribir texto donde se esperaba el
            // número de tipo (1 o 2).
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Crea una sala nueva y la agrega al hospital. La capacidad se
     * valida dentro del propio constructor de Sala (IllegalArgumentException
     * si es <= 0), igual que ya pasa al registrar pacientes.
     */
    private static void crearSala(Hospital hospital) {
        try {
            System.out.print("Nombre de la nueva sala: ");
            String nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) {
                throw new EntradaInvalidaException("El nombre de la sala no puede estar vacío.");
            }
            if (hospital.buscarSala(nombre) != null) {
                throw new SalaDuplicadaException(nombre);
            }

            System.out.print("Capacidad: ");
            int capacidad = Integer.parseInt(sc.nextLine().trim());

            Sala nueva = new Sala(nombre, capacidad);
            hospital.agregarSala(nueva);

        } catch (EntradaInvalidaException | SalaDuplicadaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Escribe solo números en la capacidad.");
        } catch (IllegalArgumentException e) {
            // Cubre capacidad <= 0.
            System.out.println("Error: " + e.getMessage());
        }
    }
}
