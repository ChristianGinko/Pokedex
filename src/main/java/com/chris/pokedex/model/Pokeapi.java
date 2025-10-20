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

    private Long id_liga;

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

    public Long getId_liga() {
        return id_liga;
    }

    public void setId_liga(Long id_liga) {
        this.id_liga = id_liga;
    }
}