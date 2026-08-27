package com.hospital.modelo;

/**
 * Clase ABSTRACTA que representa a cualquier miembro del personal
 * del hospital. Es abstracta porque en la vida real nadie "es"
 * Personal a secas: siempre es un Médico o un Enfermero.
 *
 * Una clase abstracta:
 *   - NO se puede instanciar directamente (no existe "new Personal(...)").
 *   - Puede tener métodos ya implementados (los getters de abajo, que
 *     TODAS las subclases heredan gratis).
 *   - Puede declarar métodos SIN cuerpo (abstractos), que obligan a
 *     cada subclase a implementarlos a su manera. Aquí ese método es
 *     generarReporte().
 */
public abstract class Personal {

    private String nombre;
    private String id;
    private String especialidad;

    public Personal(String nombre, String id, String especialidad) {
        this.nombre = nombre;
        this.id = id;
        this.especialidad = especialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Método abstracto: no tiene "{ ... }", solo termina en ";".
     * Cada subclase (Medico, Enfermero) DEBE escribir su propia
     * versión. Esto es la base del POLIMORFISMO DINÁMICO: en tiempo
     * de ejecución, Java decide automáticamente cuál versión llamar
     * según el tipo real del objeto (ver Hospital.generarReporteGeneral()).
     */
    public abstract String generarReporte();
}
