package com.chris.pokedex.controller;


import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.repository.HabilidadRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/habilidad")
public class HabilidadController {

    private final HabilidadRepository repository;

    public HabilidadController(HabilidadRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Habilidades> getAll(){
        return repository.findAll();
    }
}
