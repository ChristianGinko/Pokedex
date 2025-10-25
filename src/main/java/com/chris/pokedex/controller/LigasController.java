package com.chris.pokedex.controller;


import com.chris.pokedex.DTO.Ligas.LigasDTO;
import com.chris.pokedex.DTO.Ligas.LigasResumenDTO;
import com.chris.pokedex.model.Ligas;
import com.chris.pokedex.repository.LigaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ligas")
public class LigasController {

    private final LigaRepository repository;

    public LigasController(LigaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Ligas> getAllLigas(){
        return repository.findAll();
    }

    @GetMapping("/{id_liga}")
    public Ligas obtener(@PathVariable Long id_liga) {
        return repository.findById(id_liga);
    }
}
