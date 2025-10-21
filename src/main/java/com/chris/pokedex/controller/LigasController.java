package com.chris.pokedex.controller;


import com.chris.pokedex.DTO.Ligas.LigasDTO;
import com.chris.pokedex.DTO.Ligas.LigasResumenDTO;
import com.chris.pokedex.PokemonService;
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
    private final PokemonService service;

    public LigasController(LigaRepository repository, PokemonService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public List<LigasResumenDTO> getAll(){
        return repository.findAll()
                .stream()
                .map(l -> new LigasResumenDTO(l.getId_liga(), l.getNombre()))
                .toList();
    }

    @GetMapping("/{id_liga}")
    public ResponseEntity<LigasDTO> getById(@PathVariable Long id_liga) {
        Optional<Ligas> liga = repository.findById(id_liga);
        return liga.map(value -> ResponseEntity.ok(service.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
