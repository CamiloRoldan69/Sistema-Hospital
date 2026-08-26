package com.hospital.modelo;

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
    
    public abstract String generarReporte();
}
