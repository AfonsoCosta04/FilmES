package com.filmees.backend.repository;

import com.filmees.backend.model.Recomendacao;
import com.filmees.backend.model.RecomendacaoFilme;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecomendacaoFilmeRepository extends JpaRepository<RecomendacaoFilme, Integer> {
    void deleteByRecomendacao(Recomendacao recomendacao);

    List<RecomendacaoFilme> findByRecomendacao(Recomendacao recomendacao);

}
