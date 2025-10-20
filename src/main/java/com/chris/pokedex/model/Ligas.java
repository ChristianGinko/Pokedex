package com.chris.pokedex.model;


import jakarta.persistence.*;

@Entity
@Table(name = "ligas")
public class Ligas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_liga;

    private String nombre;

    public Long getId_liga() {
        return id_liga;
    }

    public void setId_liga(Long id_liga) {
        this.id_liga = id_liga;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
