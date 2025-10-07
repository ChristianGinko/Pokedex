package com.chris.pokedex.controller;

import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.repository.HabilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habilidad")
public class HabilidadController {
    @Autowired
    private HabilidadRepository habilidadRepository;

    @PostMapping
    public List<Habilidades> crearHabilidades(@RequestBody List<Habilidades> habilidades) {
        return habilidadRepository.saveAll(habilidades);
    }

    @GetMapping
    public List<Habilidades> listarHabilidades() {
        return habilidadRepository.findAll();
    }

}