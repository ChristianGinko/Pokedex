package com.chris.pokedex.controller;

import com.chris.pokedex.DTO.Tipos.TiposDTO;
import com.chris.pokedex.DTO.Tipos.TiposResumenDTO;
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

    public TipoController(TipoRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Tipos> getAllTipos(){
        return repository.findAll();
    }

    @GetMapping("/{id_tipo}")
    public Tipos obtener(@PathVariable Long id_tipo) {
        return repository.findById(id_tipo);
    }
}
