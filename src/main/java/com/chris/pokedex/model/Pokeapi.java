package com.chris.pokedex.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pokemon")
public class Pokeapi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Relación con Tipo
    @ManyToMany
    @JoinTable(
            name = "pokemon_tipo",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_id")
    )
    private List<Tipo> tipo;

    // Relación con Habilidad (un Pokémon puede tener varias habilidades)
    @ManyToMany
    @JoinTable(
            name = "pokemon_habilidad", // tabla intermedia
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidad_id")
    )
    private List<Habilidades> habilidades;

    // ⚡ Relación uno a uno con Liga
    @ManyToMany
    @JoinTable(
            name = "pokemon_liga",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "liga_id")
    )
    private List<Liga> liga;

    // Getters y setters

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

    public List<Tipo> getTipo() {
        return tipo;
    }

    public void setTipo(List<Tipo> tipo) {
        this.tipo = tipo;
    }

    public List<Habilidades> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Habilidades> habilidades) {
        this.habilidades = habilidades;
    }

    public List<Liga> getLiga() {
        return liga;
    }

    public void setLiga(List<Liga> liga) {
        this.liga = liga;
    }
}