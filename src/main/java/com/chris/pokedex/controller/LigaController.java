package com.chris.pokedex.controller;

import com.chris.pokedex.model.Liga;
import com.chris.pokedex.repository.LigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/liga")
public class LigaController {
    @Autowired
    private LigaRepository ligaRepository;

    @PostMapping
    public List<Liga> crearLiga(@RequestBody List<Liga> liga){
        return ligaRepository.saveAll(liga);
    }

    @GetMapping
    public List<Liga> listarLiga(){
        return ligaRepository.findAll();
    }
}
