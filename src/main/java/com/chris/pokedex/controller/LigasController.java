package com.chris.pokedex.controller;


import com.chris.pokedex.DTO.Ligas.LigasDTO;
import com.chris.pokedex.DTO.Ligas.LigasResumenDTO;
import com.chris.pokedex.model.Ligas;
import com.chris.pokedex.repository.LigaRepository;
import com.chris.pokedex.repository.Services.LigaService;
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

    private final LigaService service;

    public LigasController(LigaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ligas> getAll(){
        return service.getAllLigas();
    }

    @GetMapping("/{id_liga}")
    public ResponseEntity<Ligas> getById(@PathVariable Long id_liga){
        try{
            return ResponseEntity.ok(service.getLigaCompleta(id_liga));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
