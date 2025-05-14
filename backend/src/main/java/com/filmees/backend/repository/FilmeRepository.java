package com.filmees.backend.repository;

import com.filmees.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// Filme Repository
@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {
    @Query("""
                SELECT f FROM Filme f 
                JOIN Aluguer a ON f.idFilme = a.filme.idFilme 
                WHERE a.dataLevantamento >= :dataLimite 
                GROUP BY f.idFilme 
                ORDER BY COUNT(a.filme.idFilme) DESC
            """)
    List<Filme> findMaisAlugadosUltimaSemana(@Param("dataLimite") LocalDate dataLimite);

    @Query("""
                SELECT f FROM Filme f
                WHERE (:genero IS NULL OR LOWER(f.genero) = LOWER(:genero))
                  AND (
                    :ano IS NULL OR (
                      :ano = 'older' AND YEAR(f.dataLancamento) < 2020
                      OR :ano <> 'older' AND YEAR(f.dataLancamento) = CAST(:ano AS int)
                    )
                  )
                  AND (
                    :duracao IS NULL OR
                    (:duracao = '-90min' AND f.duracao < 90) OR
                    (:duracao = '90min-120min' AND f.duracao BETWEEN 90 AND 120) OR
                    (:duracao = '+120min' AND f.duracao > 120)
                  )
                  AND f.disponivel = true
            """)
    List<Filme> filtrarFilmes(
            @Param("genero") String genero,
            @Param("ano") String ano,
            @Param("duracao") String duracao
    );

}

