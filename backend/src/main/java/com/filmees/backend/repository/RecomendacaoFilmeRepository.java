package com.filmees.backend.repository;

import com.filmees.backend.model.RecomendacaoFilme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecomendacaoFilmeRepository extends JpaRepository<RecomendacaoFilme, Integer> {
}
