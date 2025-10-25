package com.chris.pokedex.controller;

import com.chris.pokedex.model.Tipos;
import com.chris.pokedex.repository.Services.TipoService;
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

    private final TipoService service;

    public TipoController(TipoService service){
        this.service = service;
    }

    @GetMapping
    public List<Tipos> getAll(){
        return service.getAllTipos();
    }

    @GetMapping("/{id_tipo")
    public ResponseEntity<Tipos> getById(@PathVariable Long id_tipo){
        try{
            return ResponseEntity.ok(service.getTipoCompleto(id_tipo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
