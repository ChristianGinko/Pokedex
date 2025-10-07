package com.chris.pokedex.controller;

import com.chris.pokedex.model.Tipo;
import com.chris.pokedex.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo")
public class TipoController {
    @Autowired
    private TipoRepository tipoRepository;

    @PostMapping
    public Tipo crearTipo(@RequestBody Tipo tipo) {
        return tipoRepository.save(tipo);
    }

    @GetMapping
    public List<Tipo> listarTipos() {
        return tipoRepository.findAll();
    }
}
