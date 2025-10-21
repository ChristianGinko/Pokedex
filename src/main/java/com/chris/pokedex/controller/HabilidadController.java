package com.chris.pokedex.controller;


import com.chris.pokedex.DTO.Habilidades.HabilidadesDTO;
import com.chris.pokedex.DTO.Habilidades.HabilidadesResumenDTO;
import com.chris.pokedex.PokemonService;
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
    private final PokemonService service;

    public HabilidadController(HabilidadRepository repository, PokemonService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public List<HabilidadesResumenDTO> getAll(){
        return repository.findAll()
                .stream()
                .map( h -> new HabilidadesResumenDTO(h.getId_habilidad(), h.getNombre()))
                .toList();
    }

    @GetMapping("/{id_habilidad}")
    public ResponseEntity<HabilidadesDTO> getById(@PathVariable Long id_habilidad) {
        Optional<Habilidades> habilidades = repository.findById(id_habilidad);
        return habilidades.map(value -> ResponseEntity.ok(service.toDTO(value)))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }
}
