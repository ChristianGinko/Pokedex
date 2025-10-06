package com.chris.pokedex.model;

import jakarta.persistence.*;

@Entity
@Table(name = "habilidad")
public class Habilidades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Constructor, getters y setters
    public Habilidades() {}

    public Habilidades(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}