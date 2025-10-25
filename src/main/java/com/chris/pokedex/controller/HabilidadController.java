package com.chris.pokedex.controller;


import com.chris.pokedex.DTO.Habilidades.HabilidadesDTO;
import com.chris.pokedex.DTO.Habilidades.HabilidadesResumenDTO;
import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.repository.HabilidadRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/habilidad")
public class HabilidadController {

    private final HabilidadRepository repository;

    public HabilidadController(HabilidadRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Habilidades> getAllHabilidades(){
        return repository.findAll();
    }

    @GetMapping("/{id_habilidad}")
    public Habilidades obtener(@PathVariable Long id_habilidad) {
        return repository.findById(id_habilidad);
    }
}
