package com.chris.pokedex.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tipos")
public class Tipos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_tipo;


    private String nombre;

    public Long getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Long id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
