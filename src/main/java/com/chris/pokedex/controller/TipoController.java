package com.chris.pokedex.controller;

import com.chris.pokedex.model.Tipo;
import com.chris.pokedex.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PatchMapping("/{id}")
    public ResponseEntity<Tipo> actualizarTipo(@PathVariable Long id, @RequestBody Tipo datosActualizados) {
        Optional<Tipo> tipoOpt = tipoRepository.findById(id);
        if (tipoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Tipo tipo = tipoOpt.get();

        // Si se manda un nuevo nombre
        if (datosActualizados.getNombre() != null) {
            tipo.setNombre(datosActualizados.getNombre());
        }

        if (datosActualizados.getDobleDanoDe() != null && !datosActualizados.getDobleDanoDe().isEmpty()) {
            tipo.getDobleDanoDe().addAll(datosActualizados.getDobleDanoDe());
        }

        if (datosActualizados.getDobleDanoA() != null && !datosActualizados.getDobleDanoA().isEmpty()) {
            tipo.getDobleDanoA().addAll(datosActualizados.getDobleDanoA());
        }

        if (datosActualizados.getMitadDanoDe() != null && !datosActualizados.getMitadDanoDe().isEmpty()) {
            tipo.getMitadDanoDe().addAll(datosActualizados.getMitadDanoDe());
        }

        if (datosActualizados.getMitadDanoA() != null && !datosActualizados.getMitadDanoA().isEmpty()) {
            tipo.getMitadDanoA().addAll(datosActualizados.getMitadDanoA());
        }

        if (datosActualizados.getSinDanoDe() != null && !datosActualizados.getSinDanoDe().isEmpty()) {
            tipo.getSinDanoDe().addAll(datosActualizados.getSinDanoDe());
        }

        if (datosActualizados.getSinDanoA() != null && !datosActualizados.getSinDanoA().isEmpty()) {
            tipo.getSinDanoA().addAll(datosActualizados.getSinDanoA());
        }

        Tipo actualizado = tipoRepository.save(tipo);
        return ResponseEntity.ok(actualizado);
    }
}
