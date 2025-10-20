package com.chris.pokedex.controller;


import com.chris.pokedex.model.Ligas;
import com.chris.pokedex.repository.LigaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ligas")
public class LigasController {

    private final LigaRepository repository;

    public LigasController(LigaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Ligas> getAll(){
        return repository.findAll();
    }
}
