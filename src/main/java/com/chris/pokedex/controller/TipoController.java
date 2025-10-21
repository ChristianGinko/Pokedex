package com.chris.pokedex.controller;

import com.chris.pokedex.DTO.Tipos.TiposDTO;
import com.chris.pokedex.DTO.Tipos.TiposResumenDTO;
import com.chris.pokedex.PokemonService;
import com.chris.pokedex.model.Tipos;
import com.chris.pokedex.repository.TipoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipo")
public class TipoController {

    private final TipoRepository repository;
    private final PokemonService service;

    public TipoController(TipoRepository repository, PokemonService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public List<TiposResumenDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(t -> new TiposResumenDTO(t.getId_tipo(), t.getNombre()))
                .toList();
    }

    @GetMapping("/{id_tipo}")
    public ResponseEntity<TiposDTO> getById(@PathVariable Long id_tipo) {
        Optional<Tipos> tipos = repository.findById(id_tipo);
        return tipos.map(value -> ResponseEntity.ok(service.toDTO(value)))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }
}
