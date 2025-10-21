package com.chris.pokedex.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pokemons")
public class Pokeapi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pokemon;

    private String nombre;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pokemon_tipo",
            joinColumns = @JoinColumn(name = "id_pokemon"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo")
    )
    private List<Tipos> tipo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pokemon_habilidad",
            joinColumns = @JoinColumn(name = "id_pokemon"),
            inverseJoinColumns = @JoinColumn(name = "id_habilidad")
    )
    private List<Habilidades> habilidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_liga", nullable = false)
    private Ligas liga;

    public Long getId_pokemon() {
        return id_pokemon;
    }

    public void setId_pokemon(Long id_pokemon) {
        this.id_pokemon = id_pokemon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Tipos> getTipo() {
        return tipo;
    }

    public void setTipo(List<Tipos> tipo) {
        this.tipo = tipo;
    }

    public List<Habilidades> getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(List<Habilidades> habilidad) {
        this.habilidad = habilidad;
    }

    public Ligas getLiga() {
        return liga;
    }

    public void setLiga(Ligas liga) {
        this.liga = liga;
    }
}