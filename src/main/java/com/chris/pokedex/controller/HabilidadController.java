package com.chris.pokedex.controller;



import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.repository.HabilidadRepository;
import com.chris.pokedex.repository.Services.HabilidadService;
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

    private final HabilidadService service;

    public HabilidadController(HabilidadService service){
        this.service = service;
    }

    @GetMapping
    public List<Habilidades> getAll(){
        return service.getAllHabilidades();
    }

    @GetMapping("/{id_habilidad}")
    public ResponseEntity<Habilidades> getById(@PathVariable Long id_habilidad){
        try{
            return ResponseEntity.ok(service.getHabilidadCompleta(id_habilidad));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
