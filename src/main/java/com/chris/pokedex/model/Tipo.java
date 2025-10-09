package com.chris.pokedex.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tipo")
public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "doble_daño_de",
            joinColumns = @JoinColumn(name = "tipo_id"),
            inverseJoinColumns = @JoinColumn(name = "doble_daño_de_id")
    )
    private Set<Tipo> dobleDañoDe = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "doble_daño_a",
            joinColumns = @JoinColumn(name = "tipo_id"),
            inverseJoinColumns = @JoinColumn(name = "doble_daño_a_id")
    )
    private Set<Tipo> dobleDañoA = new HashSet<>();

    // Constructor, getters y setters
    public Tipo() {}

    public Tipo(String nombre) {
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