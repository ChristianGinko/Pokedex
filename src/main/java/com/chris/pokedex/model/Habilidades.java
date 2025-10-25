package com.chris.pokedex.model;


public class Habilidades {
    private Long id_habilidad;
    private String nombre;
    private String efecto;
    private String efecto_corto;

    public Habilidades() {
    }

    public Long getId_habilidad() {
        return id_habilidad;
    }

    public void setId_habilidad(Long id_habilidad) {
        this.id_habilidad = id_habilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEfecto() {
        return efecto;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    public String getEfecto_corto() {
        return efecto_corto;
    }

    public void setEfecto_corto(String efecto_corto) {
        this.efecto_corto = efecto_corto;
    }
}
