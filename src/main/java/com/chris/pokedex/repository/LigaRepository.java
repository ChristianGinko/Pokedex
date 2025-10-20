package com.chris.pokedex.repository;

import com.chris.pokedex.model.Ligas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigaRepository extends JpaRepository<Ligas, Long> {
}
